package com.uv.dogappuv.view.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.uv.dogappuv.R
import com.uv.dogappuv.databinding.FragmentDetalleCitaBinding
import com.uv.dogappuv.databinding.FragmentEditarBinding
import com.uv.dogappuv.view.model.Citas
import com.uv.dogappuv.view.viewmodel.CitasViewModel
import androidx.navigation.fragment.findNavController
import com.uv.dogappuv.databinding.FragmentNuevaCitaBinding
import com.uv.dogappuv.view.webService.ApiService
import com.uv.dogappuv.view.webService.DogBreedsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var receivedCita: Citas

/**
 * A simple [Fragment] subclass.
 * Use the [EditarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val citasViewModel: CitasViewModel by viewModels()
    private lateinit var binding: FragmentEditarBinding


    private lateinit var apiService: ApiService
    private val breedsList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditarBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setupToolbar() // Llama a setupToolbar antes de devolver la vista
        dataDog() // Llama a dataDog antes de devolver la vista
        controladores(binding) // Llama a controladores antes de devolver la vista
        binding.btnEdit.isEnabled = false
        return binding.root // Devuelve la vista después de realizar las configuraciones necesarias
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataDog()
        fetchBreeds()
        controladores(binding)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun dataDog(){
        val receivedBundle = arguments
        receivedCita = receivedBundle?.getSerializable("dataCita") as Citas
        binding.etNombreMascota.setText(receivedCita.nombreMascota)
        binding.etRaza.setText(receivedCita.razaMascota)
        binding.etPropietario.setText(receivedCita.nombrePropietario.toString())
        binding.etTelefono.setText(receivedCita.telefonoPropietario.toString())

    }

    private fun updateCita(binding: FragmentEditarBinding){
        val nombreMascota = binding.etNombreMascota.text.toString()
        val raza = binding.etRaza.text.toString()
        val propietario = binding.etPropietario.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val cita = Citas(receivedCita.id, nombreMascota, raza, propietario, telefono,"" )
        citasViewModel.updateCita(cita)
        findNavController().navigate(R.id.action_itemEditFragment_to_homeFragment)

    }
    private fun controladores(binding: FragmentEditarBinding) {
        validarDatos()

        // Initially disable the submit button
        if (binding.btnEdit.isEnabled) {
            binding.btnEdit.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.darker_gray
                )
            )
        } else {
            binding.btnEdit.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.white
                )
            )
        }
        binding.btnEdit.setOnClickListener {
                // Llama a la función para guardar la cita si se ha seleccionado otro síntoma
                updateCita(binding)
            }
        }


    private fun validarDatos() {
        val listEditText = listOf(
            binding.etRaza,
            binding.etPropietario,
            binding.etTelefono,
            binding.etNombreMascota
        )

        for (editText in listEditText) {
            editText.addTextChangedListener {
                val isListFull = listEditText.all {
                    it.text?.isNotEmpty() ?: false // si toda la lista no está vacía
                }
                binding.btnEdit.isEnabled = isListFull
                // Si todos los campos están llenos, cambia el color del texto a blanco
                if (isListFull) {
                    binding.btnEdit.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.white
                        )
                    )
                }
            }
        }
    }
    private fun fetchBreeds() {
        apiService.getBreedsList().enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(call: Call<DogBreedsResponse>, response: Response<DogBreedsResponse>) {
                if (response.isSuccessful) {
                    val breedsResponse = response.body()
                    breedsResponse?.let {
                        if (it.status == "success") {
                            val breedsMap = it.message
                            val breedsList = mutableListOf<String>()
                            breedsMap.values.forEach { list ->
                                breedsList.addAll(list)
                            }
                            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breedsList)
                            binding.etRaza.setAdapter(adapter)

                            logBreedsList(breedsList)
                        }
                    }
                } else {
                    Log.e("fetchBreeds", "Failed to fetch breeds. Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                Log.e("fetchBreeds", "Error fetching breeds", t)
                Toast.makeText(requireContext(), "Error fetching breeds", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun logBreedsList(breedsList: List<String>) {
        for (breed in breedsList) {
            Log.d("BreedsList", breed)
        }
    }
    private fun setupToolbar() {
        binding.contentToolbarEditar.toolbarEdit.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onBackPressed() {
        // Navegar de regreso a la pantalla de detalle de la cita
        findNavController().navigateUp()
    }

}