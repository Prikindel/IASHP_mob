package com.invaderprogrammer.android.iashp.rest

data class Sensor(
    val name: String,
    var sensor: Int,
    var position: Int = 0   // Позиция в БД
)