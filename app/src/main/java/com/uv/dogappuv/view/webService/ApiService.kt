package com.uv.dogappuv.view.webService
import com.uv.dogappuv.view.model.BreedsList
import com.uv.dogappuv.view.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
interface ApiService {
    @GET(Constants.ENDPOINT)
    suspend fun getDogsBreeds(): MutableList<BreedsList>
}