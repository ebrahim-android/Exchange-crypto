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
import androidx.fragment.app.activityViewModels
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
import com.practica.exchangecrypto.ui.model.CryptoItem
import com.practica.exchangecrypto.ui.shared.SharedCryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// Marks this fragment for Hilt dependency injection.
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // Default time range for chart data request.
    private var selectedDays: String = "7"
    private var currentCryptoId: String? = null

    // Initialize ViewModels.
    private val vm: DetailViewModel by viewModels()
    private val sharedViewModel: SharedCryptoViewModel by activityViewModels()

    private var currentCrypto: CryptoItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up navigation for the back arrow.
        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }

        // Attempt to retrieve data passed via arguments (though primarily relying on Shared ViewModel).
        currentCrypto = arguments?.getParcelable("crypto")

        // --- Observe the selected crypto from the Shared ViewModel ---
        sharedViewModel.selectedCrypto.observe(viewLifecycleOwner) { crypto ->
            if (crypto != null) {
                currentCrypto = crypto
                currentCryptoId = crypto.id.lowercase()
                // Load the detailed data and default chart history (7 days).
                vm.loadCryptoDetail(currentCryptoId!!, selectedDays)

                // Check and update the favorite icon state immediately.
                val isFav = sharedViewModel.isFavotire(crypto)
                updateFavoriteIcon(isFav)

                // Set click listener for the favorite icon to toggle the favorite status.
                binding.ivFavorite.setOnClickListener {
                    sharedViewModel.toggleFavorite(crypto)
                    val newState = !isFav
                    updateFavoriteIcon(newState)
                }
            }
        }

        // Secondary/fallback click listener for favorite icon (using 'currentCrypto').
        binding.ivFavorite.setOnClickListener {
            currentCrypto?.let { crypto ->
                sharedViewModel.toggleFavorite(crypto)
                // Invert the current favorite state for the UI update.
                updateFavoriteIcon(!sharedViewModel.isFavotire(crypto))
            }
        }
        // Initial state update for the favorite icon if data is available from arguments.
        currentCrypto?.let { updateFavoriteIcon(sharedViewModel.isFavotire(it)) }


        // --- Time range buttons setup ---
        setupTimeRangeButtons()

        // --- Observe the detail data state ---
        observeDetailState()
    }

    // --- Updates the favorite icon based on the state ---
    private fun updateFavoriteIcon(isFavorite: Boolean) {
        // Re-check state using Shared ViewModel to ensure accuracy.
        val isFavorite = sharedViewModel.isFavotire(currentCrypto!!)
        val iconRes = if (isFavorite) R.drawable.ic_favorite_complete else R.drawable.ic_favorite
        binding.ivFavorite.setImageResource(iconRes)
    }

    // --- Observes the ViewModel's UiState for detailed crypto data ---
    private fun observeDetailState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val c = state.data

                            // Bind main textual data.
                            binding.tvName.text = c.name
                            // Format price to currency ($X,XXX.XX).
                            binding.tvPrice.text = String.format("$%,.2f", c.currentPrice)

                            val change = c.priceChange24h ?: 0.0
                            // Format 24h change percentage.
                            binding.tvChange.text = String.format("%.2f%%", change)
                            // Set change text color based on positive/negative change.
                            binding.tvChange.setTextColor(
                                if (change < 0) Color.parseColor("#FF6666") // Red for negative
                                else Color.parseColor("#4CAF50") // Green for positive
                            )

                            // Bind market data (using compact formatting helpers).
                            binding.tvMarketCap.text = c.marketCap?.let { formatCompact(it) } ?: "—"
                            binding.tvVolume.text = c.volume24h?.let { formatCompact(it) } ?: "—"
                            // Format and bind 24h high/low data.
                            binding.tvHighLow.text =
                                if (c.high24h != null && c.low24h != null)
                                    "$${formatNumber(c.high24h)} / $${formatNumber(c.low24h)}"
                                else "—"

                            Log.d("MARKET_DATA", "Chart received: ${c.history.size} points for range $selectedDays")
                            // Draw the price chart using the received historical data.
                            setupChart(c.history)
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            // Error handling logic would go here (e.g., showing a Snackbar).
                        }
                    }
                }
            }
        }
    }

    // --- Configures the click listeners for time range buttons ---
    private fun setupTimeRangeButtons() {
        binding.btn1D.setOnClickListener {
            updateChart("1") // Request 1 day of data.
            updateSelectedButton(binding.btn1D)
        }
        binding.btn1W.setOnClickListener {
            updateChart("7") // Request 7 days of data.
            updateSelectedButton(binding.btn1W)
        }
        binding.btn1M.setOnClickListener {
            updateChart("30") // Request 30 days of data.
            updateSelectedButton(binding.btn1M)
        }
        binding.btn6M.setOnClickListener {
            updateChart("180") // Request 180 days (6 months) of data.
            updateSelectedButton(binding.btn6M)
        }
        binding.btn1Y.setOnClickListener {
            updateChart("365") // Request 365 days (1 year) of data.
            updateSelectedButton(binding.btn1Y)
        }
        // Note: Initial state relies on default '7' days, need to manually call updateSelectedButton(binding.btn1W) here if desired.
    }

    // --- Sets up the chart using MPAndroidChart library ---
    private fun setupChart(history: List<Double>) {
        if (history.isEmpty()) return

        // Convert the list of prices into MPAndroidChart Entry objects (X=index, Y=price).
        val entries = history.mapIndexed { index, value -> Entry(index.toFloat(), value.toFloat()) }

        val startPrice = history.first()
        val endPrice = history.last()
        // Determine line color based on overall price trend (end vs start).
        val lineColor = if (endPrice < startPrice) Color.parseColor("#FF6666") else Color.parseColor("#4CAF50")

        // Configure the dataset (styling of the line).
        val dataSet = LineDataSet(entries, "Price").apply {
            setDrawValues(false) // Hide price values on the line.
            setDrawCircles(false) // Hide individual data point circles.
            lineWidth = 2f
            color = lineColor
        }

        binding.lineChart.apply {
            data = LineData(dataSet)
            description.isEnabled = false // Hide the description label.
            // Style axes text color.
            axisLeft.textColor = Color.WHITE
            xAxis.textColor = Color.WHITE
            axisRight.isEnabled = false // Disable the right Y-axis for cleaner look.
            invalidate() // Redraw the chart.
        }
    }

    // --- Requests new chart data from the ViewModel ---
    private fun updateChart(days: String) {
        currentCryptoId?.let { id ->
            selectedDays = days
            binding.progressBar.visibility = View.VISIBLE // Show loading indicator.

            // Launch request in a coroutine.
            lifecycleScope.launch {
                vm.loadCryptoDetail(id, days)
                Log.d("MARKET_DATA", "Requesting data: ID = $id | Days = $days")
            }
        }
    }

    // --- Helper function: formats a large number to standard currency/decimal format ---
    private fun formatNumber(value: Double): String =
        String.format("%,.2f", value)

    // --- Helper function: formats large numbers compactly (e.g., $1.25B, $50M) ---
    private fun formatCompact(value: Double): String {
        val abs = kotlin.math.abs(value)
        return when {
            abs >= 1_000_000_000 -> String.format("$%,.2fB", value / 1_000_000_000)
            abs >= 1_000_000 -> String.format("$%,.2fM", value / 1_000_000)
            abs >= 1_000 -> String.format("$%,.2fK", value / 1_000)
            else -> String.format("%,.2f", value)
        }
    }

    // --- Applies the selected/unselected style to the time range buttons ---
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
                // Apply selected style using appropriate API level method.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    button.setTextAppearance(styleSelected)
                else
                    @Suppress("DEPRECATION")
                    button.setTextAppearance(button.context, styleSelected)

                // Set the selected background drawable.
                button.background = button.context.getDrawable(bgSelected)
            } else {
                // Apply base/unselected style.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    button.setTextAppearance(styleBase)
                else
                    @Suppress("DEPRECATION")
                    button.setTextAppearance(button.context, styleBase)

                // Remove background (set to null/transparent).
                button.background = null
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}