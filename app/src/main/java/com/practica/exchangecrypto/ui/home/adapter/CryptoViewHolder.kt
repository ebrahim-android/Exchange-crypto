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
    private val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
    private val tvName: TextView = itemView.findViewById(R.id.tvName)
    private val tvSymbol: TextView = itemView.findViewById(R.id.tvSymbol)
    private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    private val tvChange: TextView = itemView.findViewById((R.id.tvChange))

    fun bind(item: CryptoItem) {
        // Load image from URL with Coil
        imgIcon.load(item.iconUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder) // mientras carga
            error(R.drawable.ic_error) // si falla
        }

        // Assign text
        tvName.text = item.name
        tvSymbol.text = item.symbol
        tvPrice.text = item.price

        // --- LÓGICA DE SIGNO Y COLOR MODIFICADA ---
        val changeText = item.change

        if (changeText.contains("-")) {
            // Es una bajada
            tvChange.text = changeText
            tvChange.setTextColor(Color.RED)
            // Asignar flecha hacia abajo
            tvChange.setCompoundDrawablesWithIntrinsicBounds(
                0, 0, R.drawable.ic_arrow_down, 0
            )
        } else {
            // Es una subida
            tvChange.text = "+$changeText"
            tvChange.setTextColor(Color.GREEN) // O Color.parseColor("#4CAF50")
            // Asignar flecha hacia arriba
            tvChange.setCompoundDrawablesWithIntrinsicBounds(
                0, 0, R.drawable.ic_arrow_up, 0
            )
        }
        // ------------------------------------------
    }
}