package com.practica.exchangecrypto.ui.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import android.graphics.Color
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
        tvChange.text = item.change

        // Verde o rojo según cambio
        if (item.change.contains("-")) {
            tvChange.setTextColor(Color.RED)
        } else {
            tvChange.setTextColor(Color.GREEN)
        }
    }
}