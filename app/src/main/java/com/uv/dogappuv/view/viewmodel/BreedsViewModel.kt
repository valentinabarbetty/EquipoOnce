package com.uv.dogappuv.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uv.dogappuv.view.model.BreedsList
import com.uv.dogappuv.view.repository.CitasRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import dagger.hilt.android.lifecycle.HiltViewModel
@HiltViewModel
class BreedsViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val citasRepository = CitasRepository(context)
    private val _breedsResponse = MutableLiveData<Response<BreedsList>?>()

    private val _listBreeds = MutableLiveData<MutableList<BreedsList>>()
    val listBreeds: LiveData<MutableList<BreedsList>> = _listBreeds

  fun getBreeds(){
      viewModelScope.launch {
          /*_progressState.value = true*/
          try {
              _listBreeds.value = citasRepository.getBreeds()
              /*_progressState.value = false*/
          } catch (e: Exception){
              /*progressState.value = false*/
          }}
  }
}