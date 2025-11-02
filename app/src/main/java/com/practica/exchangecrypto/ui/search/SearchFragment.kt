package com.practica.exchangecrypto.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.databinding.FragmentSearchBinding
import com.practica.exchangecrypto.ui.home.adapter.CryptoAdapter
import com.practica.exchangecrypto.ui.model.CryptoItem
import com.practica.exchangecrypto.ui.shared.SharedCryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedCryptoViewModel by activityViewModels() // to get the list from HomeFragment

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

        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }
        // animation for search bar
        binding.etSearch.apply {
            translationY = 200f
            alpha = 0f

            animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(600)
                .setStartDelay(200)
                .start()
        }
        // to navigate to detailFragment
        cryptoAdapter = CryptoAdapter(emptyList(), sharedViewModel) { crypto ->
            sharedViewModel.selectCrypto(crypto)
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment)
        }

        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
        }

        //Observe list from HomeFragment
        sharedViewModel.cryptoList.observe(viewLifecycleOwner) { fullList ->
            cryptoAdapter.updateList(fullList)
        }

        // dynamic filter
        var searchJob: Job? = null

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            searchJob?.cancel() // We cancel the previous search if the user is typing
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(250) // Wait 250ms before executing the filter (debounce)
                val query = text.toString().trim().lowercase()

                val filteredList = sharedViewModel.cryptoList.value?.filter {
                    it.name.lowercase().contains(query) || it.symbol.lowercase().contains(query)
                } ?: emptyList()

                // Smooth entrance animation
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