package com.newsapp.presentation.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testButton() {
        composeRule.onNodeWithText("Authenticate with Biometric").assertIsDisplayed()
        composeRule.onNodeWithText("Authenticate with Biometric").performClick()

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.findObject(UiSelector().text("Cancel")).click()

        Thread.sleep(1000)
        device.findObject(UiSelector().text("Authentication Required")).exists()
    }

    @Test
    fun testRetryButton() {
        composeRule.onNodeWithText("Authenticate with Biometric").performClick()

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.findObject(UiSelector().text("Cancel")).click()

        val dialogTitle = device.findObject(UiSelector().text("Authentication Required"))
        if (!dialogTitle.waitForExists(2000)) {
            throw AssertionError("Dialog not found")
        }

        device.findObject(UiSelector().text("RETRY")).click()

        device.findObject(UiSelector().text("Biometric Authentication")).exists()
    }

    @Test
    fun testExitButton() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        composeRule.onNodeWithText("Authenticate with Biometric").performClick()

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.findObject(UiSelector().text("Cancel")).click()

        val dialogTitle = device.findObject(UiSelector().text("Authentication Required"))
        if (!dialogTitle.waitForExists(2000)) {
            throw AssertionError("Dialog not found")
        }

        device.findObject(UiSelector().text("EXIT")).click()

        activityScenario.onActivity { activity ->
            activity.finish()
        }

        activityScenario.onActivity { activity ->
            assert(activity.isFinishing)
        }
    }
}
