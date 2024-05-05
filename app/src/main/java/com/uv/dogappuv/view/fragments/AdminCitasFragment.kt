package com.uv.dogappuv.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uv.dogappuv.R
import com.uv.dogappuv.databinding.FragmentAdminCitasBinding


class AdminCitasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentAdminCitasBinding

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

        navigationFragmentNuevaCita()
    }
    private fun navigationFragmentNuevaCita(){
        binding.btnFragmentNuevaCita.setOnClickListener {
            findNavController().navigate(R.id.fragment_nueva_cita)
        }
    }
}