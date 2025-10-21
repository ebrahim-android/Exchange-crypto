package com.practica.exchangecrypto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val vm: HomeViewModel by viewModels()

    private lateinit var cryptoAdapter: CryptoAdapter
    private lateinit var cryptoHorizontalAdapter: CryptoHorizontalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ---------- Adapter vertical ----------
        cryptoAdapter = CryptoAdapter(emptyList()) { selectedCrypto ->
            val bundle = bundleOf("cryptoId" to selectedCrypto.id)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }

        // We assign the vertical adapter
        binding.rvCrypto.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cryptoAdapter
        }

        // ---------- horizontal Adapter ----------
        cryptoHorizontalAdapter = CryptoHorizontalAdapter(emptyList())
        binding.rvTopCryptos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = cryptoHorizontalAdapter
        }

        // ---------- Observe the state of the ViewModel ----------
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE

                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE

                            val topCryptos = state.data.take(5)
                            val allCryptos = state.data

                            cryptoHorizontalAdapter.updateList(topCryptos.map {
                                com.practica.exchangecrypto.ui.model.CryptoHorizontal(
                                    name = it.name,
                                    symbol = it.symbol,
                                    price = "$${it.currentPrice}",
                                    change = "${it.priceChange24h}%",
                                    iconUrl = it.imageUrl
                                )
                            })

                            cryptoAdapter.updateList(allCryptos.map {
                                com.practica.exchangecrypto.ui.model.CryptoItem(
                                    id = it.id,
                                    name = it.name,
                                    symbol = it.symbol,
                                    price = "$${it.currentPrice}",
                                    change = "${it.priceChange24h}%",
                                    iconUrl = it.imageUrl
                                )
                            })
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
                val last = lm.findLastVisibleItemPosition()
                if (last >= (cryptoAdapter.itemCount - 5)) {
                    vm.loadNextPage()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
