package com.example.lunchtray
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.lunchtray.datasource.DataSource
import org.junit.Rule
import org.junit.Test
class LunchNavigationTest {
    @get:Rule val compose=createAndroidComposeRule<MainActivity>()
    private fun click(text: String) {
        compose.onNodeWithText(text, ignoreCase = true).performClick()
    }
    private fun selectItem(name: String) {
        compose.onNode(
            hasClickAction() and hasText(name)
        ).performScrollTo().performClick()
    }
    @Test fun completeOrderReturnsToStart() {
        compose.onNodeWithText("Start Order",ignoreCase=true).performClick()
        selectItem(DataSource.entreeMenuItems.first().name)
        click("Next")
        selectItem(DataSource.sideDishMenuItems.first().name)
        click("Next")
        selectItem(DataSource.accompanimentMenuItems.first().name)
        click("Next")
        click("Submit")
        compose.onNodeWithText("Start Order",ignoreCase=true).assertExists()
    }
    @Test fun cancelAndBackReturnToStart() {
        compose.onNodeWithText("Start Order",ignoreCase=true).performClick()
        compose.onNodeWithContentDescription("Back").performClick()
        compose.onNodeWithText("Start Order",ignoreCase=true).performClick()
        click("Cancel")
        compose.onNodeWithText("Start Order",ignoreCase=true).assertExists()
    }
}
