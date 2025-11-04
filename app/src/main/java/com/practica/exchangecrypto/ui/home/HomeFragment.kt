package com.practica.exchangecrypto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.databinding.FragmentHomeBinding
import com.practica.exchangecrypto.domain.model.CryptoFilter
import com.practica.exchangecrypto.domain.state.UiState
import com.practica.exchangecrypto.ui.home.adapter.CryptoAdapter
import com.practica.exchangecrypto.ui.home.adapter.CryptoHorizontalAdapter
import com.practica.exchangecrypto.ui.home.adapter.FilterAdapter
import com.practica.exchangecrypto.ui.shared.SharedCryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// Marks this fragment for Hilt dependency injection.
@AndroidEntryPoint
class HomeFragment : Fragment() {

    // View Binding for the fragment. Using a nullable backing property.
    private var _binding: FragmentHomeBinding? = null
    // Non-nullable access to the binding.
    private val binding get() = _binding!!

    // Initialize ViewModel using the fragment's scope.
    private val vm: HomeViewModel by viewModels()
    // Initialize Shared ViewModel, scoped to the Activity for data sharing (e.g., with Detail or Search).
    private val sharedViewModel: SharedCryptoViewModel by activityViewModels()

    // Adapters for the different RecyclerViews.
    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var cryptoHorizontalAdapter: CryptoHorizontalAdapter
    private lateinit var filterAdapter: FilterAdapter

    // Variables for handling pagination.
    private var currentPage = 1
    private val perPage = 50 // Not used in the provided code, but kept for context.

    // Inflates the layout for this fragment using View Binding.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ---------- Vertical Adapter (Main Crypto List) ----------
        // Initializes the main list adapter. Sets up a click listener for navigation.
        cryptoAdapter = CryptoAdapter(emptyList(), sharedViewModel) { crypto ->
            // Save the selected crypto item in the Shared ViewModel.
            sharedViewModel.selectCrypto(crypto)
            // Navigate to the detail screen.
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
        binding.rvCrypto.apply {
            // Set the LayoutManager for the main vertical list.
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
        }

        // ---------- Horizontal Adapter (Top cryptos) ----------
        // Initializes the horizontal adapter for trending/top cryptos.
        cryptoHorizontalAdapter = CryptoHorizontalAdapter(emptyList())
        binding.rvTopCryptos.apply {
            // Set the LayoutManager for the horizontal scrolling list.
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = cryptoHorizontalAdapter
        }

        // ---------- Filter Adapter ----------
        // Defines the list of available filters (e.g., All, Top Gainers, Top Losers).
        val filters = listOf(
            CryptoFilter.ALL,
            CryptoFilter.TOP_GAINERS,
            CryptoFilter.TOP_LOSERS,
            CryptoFilter.VOLUME,
            CryptoFilter.MARKET_CAP
        )

        // Initializes the filter adapter and defines the action when a filter chip is clicked.
        filterAdapter = FilterAdapter(filters) { selectedFilter ->
            // Get the current state from the ViewModel.
            val state = vm.uiState.value
            // Only proceed if the data is successfully loaded.
            if (state is UiState.Success) {
                // Apply the sorting/filtering logic based on the selected filter.
                val filteredList = when (selectedFilter) {
                    CryptoFilter.ALL -> state.data
                        .sortedByDescending { it.marketCap } // Sort by Market Cap (default for ALL)

                    CryptoFilter.TOP_GAINERS -> state.data
                        .sortedByDescending { it.priceChange24h }
                        .take(10) // Take only the top 10 with the highest gain.

                    CryptoFilter.TOP_LOSERS -> state.data
                        .sortedBy { it.priceChange24h } // Ascending order for losers
                        .take(10) // Take only the top 10 with the biggest drop.

                    CryptoFilter.VOLUME -> state.data
                        .sortedByDescending { it.totalVolume }
                        .take(10) // Take the top 10 most traded coins.

                    CryptoFilter.MARKET_CAP -> state.data
                        .sortedByDescending { it.marketCap }
                        .take(10) // Take the 10 largest by market capitalization.
                }

                // Update the vertical list (main list) with the filtered data.
                val newList = filteredList.map {
                    com.practica.exchangecrypto.ui.model.CryptoItem(
                        id = it.id,
                        name = it.name,
                        symbol = it.symbol,
                        price = "$${it.currentPrice}",
                        change = "${it.priceChange24h}%",
                        iconUrl = it.imageUrl
                    )
                }
                cryptoAdapter.updateList(newList)
            }
        }

        binding.rvFilters.apply {
            // Set the LayoutManager for the horizontal filter chips.
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = filterAdapter
        }

        // ---------- Observe ViewModel ----------
        // Launch a coroutine to collect StateFlow emissions safely.
        viewLifecycleOwner.lifecycleScope.launch {
            // Use repeatOnLifecycle to restart collection when the fragment is STARTed and stop when STOPped.
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE // Show loading indicator.

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE // Hide loading indicator.

                            val allCryptos = state.data

                            // --- Top 5 horizontal ---
                            // Prepare data for the horizontal "Top Cryptos" list (first 5 items).
                            val topCryptos = allCryptos.take(5).map { it.toHorizontal() }
                            cryptoHorizontalAdapter.updateList(topCryptos)

                            // --- Vertical list ---
                            // Prepare data for the main vertical list.
                            val verticalList = allCryptos.map {
                                com.practica.exchangecrypto.ui.model.CryptoItem(
                                    id = it.id,
                                    name = it.name,
                                    symbol = it.symbol,
                                    price = "$${it.currentPrice}",
                                    change = "${it.priceChange24h}%",
                                    iconUrl = it.imageUrl
                                )
                            }
                            cryptoAdapter.updateList(verticalList)

                            // --- Share with SearchFragment ---
                            // Update the shared ViewModel with the complete list for search functionality.
                            sharedViewModel.setCryptoList(verticalList)
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE // Hide loading indicator.
                            // Show an error message using a Snackbar.
                            Snackbar.make(binding.root, "Error: ${state.message}", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        // ---------- Pagination ----------
        // Attaches a scroll listener to the main crypto list for infinite scrolling/pagination.
        binding.rvCrypto.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                dx: Int,
                dy: Int
            ) {
                // Check if scrolling down (dy > 0)
                if (dy > 0) {
                    val lm = recyclerView.layoutManager as LinearLayoutManager
                    // Find the position of the last visible item.
                    val lastVisible = lm.findLastVisibleItemPosition()
                    // Check if the user is 5 items away from the end of the current list.
                    if (lastVisible >= (cryptoAdapter.itemCount - 5)) {
                        // Increment the page number.
                        currentPage += 1
                        // Request more data from the ViewModel.
                        vm.loadMarkets(currentPage)
                    }
                }
            }
        })
    }

    // Clears the binding reference when the view is destroyed to avoid memory leaks.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}