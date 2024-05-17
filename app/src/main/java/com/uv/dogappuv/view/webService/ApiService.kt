package com.uv.dogappuv.view.webService

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("breeds/list/all")
    fun getBreedsList(): Call<DogBreedsResponse>

    @GET("{breed}/images/random")
    fun getImagenPerro(@Path("breed") breed: String): Call<ImageResponse>
}

data class ImageResponse(
    val message: String,
    val status: String
)

data class DogBreedsResponse(
    val message: Map<String,
            List<String>>,
    val status: String
)