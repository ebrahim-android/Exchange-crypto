package com.practica.exchangecrypto.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.practica.exchangecrypto.R
import com.practica.exchangecrypto.domain.model.CryptoFilter

class FilterAdapter(
    private val filters: List<CryptoFilter>,
    // Callback function executed when a filter chip is selected.
    private val onFilterSelected: (CryptoFilter) -> Unit
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    // Tracks the index of the currently selected filter chip (default: first item).
    private var selectedPosition = 0

    // Inner ViewHolder class, holding a reference to the Material Chip view.
    inner class FilterViewHolder(val chip: Chip) : RecyclerView.ViewHolder(chip)

    /**
     * Inflates the custom Chip layout and returns the ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val chip = LayoutInflater.from(parent.context)
            // Inflate the Chip layout defined in item_filter_chip.xml.
            .inflate(R.layout.item_filter_chip, parent, false) as Chip
        return FilterViewHolder(chip)
    }

    /**
     * Binds the filter data and handles the click logic.
     */
    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = filters[position]
        // Display name from the filter enum/data class.
        holder.chip.text = filter.displayName

        // Set the 'checked' state based on the selectedPosition index.
        holder.chip.isChecked = position == selectedPosition

        // Set the click listener to handle filter selection.
        holder.chip.setOnClickListener {
            val previous = selectedPosition
            // Update the selected position to the newly clicked item's position.
            selectedPosition = holder.adapterPosition

            // Notify RecyclerView to rebind the previous item (to uncheck it).
            notifyItemChanged(previous)
            // Notify RecyclerView to rebind the current item (to check it).
            notifyItemChanged(selectedPosition)

            // Execute the callback function with the selected filter.
            onFilterSelected(filter)
        }
    }

    override fun getItemCount() = filters.size
}