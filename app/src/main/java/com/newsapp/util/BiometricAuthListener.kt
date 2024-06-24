package com.newsapp.util

import androidx.fragment.app.FragmentActivity

interface BiometricAuthListener {
    fun onAuthSuccess()
    fun onAuthError(errorCode: Int, errString: CharSequence)
    fun onAuthFailed()
}
