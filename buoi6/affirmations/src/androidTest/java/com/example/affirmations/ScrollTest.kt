package com.example.affirmations

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test

class ScrollTest {
    @get:Rule val compose = createComposeRule()

    @Test fun lastItemCanBeReachedByScrolling() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        compose.setContent { MaterialTheme { AffirmationsApp() } }
        compose.onNodeWithTag("affirmation_list").performScrollToIndex(9)
        compose.onNodeWithText(context.getString(R.string.affirmation10)).assertIsDisplayed()
        compose.onNodeWithTag("affirmation_list").performScrollToIndex(0)
        compose.onNodeWithText(context.getString(R.string.affirmation1)).assertIsDisplayed()
    }
}
