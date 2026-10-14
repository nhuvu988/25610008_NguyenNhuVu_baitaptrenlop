package com.example.inventory

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InventoryUiTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()

    @Test fun addSellEditAndDeletePersistThroughTheUi() {
        val name = "Buoi10-${System.nanoTime()}"
        compose.onNodeWithContentDescription("Add Item").performClick()
        compose.onNodeWithText("Save").assertIsNotEnabled()
        compose.onNodeWithText("Item Name*").performTextInput(name)
        compose.onNodeWithText("Item Price*").performTextInput("10.5")
        compose.onNodeWithText("Quantity in Stock*").performTextInput("1")
        compose.onNodeWithText("Save").performScrollTo().performClick()
        waitForText(name)
        compose.activityRule.scenario.recreate()
        waitForText(name)
        compose.onNodeWithText(name).performClick()
        waitForText(name)
        compose.onNodeWithText("Sell").performClick()
        compose.waitUntil(10_000) { compose.onAllNodesWithText("0").fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText("Sell").assertIsNotEnabled()
        compose.onNodeWithContentDescription("Edit Item").performClick()
        compose.onNodeWithText("Item Name*").performTextReplacement("$name-edited")
        compose.onNodeWithText("Quantity in Stock*").performTextReplacement("2")
        compose.onNodeWithText("Save").performScrollTo().performClick()
        waitForText("$name-edited")
        compose.onNodeWithText("Delete").performScrollTo().performClick()
        compose.onNodeWithText("No").performClick()
        compose.onNodeWithText("$name-edited").assertExists()
        compose.onNodeWithText("Delete").performClick()
        compose.onNodeWithText("Yes").performClick()
        compose.waitUntil(10_000) { compose.onAllNodesWithText("$name-edited").fetchSemanticsNodes().isEmpty() }
        compose.onNodeWithContentDescription("Add Item").assertExists()
    }

    private fun waitForText(text: String) {
        compose.waitUntil(10_000) { compose.onAllNodesWithText(text).fetchSemanticsNodes().isNotEmpty() }
    }
}
