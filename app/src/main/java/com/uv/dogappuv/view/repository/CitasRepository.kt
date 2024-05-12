package com.uv.dogappuv.view.repository

import android.content.Context
import com.uv.dogappuv.view.data.CitasDB
import com.uv.dogappuv.view.data.CitasDao
import com.uv.dogappuv.view.model.Citas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CitasRepository(val context: Context) {
    private var citasDao: CitasDao = CitasDB.getDatabase(context).citasDao()
    //private var apiService: ApiService = ApiUtils.getApiService()
    suspend fun saveCita(citas: Citas) {
        withContext(Dispatchers.IO) {
            citasDao.saveCita(citas)
        }
    }

    suspend fun getListCitas(): MutableList<Citas> {
        return withContext(Dispatchers.IO) {
            citasDao.getListCitas()
        }
    }

    suspend fun deleteCita(citas: Citas) {
        withContext(Dispatchers.IO) {
            citasDao.deleteCita(citas)
        }
    }

    suspend fun updateCita(citas: Citas) {
        withContext(Dispatchers.IO) {
            citasDao.updateCita(citas)
        }
    }

    /*suspend fun getProducts(): MutableList<Product> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProducts()
                response
            } catch (e: Exception) {

                e.printStackTrace()
                mutableListOf()
            }
        }
    }*/
}