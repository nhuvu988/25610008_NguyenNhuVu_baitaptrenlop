package com.example.racetracker

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.racetracker.ui.RaceTrackerApp
import com.example.racetracker.ui.theme.RaceTrackerTheme
import org.junit.Rule
import org.junit.Test

class RaceTrackerUiTest {
    @get:Rule val compose = createComposeRule()

    @Test fun startPauseAndResetKeepControlsUsable() {
        compose.setContent { RaceTrackerTheme { RaceTrackerApp() } }
        compose.onNodeWithText("Start").performClick()
        compose.onNodeWithText("Pause").assertIsDisplayed().performClick()
        compose.onNodeWithText("Start").assertIsDisplayed()
        compose.onNodeWithText("Reset").performClick()
        compose.onAllNodesWithText("0%").assertCountEquals(2)
    }
}
