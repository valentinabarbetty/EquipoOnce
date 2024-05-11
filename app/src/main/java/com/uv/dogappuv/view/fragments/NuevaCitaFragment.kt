package com.uv.dogappuv.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.uv.dogappuv.R
import com.uv.dogappuv.databinding.FragmentNuevaCitaBinding
import com.uv.dogappuv.view.model.Citas
import com.uv.dogappuv.view.viewmodel.CitasViewModel
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.uv.dogappuv.view.model.BreedsList
import com.uv.dogappuv.view.viewmodel.BreedsViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NuevaCitaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class NuevaCitaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val citasViewModel by viewModels<CitasViewModel>()


    private lateinit var binding: FragmentNuevaCitaBinding





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

        binding = FragmentNuevaCitaBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setupSpinner(binding)
        controladores(binding)
        setupToolbar()
        observerViewModel()
       /* breedsViewModel.getBreeds()*/

       /* get_breeds()
*/      binding.btnSubmit.isEnabled = false

        return binding.root
    }


    private fun observerViewModel() {
        observerListBreeds()
    }
    private fun observerListBreeds(){
    /*    citasViewModel.getBreeds()
        citasViewModel.listBreeds.observe(viewLifecycleOwner){
            lista ->
            val breed = lista[2]
            binding.etRaza.setText(breed.toString())

        }*/
    }

    private fun setupToolbar(){
        binding.contentToolbar.toolbar.setNavigationOnClickListener { onBackPressed() }
    }
    private fun onBackPressed() {
        findNavController().navigate(R.id.action_go_back_home)
    }

    private fun setupSpinner(binding: FragmentNuevaCitaBinding) {
        val items = arrayOf("Síntomas", "Solo duerme", "No come", "Fractura extremidad", "Tiene pulgas", "Tiene garrapatas", "Bota demasiado pelo")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter


    }


    companion object {

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
        validarDatos()


        // Initially disable the submit button
        if (binding.btnSubmit.isEnabled) {
            binding.btnSubmit.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
        } else {
            binding.btnSubmit.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        }
        binding.btnSubmit.setOnClickListener {
            val selectedSintoma = binding.spinner.selectedItem.toString()
            if (selectedSintoma == "Síntomas") {
                // Muestra el mensaje emergente si "Síntomas" está seleccionado
                Toast.makeText(requireContext(), "Selecciona un síntoma", Toast.LENGTH_SHORT).show()
            } else {
                // Llama a la función para guardar la cita si se ha seleccionado otro síntoma
                saveCita(binding)
            }
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
    private fun validarDatos() {
        val listEditText = listOf(binding.etRaza, binding.etPropietario, binding.etTelefono, binding.etNombreMascota)

        for (editText in listEditText) {
            editText.addTextChangedListener {
                val isListFull = listEditText.all {
                    it.text?.isNotEmpty() ?: false // si toda la lista no está vacía
                }
                binding.btnSubmit.isEnabled = isListFull
                // Si todos los campos están llenos, cambia el color del texto a blanco
                if (isListFull) {
                    binding.btnSubmit.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                }
            }
        }
    }




}


