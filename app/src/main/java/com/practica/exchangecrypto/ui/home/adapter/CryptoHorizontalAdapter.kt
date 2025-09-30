package com.practica.exchangecrypto.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.ui.model.CryptoHorizontal

class CryptoHorizontalAdapter(
    private var items: List<CryptoHorizontal>
) : RecyclerView.Adapter<CryptoHorizontalViewHolder>() {

    fun updateList(List: List<CryptoHorizontal>) {
        items = List
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHorizontalViewHolder {
        return CryptoHorizontalViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_crypto_horizontal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CryptoHorizontalViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
