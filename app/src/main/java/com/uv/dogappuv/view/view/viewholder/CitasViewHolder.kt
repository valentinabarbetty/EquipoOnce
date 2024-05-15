package com.uv.dogappuv.view.view.viewholder

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uv.dogappuv.databinding.ItemMascotaBinding
import com.uv.dogappuv.view.model.Citas
import com.uv.dogappuv.view.utils.Constants
import com.uv.dogappuv.view.webService.ApiService
import com.uv.dogappuv.view.webService.ImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CitasViewHolder(binding: ItemMascotaBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController

    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL + Constants.ENDPOINTIMAGE)
            .addConverterFactory(GsonConverterFactory.create()).build()
        retrofit.create(ApiService::class.java)
    }

    fun setItemCita(cita: Citas) {
        getImagenPerro(cita.razaMascota)

        bindingItem.tvNombreMascota.text = cita.nombreMascota
        bindingItem.tvDescripcion.text = "${cita.sintoma}"
        bindingItem.tvId.text = "#${cita.id}"

        bindingItem.cvMascosta.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", cita)
            //navController.navigate(R.id.action_homeInventoryFragment_to_itemDetailsFragment, bundle)
        }
    }

    fun getImagenPerro(razaMascota: String) {
        apiService.getImagenPerro(razaMascota)
            .enqueue(object : Callback<ImageResponse> {
                override fun onResponse(
                    call: Call<ImageResponse>,
                    response: Response<ImageResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        val imageUrl = response.body()?.message.toString()
                        imageUrl?.let {
                            // Carga la imagen utilizando la URL directamente
                            Glide.with(itemView.context).load(it).into(bindingItem.civFotoPerro)
                        }
                    } else {
                        Log.d("test imagen: ", "Respuesta del servidor negativa:" + response.toString())
                    }
                }

                override fun onFailure(
                    call: Call<ImageResponse>,
                    t: Throwable
                ) {
                    Log.e("test Error API", "El consumo de la aplicación ha fallado", t)
                }
            })
    }
}