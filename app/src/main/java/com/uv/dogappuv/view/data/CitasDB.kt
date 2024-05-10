package com.uv.dogappuv.view.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uv.dogappuv.view.model.Citas
import com.uv.dogappuv.view.utils.Constants.NAME_BD

@Database(entities = [Citas::class], version = 1)
abstract class CitasDB : RoomDatabase() {
    abstract fun citasDao(): CitasDao

    companion object {
        fun getDatabase(context: Context): CitasDB {
            return Room.databaseBuilder(
                context.applicationContext,
                CitasDB::class.java,
                NAME_BD
            ).build()
        }
    }
}