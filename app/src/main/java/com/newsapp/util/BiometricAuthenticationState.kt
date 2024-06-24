package com.newsapp.util

sealed class BiometricAuthenticationState {
    data object Success : BiometricAuthenticationState()
    data class Error(val errorCode: Int, val errorMessage: String) : BiometricAuthenticationState()
    data object Failure : BiometricAuthenticationState()
}