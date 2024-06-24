package com.newsapp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.newsapp.presentation.viewmodel.BiometricViewModel
import com.newsapp.util.BiometricAuthListener

@Composable
fun BiometricAuthenticationScreen(context: FragmentActivity, viewModel: BiometricViewModel = viewModel()) {

    val canAuthenticateWithBiometrics by viewModel.canAuthenticateWithBiometrics.collectAsState()

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (canAuthenticateWithBiometrics) {
                BiometricButton(onClick = {
                    viewModel.authenticateBiometric(context, context as BiometricAuthListener)
                }, text = "Authenticate with Biometric")
            } else {
                Text(text = "Biometric authentication is not available on this device.")
            }
        }
    }
}

@Composable
fun BiometricButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = text)
    }
}
