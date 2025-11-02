package com.practica.exchangecrypto.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.ui.model.CryptoItem
import com.practica.exchangecrypto.ui.shared.SharedCryptoViewModel

class CryptoAdapter(
    private var items: List<CryptoItem>,
    private val sharedViewModel: SharedCryptoViewModel, // we inject the SharedCryptoViewModel
    private val onItemClick: (CryptoItem) -> Unit
) : RecyclerView.Adapter<CryptoViewHolder>() {

    fun updateList(newList: List<CryptoItem>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_crypto, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClick(item) // we pass the selected CryptoItem
        }

    }
}


