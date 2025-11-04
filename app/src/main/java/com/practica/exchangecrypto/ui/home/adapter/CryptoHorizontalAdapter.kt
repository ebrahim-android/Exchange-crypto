package com.practica.exchangecrypto.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.ui.model.CryptoHorizontal

// Adapter for the horizontal list (e.g., Top 5 cryptos).
class CryptoHorizontalAdapter(
    private var items: List<CryptoHorizontal>
) : RecyclerView.Adapter<CryptoHorizontalViewHolder>() {

    /**
     * Updates the adapter's data set and signals the RecyclerView to redraw.
     */
    fun updateList(List: List<CryptoHorizontal>) {
        items = List
        notifyDataSetChanged()
    }

    /**
     * Inflates the layout specific for the horizontal items.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHorizontalViewHolder {
        return CryptoHorizontalViewHolder(
            // Inflate the horizontal item layout (R.layout.item_crypto_horizontal).
            LayoutInflater.from(parent.context).inflate(R.layout.item_crypto_horizontal, parent, false)
        )
    }

    /**
     * Binds the data item to the ViewHolder.
     */
    override fun onBindViewHolder(holder: CryptoHorizontalViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}