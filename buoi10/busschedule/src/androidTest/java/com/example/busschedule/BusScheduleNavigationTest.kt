package com.example.busschedule

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BusScheduleNavigationTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun selectedStopAndTitleSurviveRecreationAndUpReturnsToAllStops() {
        compose.waitUntil(10_000) { compose.onAllNodesWithText("Main Street").fetchSemanticsNodes().isNotEmpty() }
        compose.onAllNodesWithText("Main Street")[0].performClick()
        compose.onNodeWithText("Main Street Stop").assertIsDisplayed()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("Main Street").assertIsDisplayed()
        compose.onNodeWithText("Main Street Stop").assertIsDisplayed()
        compose.onNodeWithContentDescription("Back").performClick()
        compose.onNodeWithText("Bus Schedule").assertIsDisplayed()
        compose.onNodeWithText("Stop Name").assertIsDisplayed()
    }
}
