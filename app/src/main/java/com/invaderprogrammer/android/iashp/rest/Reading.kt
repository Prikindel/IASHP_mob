package com.invaderprogrammer.android.iashp.rest

data class Reading(
    val electricity: Int,
    val water: Int,
    val heat: Int,
    val vape: Int,
    val gas: Int,
    val specGas: Int
)