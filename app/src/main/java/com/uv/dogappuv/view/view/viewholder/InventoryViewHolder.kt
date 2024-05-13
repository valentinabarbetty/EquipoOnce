package com.uv.dogappuv.view.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.uv.dogappuv.R
import com.uv.dogappuv.databinding.ItemMascotaBinding
import com.uv.dogappuv.view.model.Citas

class CitasViewHolder(binding: ItemMascotaBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController
    fun setItemCita(cita: Citas) {
        bindingItem.tvNombreMascota.text = cita.nombreMascota
        bindingItem.tvDescripcion.text = "$ ${cita.sintoma}"
        bindingItem.tvId.text = "${cita.id}"

        bindingItem.cvMascosta.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", cita)
            //navController.navigate(R.id.action_homeInventoryFragment_to_itemDetailsFragment, bundle)
        }

    }
}