package com.practica.exchangecrypto.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.ui.model.CryptoItem
import com.practica.exchangecrypto.ui.shared.SharedCryptoViewModel

class CryptoAdapter(
    private var items: List<CryptoItem>,
    // Injects the SharedCryptoViewModel to potentially interact with shared state (though not used directly here).
    private val sharedViewModel: SharedCryptoViewModel,
    // Lambda function executed when an item is clicked.
    private val onItemClick: (CryptoItem) -> Unit
) : RecyclerView.Adapter<CryptoViewHolder>() {

    /**
     * Updates the data set and notifies the RecyclerView to redraw the list.
     */
    fun updateList(newList: List<CryptoItem>) {
        items = newList
        notifyDataSetChanged()
    }

    /**
     * Inflates the layout for a single item view and returns the ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(
            // Inflate the item layout (R.layout.item_crypto).
            LayoutInflater.from(parent.context).inflate(R.layout.item_crypto, parent, false)
        )
    }

    override fun getItemCount() = items.size

    /**
     * Binds the data to the ViewHolder and sets up the click listener.
     */
    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        // Set up the click listener for navigation or interaction.
        holder.itemView.setOnClickListener {
            onItemClick(item) // Executes the lambda passing the selected CryptoItem.
        }

    }
}