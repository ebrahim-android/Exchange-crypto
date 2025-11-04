package com.practica.exchangecrypto.ui.home.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.ui.model.CryptoItem

class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    // View initialization using findViewById.
    private val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
    private val tvName: TextView = itemView.findViewById(R.id.tvName)
    private val tvSymbol: TextView = itemView.findViewById(R.id.tvSymbol)
    private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    private val tvChange: TextView = itemView.findViewById(R.id.tvChange)

    /**
     * Binds the data from a CryptoItem to the respective views.
     */
    fun bind(item: CryptoItem) {
        // --- Load image from URL using Coil ---
        imgIcon.load(item.iconUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_error)
        }

        // --- Bind basic text data ---
        tvName.text = item.name
        tvSymbol.text = item.symbol
        tvPrice.text = item.price

        // --- Handle price change background and formatting ---
        val changeText = item.change
        // Safely parse the percentage string to a float for comparison.
        val rawChangeValue = changeText.replace("%", "").toFloatOrNull() ?: 0f

        tvChange.text = changeText
        tvChange.setTextColor(Color.WHITE)
        // Ensure no drawables are set from previous recycling.
        tvChange.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

        // Apply background color/drawable based on positive or negative change.
        if (rawChangeValue >= 0) {
            // Set positive background resource (e.g., green rounded box).
            tvChange.setBackgroundResource(R.drawable.bg_change_positive)

            // Prepend '+' sign if the change value is positive but the sign is missing.
            if (!changeText.startsWith("+") && rawChangeValue > 0)
                tvChange.text = "+$changeText"
        } else {
            // Set negative background resource (e.g., red rounded box).
            tvChange.setBackgroundResource(R.drawable.bg_change_negative)
        }
    }
}