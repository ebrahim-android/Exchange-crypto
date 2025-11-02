package com.practica.exchangecrypto.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoItem(
    val id: String,                     // 👈 Agregado
    val name: String,
    val symbol: String,
    val price: String,
    val change: String,
    val iconUrl: String, //Here we're gonna save the link from API
) : Parcelable
