package com.example.inventory

import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetails
import com.example.inventory.ui.item.ItemEntryViewModel
import com.example.inventory.ui.item.isValid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Test
import androidx.lifecycle.viewModelScope

@OptIn(ExperimentalCoroutinesApi::class)
class InventoryViewModelTest {
    private val valid = ItemDetails(name = "Pen", price = "12.50", quantity = "3")

    @Test fun validationRejectsMissingNegativeAndNonNumericValues() {
        assertTrue(valid.isValid())
        assertTrue(valid.copy(price = "0", quantity = "0").isValid())
        assertFalse(valid.copy(name = "  ").isValid())
        listOf("", "text", "-1", "NaN", "Infinity").forEach {
            assertFalse(valid.copy(price = it).isValid())
        }
        listOf("", "text", "-1", "1.5", "2147483648").forEach {
            assertFalse(valid.copy(quantity = it).isValid())
        }
    }

    @Test fun entryDoesNotInsertInvalidDataAndTrimsValidNames() = runTest {
        val repository = FakeItemsRepository()
        val model = ItemEntryViewModel(repository)
        model.updateUiState(valid.copy(price = "-5"))
        model.saveItem()
        assertTrue(repository.items.value.isEmpty())
        model.updateUiState(valid.copy(name = "  Pen  "))
        model.saveItem()
        assertEquals(listOf(Item(0, "Pen", 12.5, 3)), repository.items.value)
    }

    @Test fun homeStateFlowObservesRepositoryChangesWithAnActiveSubscriber() {
        val dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
        try {
            runTest(dispatcher) {
            val repository = FakeItemsRepository()
            val model = HomeViewModel(repository)
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                model.homeUiState.collect()
            }
            runCurrent()
            repository.items.value = listOf(Item(1, "Book", 10.0, 2))
            runCurrent()
            assertEquals("Book", model.homeUiState.value.itemList.single().name)
            repository.items.value = emptyList()
            runCurrent()
            assertTrue(model.homeUiState.value.itemList.isEmpty())
            model.viewModelScope.cancel()
            runCurrent()
            }
        } finally {
            Dispatchers.resetMain()
        }
    }

    private class FakeItemsRepository : ItemsRepository {
        val items = MutableStateFlow<List<Item>>(emptyList())
        override fun getAllItemsStream(): Flow<List<Item>> = items
        override fun getItemStream(id: Int): Flow<Item?> = items.map { list -> list.find { it.id == id } }
        override suspend fun insertItem(item: Item) { items.value += item }
        override suspend fun updateItem(item: Item) { items.value = items.value.map { if (it.id == item.id) item else it } }
        override suspend fun deleteItem(item: Item) { items.value = items.value.filterNot { it.id == item.id } }
        override suspend fun sellItem(id: Int): Boolean = false
    }
}
