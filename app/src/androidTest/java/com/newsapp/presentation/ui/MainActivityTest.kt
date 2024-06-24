package com.newsapp.presentation.ui

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.FragmentActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newsapp.presentation.viewmodel.BiometricViewModel
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    private lateinit var mockViewModel: BiometricViewModel
    private lateinit var mockFragmentActivity: FragmentActivity
    private val application: Application = mockk()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp()
    {
        // Mock ViewModel
        mockViewModel = spyk(BiometricViewModel(application))

        // Mock FragmentActivity
        mockFragmentActivity = mock(MainActivity::class.java)

        // Mock ViewModel behavior
        `when`(mockViewModel.canAuthenticateWithBiometrics)
            .thenReturn(mockViewModel.canAuthenticateWithBiometrics)

        composeTestRule.setContent {
            BiometricAuthenticationScreen(context = mockFragmentActivity, viewModel = mockViewModel)
        }
    }

    @Test
    fun testBiometricButtonDisplayed() {
        composeTestRule.onNodeWithText("Authenticate with Biometric").assertIsDisplayed()
    }

    @Test
    fun testBiometricNotAvailableTextDisplayed() {
        composeTestRule.onNodeWithText("Biometric authentication is not available on this device.").assertIsDisplayed()
    }
}