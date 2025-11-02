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
import com.practica.exchangecrypto.domain.state.UiState
import com.practica.exchangecrypto.ui.home.adapter.CryptoAdapter
import com.practica.exchangecrypto.ui.home.adapter.CryptoHorizontalAdapter
import com.practica.exchangecrypto.ui.shared.SharedCryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val vm: HomeViewModel by viewModels()
    private val sharedViewModel: SharedCryptoViewModel by activityViewModels()

    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var cryptoHorizontalAdapter: CryptoHorizontalAdapter

    private var currentPage = 1
    private val perPage = 50

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ---------- Vertical Adapter ----------
        cryptoAdapter = CryptoAdapter(emptyList(), sharedViewModel) { crypto ->
            sharedViewModel.selectCrypto(crypto)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
        binding.rvCrypto.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
        }

        // ---------- Horizontal Adapter ----------
        cryptoHorizontalAdapter = CryptoHorizontalAdapter(emptyList())
        binding.rvTopCryptos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = cryptoHorizontalAdapter
        }

        // ---------- Observe ViewModel ----------
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE

                            val allCryptos = state.data

                            // --- Top 5 horizontal ---
                            val topCryptos = allCryptos.take(5).map { it.toHorizontal() }
                            cryptoHorizontalAdapter.updateList(topCryptos)

                            // --- Vertical list ---
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
                            sharedViewModel.setCryptoList(verticalList)
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Snackbar.make(binding.root, "Error: ${state.message}", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        // ---------- Pagination ----------
        binding.rvCrypto.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                val lm = recyclerView.layoutManager as LinearLayoutManager
                val lastVisible = lm.findLastVisibleItemPosition()
                if (lastVisible >= (cryptoAdapter.itemCount - 5)) {
                    currentPage += 1
                    vm.loadMarkets(currentPage)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
