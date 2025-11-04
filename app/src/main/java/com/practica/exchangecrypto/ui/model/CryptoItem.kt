package com.practica.exchangecrypto.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Data class representing an item displayed in the main vertical list (UI Model).
// The @Parcelize annotation allows this object to be easily passed between Fragments/Activities.
@Parcelize
data class CryptoItem(
    val id: String,                     // Unique identifier for the cryptocurrency (needed for detail fetch/navigation).
    val name: String,
    val symbol: String,
    val price: String,                  // Formatted price string (e.g., "$123.45").
    val change: String,                 // Formatted 24h change string (e.g., "1.23%").
    val iconUrl: String,                // URL link for the crypto's icon/logo from the API.
) : Parcelable