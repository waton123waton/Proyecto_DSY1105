package com.example.proyecto_dsy1105.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class FeriadoApi(
    val date: String,
    val localName: String
)

interface ServicioFeriadosApi {
    @GET("api/v3/PublicHolidays/{year}/CL")
    fun obtenerFeriados(@Path("year") year: Int): Call<List<FeriadoApi>>
}

object ClienteFeriados {
    val servicio: ServicioFeriadosApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://date.nager.at/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServicioFeriadosApi::class.java)
    }
}
