package com.uv.dogappuv.view.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.uv.dogappuv.R
import com.uv.dogappuv.databinding.FragmentDetalleCitaBinding
import com.uv.dogappuv.view.model.Citas
import com.uv.dogappuv.view.viewmodel.CitasViewModel

class DetalleCitaFragment : Fragment() {
    private lateinit var binding: FragmentDetalleCitaBinding
    private val citasViewModel: CitasViewModel by viewModels()
    private lateinit var receivedCita: Citas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetalleCitaBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataCita()
        controladores()
    }

    private fun controladores() {
        binding.btnDeleteCita.setOnClickListener {
            deleteCita()
        }

        binding.btnEditCita.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("dataCita", receivedCita)
            val detalleCitaFragment = DetalleCitaFragment()
            detalleCitaFragment.arguments = bundle
            findNavController().navigate(R.id.action_itemDetailsFragment_to_itemEditFragment, bundle)
        }


    }

    private fun dataCita() {
        val receivedBundle = arguments
        receivedCita = receivedBundle?.getSerializable("clave") as Citas
        val image = receivedBundle?.getSerializable("imagen")
        binding.ctDetalle.tvTitulo.text = "${receivedCita.nombreMascota}"
        binding.tvRaza.text = "${receivedCita.razaMascota}"
        binding.tvPropietario.text = "${receivedCita.nombrePropietario}"
        binding.tvTelefono.text = "${receivedCita.telefonoPropietario}"
        binding.tvId.text = "#${receivedCita.id}"
        binding.tvSintoma.text = "${receivedCita.sintoma}"
        Glide.with(this).load(image).into(binding.ivFoto)

    }

    private fun deleteCita() {
        citasViewModel.deleteCita(receivedCita)
        citasViewModel.getListCitas()
        findNavController().popBackStack()
    }
    private fun setupToolbar() {
        binding.ctDetalle.tbDetalle.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onBackPressed() {
        findNavController().navigate(R.id.action_fragment_detalle_cita_to_fragment_admin_citas)
    }

}