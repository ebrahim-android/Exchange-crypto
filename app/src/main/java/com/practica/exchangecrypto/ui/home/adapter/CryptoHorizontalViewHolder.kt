package com.practica.exchangecrypto.ui.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import android.graphics.Color
import androidx.core.content.ContextCompat // Necesario para cargar drawables
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.ui.model.CryptoHorizontal

class CryptoHorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
    private val tvName: TextView = itemView.findViewById(R.id.tvName)
    private val tvSymbol: TextView = itemView.findViewById(R.id.tvSymbol)
    private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
    private val tvChange: TextView = itemView.findViewById(R.id.tvChange)

    fun bind(item: CryptoHorizontal) {
        // Cargar imagen desde URL con Coil
        imgIcon.load(item.iconUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_placeholder) // mientras carga
            error(R.drawable.ic_error) // si falla
        }

        // Asignar textos
        tvName.text = item.name
        tvSymbol.text = item.symbol
        tvPrice.text = item.price

        // --- LÓGICA DE SIGNO, COLOR Y FLECHA MODIFICADA ---
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