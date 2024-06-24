package com.newsapp.presentation.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.newsapp.presentation.viewmodel.BiometricViewModel
import com.newsapp.util.BiometricAuthListener
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BiometricAuthListener {
    val mainViewModel: BiometricViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BiometricAuthenticationScreen(this,mainViewModel)
        }
    }

    override fun onAuthSuccess() {
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onAuthError(errorCode: Int, errString: CharSequence) {
        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
            showWarningDialog()
        }
    }

    override fun onAuthFailed() {
    }

    fun showWarningDialog() {
        AlertDialog.Builder(this)
            .setTitle("Authentication Required")
            .setMessage("You must authenticate yourself to continue using the app. If you do not, you will be thrown out of the app.")
            .setPositiveButton("Retry") { dialog, _ ->
                mainViewModel.authenticateBiometric(this@MainActivity, this)
                dialog.dismiss()
            }
            .setNegativeButton("Exit") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setCancelable(false)
            .show()
    }
}