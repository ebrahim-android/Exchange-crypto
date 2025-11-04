package com.practica.exchangecrypto.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.databinding.FragmentFavoritesBinding
import com.practica.exchangecrypto.ui.home.adapter.CryptoAdapter
import com.practica.exchangecrypto.ui.shared.SharedCryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

// Marks this fragment for Hilt dependency injection.
@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    // Access the Shared ViewModel to get the list of favorite cryptocurrencies.
    private val sharedViewModel: SharedCryptoViewModel by activityViewModels()
    private lateinit var adapter: CryptoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        // --- Observe changes in the favorites list ---
        sharedViewModel.favoriteCryptos.observe(viewLifecycleOwner) { favorites ->
            // Update the RecyclerView adapter with the latest favorites list.
            adapter.updateList(favorites)
            // Show or hide the empty list message based on the list size.
            binding.tvEmpty.visibility = if (favorites.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    /**
     * Initializes the RecyclerView and its adapter, including the click listener for navigation.
     */
    private fun setupRecyclerView() {
        // Initialize the adapter with an empty list and a click listener.
        adapter = CryptoAdapter(mutableListOf(), sharedViewModel) { crypto ->
            // Prepare the bundle to pass the selected crypto item to the detail screen.
            val bundle = Bundle().apply { putParcelable("crypto", crypto) }
            // Navigate to the detail fragment.
            findNavController().navigate(R.id.action_favoritesFragment_to_detailFragment, bundle)
        }

        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}