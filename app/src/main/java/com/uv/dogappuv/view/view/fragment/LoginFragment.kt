package com.uv.dogappuv.view.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.uv.dogappuv.R
import com.uv.dogappuv.view.model.BiometricPromptManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    private lateinit var biometricPromptManager: BiometricPromptManager
    private lateinit var animationView: LottieAnimationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize BiometricPromptManager
        biometricPromptManager = BiometricPromptManager(requireActivity() as AppCompatActivity)

        // Get reference to LottieAnimationView
        animationView = view.findViewById(R.id.animation_view)

        // Show biometric prompt when animation view is clicked
        animationView.setOnClickListener {
            showBiometricPrompt()
        }

        //view.findViewById<LottieAnimationView>(R.id.animation_view).setOnClickListener {
          //  showBiometricPrompt()
        //}


        // Observe biometric prompt results
        lifecycleScope.launch {
            biometricPromptManager.promptResults.collect { result ->
                handleBiometricPromptResult(result)
            }
        }

        return view
    }

    private fun showBiometricPrompt() {
        biometricPromptManager.showBiometricPrompt(
            title = "Biometric Authentication",
            description = "Please authenticate using your biometric credential"
        )
    }

    private fun handleBiometricPromptResult(result: BiometricPromptManager.BiometricResult) {
        when (result) {
            is BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                findNavController().navigate(R.id.fragment_admin_citas)
            }
            is BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
            }
            is BiometricPromptManager.BiometricResult.AuthenticationError -> {

            }
            // Handle other results as needed
            else -> {
                // Handle other results as needed
            }
        }
    }
}




 //   private fun navigationFragmentAdmin() {
   //     binding.btnFragmentAdmin.setOnClickListener {
     //       findNavController().navigate(R.id.fragment_admin_citas)
  //      }
   // }




