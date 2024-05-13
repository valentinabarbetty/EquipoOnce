package com.uv.dogappuv.view.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.uv.dogappuv.databinding.ItemMascotaBinding
import com.uv.dogappuv.view.model.Citas
import com.uv.dogappuv.view.view.viewholder.CitasViewHolder

class CitasAdapter(
    private val listCita: MutableList<Citas>, private val navController: NavController
) : RecyclerView.Adapter<CitasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasViewHolder {
        val binding = ItemMascotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitasViewHolder(binding, navController)
    }

    override fun getItemCount(): Int {
        return listCita.size
    }

    override fun onBindViewHolder(holder: CitasViewHolder, position: Int) {
        val cita = listCita[position]
        holder.setItemCita(cita)
    }
}