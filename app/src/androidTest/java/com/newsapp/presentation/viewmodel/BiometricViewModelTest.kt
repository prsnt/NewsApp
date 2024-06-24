package com.newsapp.presentation.viewmodel

import android.app.Application
import androidx.biometric.BiometricManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class BiometricViewModelTest {

    private lateinit var biometricViewModel: BiometricViewModel
    private lateinit var application: Application

    @Mock
    private lateinit var biometricManager: BiometricManager

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        application = Mockito.mock(Application::class.java)
        biometricViewModel = BiometricViewModel(application)
        Mockito.`when`(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG))
            .thenReturn(BiometricManager.BIOMETRIC_SUCCESS)
    }

    @Test
    fun testBiometricIsSupported() = runTest {
        biometricViewModel.checkBiometricSupport()
        assertEquals(true, biometricViewModel.canAuthenticateWithBiometrics.value)
    }

    @Test
    fun testBiometricNotSupported() = runTest {
        Mockito.`when`(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG))
            .thenReturn(BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE)
        biometricViewModel.checkBiometricSupport()
        assertEquals(false, biometricViewModel.canAuthenticateWithBiometrics.value)
    }
}