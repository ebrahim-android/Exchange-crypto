package com.practica.exchangecrypto.ui.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.databinding.FragmentDetailBinding
import com.practica.exchangecrypto.domain.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var selectedDays: String = "7"
    private var currentCryptoId: String? = null

    private val vm: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }

        val cryptoId = arguments?.getString("cryptoId")
        Log.d("DetailFragment", "Crypto ID received: $cryptoId")
        if (cryptoId == null) return

        currentCryptoId = cryptoId.lowercase()
        vm.loadCryptoDetail(currentCryptoId!!, "7")

        // --- Time range buttons ---
        binding.btn1D.setOnClickListener {
            updateChart("1")
            updateSelectedButton(binding.btn1D)
        }
        binding.btn1W.setOnClickListener {
            updateChart("7")
            updateSelectedButton(binding.btn1W)
        }
        binding.btn1M.setOnClickListener {
            updateChart("30")
            updateSelectedButton(binding.btn1M)
        }
        binding.btn6M.setOnClickListener {
            updateChart("180")
            updateSelectedButton(binding.btn6M)
        }
        binding.btn1Y.setOnClickListener {
            updateChart("365")
            updateSelectedButton(binding.btn1Y)
        }

        // --- We observe the changes in detail ---
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val c = state.data
                            binding.tvName.text = c.name
                            binding.tvPrice.text = String.format("$%,.2f", c.currentPrice)

                            val change = c.priceChange24h ?: 0.0
                            binding.tvChange.text = String.format("%.2f%%", change)
                            binding.tvChange.setTextColor(
                                if (change < 0) Color.parseColor("#FF6666")
                                else Color.parseColor("#4CAF50")
                            )

                            binding.tvMarketCap.text = c.marketCap?.let { formatCompact(it) } ?: "—"
                            binding.tvVolume.text = c.volume24h?.let { formatCompact(it) } ?: "—"
                            binding.tvHighLow.text =
                                if (c.high24h != null && c.low24h != null)
                                    "$${formatNumber(c.high24h)} / $${formatNumber(c.low24h)}"
                                else "—"

                            Log.d("MARKET_DATA", "Chart received: ${c.history.size} points for range $selectedDays")
                            setupChart(c.history)
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    // --- Set up the chart ---
    private fun setupChart(history: List<Double>) {
        if (history.isEmpty()) return

        val entries = history.mapIndexed { index, value -> Entry(index.toFloat(), value.toFloat()) }

        // 🔴🟢 Dynamic line color based on price trend
        // If the last price is lower than the first, the line turns red (downtrend)
        // Otherwise, it turns green (uptrend)
        val startPrice = history.first()
        val endPrice = history.last()
        val lineColor = if (endPrice < startPrice) Color.parseColor("#FF6666") else Color.parseColor("#4CAF50")

        val dataSet = LineDataSet(entries, "Price").apply {
            setDrawValues(false)
            setDrawCircles(false)
            lineWidth = 2f
            color = lineColor
        }

        binding.lineChart.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            axisLeft.textColor = Color.WHITE
            xAxis.textColor = Color.WHITE
            axisRight.isEnabled = false
            invalidate()
        }
    }

    // --- Formatters ---
    private fun formatNumber(value: Double): String =
        String.format("%,.2f", value)

    private fun formatCompact(value: Double): String {
        val abs = kotlin.math.abs(value)
        return when {
            abs >= 1_000_000_000 -> String.format("$%,.2fB", value / 1_000_000_000)
            abs >= 1_000_000 -> String.format("$%,.2fM", value / 1_000_000)
            abs >= 1_000 -> String.format("$%,.2fK", value / 1_000)
            else -> String.format("$%,.2f", value)
        }
    }

    // --- Change range ---
    private fun updateChart(days: String) {
        currentCryptoId?.let { id ->
            selectedDays = days
            binding.progressBar.visibility = View.VISIBLE

            viewLifecycleOwner.lifecycleScope.launch {
                vm.loadCryptoDetail(id, days)
                Log.d("MARKET_DATA", "Requesting data: ID = $id | Days = $days")
            }
        }
    }

    // --- Button styles ---
    private fun updateSelectedButton(selectedButton: Button) {
        val buttons = listOf(
            binding.btn1D,
            binding.btn1W,
            binding.btn1M,
            binding.btn6M,
            binding.btn1Y
        )

        val styleSelected = R.style.TimeRangeButton_Selected
        val styleBase = R.style.TimeRangeButton
        val bgSelected = R.drawable.bg_time_range_selected

        buttons.forEach { button ->
            if (button == selectedButton) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    button.setTextAppearance(styleSelected)
                else
                    @Suppress("DEPRECATION")
                    button.setTextAppearance(button.context, styleSelected)

                button.background = button.context.getDrawable(bgSelected)
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    button.setTextAppearance(styleBase)
                else
                    @Suppress("DEPRECATION")
                    button.setTextAppearance(button.context, styleBase)

                button.background = null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
