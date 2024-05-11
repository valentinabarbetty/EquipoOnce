package com.uv.dogappuv.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.uv.dogappuv.R
import com.uv.dogappuv.databinding.FragmentNuevaCitaBinding
import com.uv.dogappuv.view.model.Citas
import com.uv.dogappuv.view.viewmodel.CitasViewModel
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NuevaCitaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevaCitaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val citasViewModel: CitasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentNuevaCitaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_nueva_cita, container, false)

        setupSpinner(binding)
        controladores(binding)

        return binding.root
    }

    private fun setupSpinner(binding: FragmentNuevaCitaBinding) {
        // Define an array of items for the spinner, including "Síntomas" as the default item
        val items = arrayOf("Síntomas", "Solo duerme", "No come", "Fractura extremidad", "Tiene pulgas", "Tiene garrapatas", "Bota demasiado pelo")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                // Handle the selection event here
                if (position != 0) {
                    val selectedItem = parentView.getItemAtPosition(position) as String
                    // Do something with the selected item
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing when nothing is selected
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NuevaCitaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevaCitaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun controladores(binding: FragmentNuevaCitaBinding) {
//        validarDatos()
        binding.btnSubmit.setOnClickListener {
            saveCita(binding)
        }
    }
    private fun saveCita(binding: FragmentNuevaCitaBinding) {
        val nombreMascota = binding.etNombreMascota.text.toString()
        val raza = binding.etRaza.text.toString()
        val propietario = binding.etPropietario.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val sintoma = binding.spinner.selectedItem.toString()
        val cita = Citas(
            nombreMascota = nombreMascota,
            nombrePropietario = propietario,
            razaMascota = raza,
            sintoma = sintoma,
            telefonoPropietario = telefono
        )
        Log.d("test", "Antes")
        citasViewModel.saveCita(cita)
        Log.d("test", cita.toString())
        Toast.makeText(context, "Cita creada !!", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }
}


