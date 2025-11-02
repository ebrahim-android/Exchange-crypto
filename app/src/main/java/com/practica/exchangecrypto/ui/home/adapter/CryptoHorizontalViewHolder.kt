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

    private val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
    private val tvName: TextView = itemView.findViewById(R.id.tvName)
    private val tvSymbol: TextView = itemView.findViewById(R.id.tvSymbol)
    private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    private val tvChange: TextView = itemView.findViewById(R.id.tvChange)
    private val sparkView: SparkView = itemView.findViewById(R.id.sparklineView)

    fun bind(item: CryptoHorizontal) {
        Log.d("CRYPTO_BIND", "Binding ${item.name} | Change: ${item.change}")
        Log.d("CRYPTO_BIND", "Sparkline size: ${item.sparkline.size}")

        // --- Image and basic info ---
        imgIcon.load(item.iconUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_error)
        }

        tvName.text = item.name
        tvSymbol.text = item.symbol
        tvPrice.text = item.price

        // --- Change text and color ---
        val rawChangeValue = item.change.replace("%", "").toFloatOrNull() ?: 0f
        if (rawChangeValue < 0) {
            tvChange.text = "${item.change}"
            tvChange.setTextColor(Color.RED)
            tvChange.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
        } else {
            tvChange.text = "+${item.change}"
            tvChange.setTextColor(Color.GREEN)
            tvChange.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
        }

        // --- Sparkline safely ---
        val sparklineList = item.sparkline ?: emptyList()
        if (sparklineList.isNotEmpty()) {
            val safeList = sparklineList.mapNotNull { it?.toFloat() }
            try {
                sparkView.adapter = object : SparkAdapter() {
                    override fun getCount() = sparklineList.size
                    override fun getItem(index: Int) = sparklineList[index]
                    override fun getY(index: Int) = sparklineList[index].toFloat()
                }
                sparkView.lineColor = if (rawChangeValue < 0) Color.RED else Color.GREEN
            } catch (e: Exception) {
                Log.e("CRYPTO_BIND", "Error setting SparkView", e)
            }
        } else {
            sparkView.adapter = null // vaciar gráfico si no hay datos
            Log.d("CRYPTO_BIND", "No sparkline data for ${item.name}")
        }
    }
}