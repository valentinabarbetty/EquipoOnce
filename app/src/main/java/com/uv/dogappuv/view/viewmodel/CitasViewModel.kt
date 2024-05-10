package com.uv.dogappuv.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uv.dogappuv.view.model.Citas
import com.uv.dogappuv.view.repository.CitasRepository
import kotlinx.coroutines.launch

class CitasViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val citasRepository = CitasRepository(context)


    private val _listCitas = MutableLiveData<MutableList<Citas>>()
    val listCitas: LiveData<MutableList<Citas>> get() = _listCitas

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    fun saveCita(citas: Citas) {
        viewModelScope.launch {

            _progresState.value = true
            try {
                citasRepository.saveCita(citas)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun getListCitas() {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listCitas.value = citasRepository.getListCitas()
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }

        }
    }

    fun deleteCita(citas: Citas) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                citasRepository.deleteCita(citas)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }

        }
    }

    fun updateCita(citas: Citas) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                citasRepository.updateCita(citas)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }
}

