package com.uv.dogappuv.view.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.uv.dogappuv.databinding.FragmentLoginBinding
import com.uv.dogappuv.R


class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationFragmentAdmin()
    }

    private fun navigationFragmentAdmin() {
        binding.btnFragmentAdmin.setOnClickListener {
            findNavController().navigate(R.id.fragment_admin_citas)
        }
    }
}
