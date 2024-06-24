package com.newsapp.presentation.ui

import android.content.Intent
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class MainActivityUnitTest {

    @Test
    fun testOnAuthSuccess() {
        // Arrange
        val controller = Robolectric.buildActivity(MainActivity::class.java).create().start().resume()
        val activity = spy(controller.get())
        doNothing().`when`(activity).finish()

        // Act
        activity.onAuthSuccess()

        // Assert
        verify(activity).startActivity(any(Intent::class.java))
        verify(activity).finish()
    }
}