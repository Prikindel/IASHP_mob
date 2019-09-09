package com.invaderprogrammer.android.iashp.rest

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SmisApi {
    //Запрос списка предприятий
    @GET("api/getzavods.php")
    fun getZavod(

    ): Observable<List<Zavod>>

    //Запрос списка сенсоров
    @GET("api/getsensors.php")
    fun getSensor(
        @Query("zavod") id: Int
    ): Observable<List<Sensor>>

    @GET("api/sensor-update.php")
    fun setSensor(
        @Query("sensor") sensor: String,
        @Query("id") id: Int,
        @Query("threat") onoff: String
    ): Observable<Sensor>

    @GET("api/getreading.php")
    fun getReading(
        @Query("zavod") id: Int
    ): Observable<Reading>

    @GET("api/setread.php")
    fun setReading(
        @Query("read") read: String,
        @Query("id") id: Int,
        @Query("value") value: Int
    ): Observable<Reading>
}