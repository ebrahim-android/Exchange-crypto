package com.practica.exchangecrypto.ui.home.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.robinhood.spark.SparkAdapter
import com.robinhood.spark.SparkView
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.ui.model.CryptoItem

class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
    private val tvName: TextView = itemView.findViewById(R.id.tvName)
    private val tvSymbol: TextView = itemView.findViewById(R.id.tvSymbol)
    private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    private val tvChange: TextView = itemView.findViewById(R.id.tvChange)

    fun bind(item: CryptoItem) {
        // --- Load image from URL ---
        imgIcon.load(item.iconUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_error)
        }

        // --- Bind text data ---
        tvName.text = item.name
        tvSymbol.text = item.symbol
        tvPrice.text = item.price

        // --- Handle price change text and color ---
        val changeText = item.change
        val rawChangeValue = changeText.replace("%", "").toFloatOrNull() ?: 0f

        tvChange.text = changeText
        tvChange.setTextColor(Color.WHITE)
        tvChange.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        if (rawChangeValue >= 0) {
            tvChange.setBackgroundResource(R.drawable.bg_change_positive)
            if (!changeText.startsWith("+") && rawChangeValue > 0)
                tvChange.text = "+$changeText"
        } else {
            tvChange.setBackgroundResource(R.drawable.bg_change_negative)
        }
    }
}
