package com.uv.dogappuv.view.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Citas(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreMascota: String,
    val razaMascota: String,
    val nombrePropietario: String,
    val telefonoPropietario: String,
    val sintoma: String
) : Serializable
