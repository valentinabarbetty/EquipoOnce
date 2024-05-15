package com.uv.dogappuv.view.webService

import com.uv.dogappuv.view.model.BreedsList
import com.uv.dogappuv.view.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(Constants.ENDPOINT)
    suspend fun getDogsBreeds(): MutableList<BreedsList>

    @GET("{breed}/images/random")
    fun getImagenPerro(@Path("breed") breed: String): Call<ImageResponse>
}

data class ImageResponse(
    val message: String,
    val status: String
)