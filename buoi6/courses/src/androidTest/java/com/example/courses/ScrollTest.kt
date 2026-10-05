package com.example.courses

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
        compose.setContent { MaterialTheme { TopicGrid() } }
        compose.onNodeWithTag("topic_grid").performScrollToIndex(13)
        compose.onNodeWithText(context.getString(R.string.tech)).assertIsDisplayed()
        compose.onNodeWithTag("topic_grid").performScrollToIndex(0)
        compose.onNodeWithText(context.getString(R.string.architecture)).assertIsDisplayed()
    }
}
