package com.uv.dogappuv.view.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.uv.dogappuv.R
import com.uv.dogappuv.databinding.FragmentAdminCitasBinding
import com.uv.dogappuv.view.view.adapter.CitasAdapter
import com.uv.dogappuv.view.viewmodel.CitasViewModel

class AdminCitasFragment : Fragment() {
    private lateinit var binding: FragmentAdminCitasBinding
    private val citasViewModel: CitasViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminCitasBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controladores();
        observadorViewModel();

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Mueve la tarea al fondo
                    requireActivity().moveTaskToBack(true)
                }
            }
        )
    }

    private fun controladores() {
        binding.btnFragmentNuevaCita.setOnClickListener {
            findNavController().navigate(R.id.fragment_nueva_cita)
        }
    }

    private fun observadorViewModel() {
        observerListCitas()
        observerProgress()
    }

    private fun observerListCitas() {
        citasViewModel.getListCitas()
        citasViewModel.listCitas.observe(viewLifecycleOwner) { listCitas ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = CitasAdapter(listCitas, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun observerProgress() {
        citasViewModel.progresState.observe(viewLifecycleOwner) { status ->
            binding.progress.isVisible = status
        }
    }
}