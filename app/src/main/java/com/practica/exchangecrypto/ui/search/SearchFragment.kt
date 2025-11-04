package com.practica.exchangecrypto.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.databinding.FragmentSearchBinding
import com.practica.exchangecrypto.ui.home.adapter.CryptoAdapter
import com.practica.exchangecrypto.ui.shared.SharedCryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Marks this fragment for Hilt dependency injection.
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    // Access the Shared ViewModel to retrieve the full list of cryptos from HomeFragment.
    private val sharedViewModel: SharedCryptoViewModel by activityViewModels()

    private lateinit var cryptoAdapter: CryptoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up navigation for the back arrow.
        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }

        // --- Search bar entrance animation ---
        binding.etSearch.apply {
            // Initial positioning for the animation start.
            translationY = 200f
            alpha = 0f

            // Animate properties (slide up and fade in).
            animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(600)
                .setStartDelay(200)
                .start()
        }

        // --- RecyclerView Setup ---
        cryptoAdapter = CryptoAdapter(emptyList(), sharedViewModel) { crypto ->
            // Action on item click: save the selected crypto and navigate to the detail view.
            sharedViewModel.selectCrypto(crypto)
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment)
        }

        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
        }

        // --- Data Observation ---
        // Observe the full crypto list shared by the HomeFragment.
        sharedViewModel.cryptoList.observe(viewLifecycleOwner) { fullList ->
            // Initially populate the search results with the full list.
            cryptoAdapter.updateList(fullList)
        }

        // --- Dynamic Search Filter (Debounced) ---
        var searchJob: Job? = null

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            searchJob?.cancel() // Cancel any previous search job if the user types quickly.

            // Launch a new coroutine for the search operation.
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(250) // Debounce: wait 250ms to reduce unnecessary filtering operations.
                val query = text.toString().trim().lowercase()

                // Filter the full list based on name or symbol matching the query.
                val filteredList = sharedViewModel.cryptoList.value?.filter {
                    it.name.lowercase().contains(query) || it.symbol.lowercase().contains(query)
                } ?: emptyList()

                // Apply a fade-in animation to the results RecyclerView for a smooth update.
                val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
                binding.rvSearchResults.startAnimation(fadeIn)

                cryptoAdapter.updateList(filteredList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}