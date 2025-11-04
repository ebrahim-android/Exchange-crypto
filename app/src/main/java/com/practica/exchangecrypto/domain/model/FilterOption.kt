package com.practica.exchangecrypto.ui.model

// Data class representing a filter option displayed on the UI (e.g., in a Chip/RecyclerView).
data class FilterOption(
    // Unique identifier for the filter (often linked to an API sorting parameter or internal logic).
    val id: String,
    // The user-friendly name displayed on the filter chip.
    val displayName: String
)