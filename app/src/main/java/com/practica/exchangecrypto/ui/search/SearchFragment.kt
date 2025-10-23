package com.practica.exchangecrypto.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.practica.exchangecrypto.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

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



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}