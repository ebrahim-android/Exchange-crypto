package com.practica.exchangecrypto.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.databinding.FragmentSearchBinding
import com.practica.exchangecrypto.ui.home.adapter.CryptoAdapter
import com.practica.exchangecrypto.ui.shared.SharedCryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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

        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }

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
        cryptoAdapter = CryptoAdapter(emptyList()) { /* click no necesario aquí */ }

        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
        }

        //Observe list from HomeFragment
        sharedViewModel.cryptoList.observe(viewLifecycleOwner) { fullList ->
            cryptoAdapter.updateList(fullList)
        }

        // Filtro dinámico
        var searchJob: Job? = null

        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            searchJob?.cancel() // Cancelamos la búsqueda anterior si el usuario sigue escribiendo
            searchJob = viewLifecycleOwner.lifecycleScope.launch {
                delay(250) // Espera 250ms antes de ejecutar el filtro (debounce)
                val query = text.toString().trim().lowercase()

                val filteredList = sharedViewModel.cryptoList.value?.filter {
                    it.name.lowercase().contains(query) || it.symbol.lowercase().contains(query)
                } ?: emptyList()

                // Animación de entrada suave
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