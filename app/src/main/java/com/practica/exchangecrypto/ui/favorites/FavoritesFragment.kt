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

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

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

        // 👀 Observamos los cambios en la lista de favoritos
        sharedViewModel.favoriteCryptos.observe(viewLifecycleOwner) { favorites ->
            adapter.updateList(favorites)
            binding.tvEmpty.visibility = if (favorites.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupRecyclerView() {
        adapter = CryptoAdapter(mutableListOf(), sharedViewModel) { crypto ->
            val bundle = Bundle().apply { putParcelable("crypto", crypto) }
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

