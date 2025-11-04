package com.practica.exchangecrypto.ui.home.adapter

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.robinhood.spark.SparkAdapter
import com.robinhood.spark.SparkView
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.ui.model.CryptoHorizontal

class CryptoHorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // Initialize views by finding them using their IDs.
    private val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
    private val tvName: TextView = itemView.findViewById(R.id.tvName)
    private val tvSymbol: TextView = itemView.findViewById(R.id.tvSymbol)
    private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    private val tvChange: TextView = itemView.findViewById(R.id.tvChange)
    private val sparkView: SparkView = itemView.findViewById(R.id.sparklineView)

    /**
     * Binds the data from a CryptoHorizontal item to the corresponding views.
     */
    fun bind(item: CryptoHorizontal) {
        // Log binding events (useful for debugging RecyclerView performance).
        Log.d("CRYPTO_BIND", "Binding ${item.name} | Change: ${item.change}")
        Log.d("CRYPTO_BIND", "Sparkline size: ${item.sparkline.size}")

        // --- Image and basic info ---
        // Load image using Coil library, including crossfade and placeholder/error resources.
        imgIcon.load(item.iconUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_error)
        }

        tvName.text = item.name
        tvSymbol.text = item.symbol
        tvPrice.text = item.price

        // --- Change text and color logic ---
        // Safely parse the change percentage string into a float value.
        val rawChangeValue = item.change.replace("%", "").toFloatOrNull() ?: 0f

        if (rawChangeValue < 0) {
            tvChange.text = "${item.change}"
            tvChange.setTextColor(Color.RED)
            // Set a drawable for a downward arrow (loss).
            tvChange.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
        } else {
            // Prepend a "+" sign for positive change.
            tvChange.text = "+${item.change}"
            tvChange.setTextColor(Color.GREEN)
            // Set a drawable for an upward arrow (gain).
            tvChange.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
        }

        // --- Sparkline chart setup ---
        val sparklineList = item.sparkline ?: emptyList()
        if (sparklineList.isNotEmpty()) {
            // Convert to a list of non-null floats (though not strictly used below, good practice).
            val safeList = sparklineList.mapNotNull { it?.toFloat() }
            try {
                // Set the custom SparkAdapter to feed data points to the SparkView.
                sparkView.adapter = object : SparkAdapter() {
                    override fun getCount() = sparklineList.size
                    override fun getItem(index: Int) = sparklineList[index]
                    // Implement getY() to return the price value as Float.
                    override fun getY(index: Int) = sparklineList[index].toFloat()
                }
                // Set the sparkline color based on the 24h price change.
                sparkView.lineColor = if (rawChangeValue < 0) Color.RED else Color.GREEN
            } catch (e: Exception) {
                Log.e("CRYPTO_BIND", "Error setting SparkView", e)
            }
        } else {
            // Clear the chart if no data is available.
            sparkView.adapter = null
            Log.d("CRYPTO_BIND", "No sparkline data for ${item.name}")
        }
    }
}