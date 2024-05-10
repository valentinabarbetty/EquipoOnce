package com.uv.dogappuv.view.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import  com.uv.dogappuv.view.model.Citas

@Dao
interface CitasDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCita(citas: Citas)

    @Query("SELECT * FROM Citas")
    suspend fun getListCitas(): MutableList<Citas>

    @Delete
    suspend fun deleteCita(citas: Citas)

    @Update
    suspend fun updateCita(citas: Citas)
}