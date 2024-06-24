package com.newsapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import com.newsapp.util.BiometricAuthListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BiometricViewModel(application: Application) : AndroidViewModel(application) {

    private val _canAuthenticateWithBiometrics = MutableStateFlow(false)
    val canAuthenticateWithBiometrics: StateFlow<Boolean> = _canAuthenticateWithBiometrics

    init {
        checkBiometricSupport()
    }

    fun checkBiometricSupport() {
        val biometricManager = BiometricManager.from(getApplication<Application>().applicationContext)
        _canAuthenticateWithBiometrics.value = when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> {
                Log.e("TAG", "Device does not support strong biometric authentication")
                false
            }
        }
    }

    fun authenticateBiometric(context: FragmentActivity, listener: BiometricAuthListener) {
        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(context, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                listener.onAuthSuccess()
                Log.d("TAG", "Authentication successful!!!")
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                listener.onAuthError(errorCode, errString)
                Log.e("TAG", "onAuthenticationError")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                listener.onAuthFailed()
                Log.e("TAG", "onAuthenticationFailed")
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setDescription("Place your finger on the sensor or look at the front camera to authenticate.")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}