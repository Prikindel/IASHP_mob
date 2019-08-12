package com.invaderprogrammer.android.iashp.rest

import com.google.gson.annotations.SerializedName

data class Reading(
    val electricity: Int,
    val water: Int,
    val heat: Int,
    val vape: Int,
    val gas: Int,
    @SerializedName("spec_gas")
    val specGas: Int
)