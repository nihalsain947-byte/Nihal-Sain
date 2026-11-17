package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "w411dp-h915dp")
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun app_name_is_nihalx() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        assertEquals("NIHALX", context.getString(R.string.app_name))
    }

    @Test
    fun navigation_and_portfolio_screens_render_correctly() {
        // Verify PremiumLoadingScreen is displayed while initializing, then dismiss
        composeTestRule.onNodeWithTag("loading_overlay").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loading_brand_title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loading_status_text").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loading_overlay").performClick()
        composeTestRule.waitForIdle()

        // Home screen displays Nihal Sain identity
        composeTestRule.onNodeWithTag("screen_home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nihal Sain").assertIsDisplayed()

        // Navigate to About Me
        composeTestRule.onNodeWithTag("nav_item_about").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("screen_about").assertIsDisplayed()
        composeTestRule.onNodeWithText("About Me").assertIsDisplayed()
        composeTestRule.onNodeWithText("Founder • NIHALX").assertIsDisplayed()

        // Navigate to Skills
        composeTestRule.onNodeWithTag("nav_item_skills").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("screen_skills").assertIsDisplayed()
        composeTestRule.onNodeWithText("AI Photo Editing").assertIsDisplayed()

        // Navigate to Projects -> verify zero fake projects empty state and add a real project
        composeTestRule.onNodeWithTag("nav_item_projects").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("screen_projects").assertIsDisplayed()
        composeTestRule.onNodeWithTag("projects_empty_card").assertIsDisplayed()

        composeTestRule.onNodeWithTag("empty_add_project_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("input_project_title").performTextInput("NIHALX Web Studio")
        composeTestRule.onNodeWithTag("input_project_description").performTextInput("Personal portfolio web build.")
        composeTestRule.onNodeWithTag("btn_save_project").performScrollTo().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("NIHALX Web Studio").assertIsDisplayed()

        // Navigate to Services
        composeTestRule.onNodeWithTag("nav_item_services").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("screen_services").assertIsDisplayed()
        composeTestRule.onNodeWithText("Website Development").assertIsDisplayed()

        // Navigate to Contact Me -> verify all 4 action buttons
        composeTestRule.onNodeWithTag("nav_item_contact").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("screen_contact").assertIsDisplayed()
        composeTestRule.onNodeWithTag("contact_call_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("contact_whatsapp_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("contact_email_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("contact_instagram_button").assertIsDisplayed()
    }
}
