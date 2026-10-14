/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inventory.data.InventoryDatabase
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: InventoryDatabase
    private val item1 = Item(1, "Apples", 10.0, 20)
    private val item2 = Item(2, "Bananas", 15.0, 97)

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        itemDao = inventoryDatabase.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        inventoryDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }


    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        addOneItemToDb()
        val item = itemDao.getItem(1)
        assertEquals(item.first(), item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.delete(item1)
        itemDao.delete(item2)
        val allItems = itemDao.getAllItems().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        addTwoItemsToDb()
        itemDao.update(Item(1, "Apples", 15.0, 25))
        itemDao.update(Item(2, "Bananas", 5.0, 50))

        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], Item(1, "Apples", 15.0, 25))
        assertEquals(allItems[1], Item(2, "Bananas", 5.0, 50))
    }

    private suspend fun addOneItemToDb() {
        itemDao.insert(item1)
    }

    @Test fun namesAreSortedAndMissingItemsReturnNull() = runBlocking {
        itemDao.insert(item2)
        itemDao.insert(item1)
        assertEquals(listOf(item1, item2), itemDao.getAllItems().first())
        assertEquals(null, itemDao.getItem(999).first())
    }

    @Test fun concurrentSalesCannotOverdrawOrLoseStockUpdates() = runBlocking {
        itemDao.insert(item1.copy(quantity = 2))
        val sales = List(6) { async(Dispatchers.IO) { itemDao.sellOne(1) } }.awaitAll()
        assertEquals(2, sales.sum())
        assertEquals(0, itemDao.getItem(1).first()!!.quantity)
        assertEquals(0, itemDao.sellOne(1))
    }

    @Test fun diskDatabaseKeepsItemsWhenReopened() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val name = "inventory-persistence-test.db"
        context.deleteDatabase(name)
        try {
            Room.databaseBuilder(context, InventoryDatabase::class.java, name).build().let { db ->
                try { db.itemDao().insert(item1) } finally { db.close() }
            }
            Room.databaseBuilder(context, InventoryDatabase::class.java, name).build().let { db ->
                try { assertEquals(item1, db.itemDao().getItem(1).first()) } finally { db.close() }
            }
        } finally {
            context.deleteDatabase(name)
        }
    }

    private suspend fun addTwoItemsToDb() {
        itemDao.insert(item1)
        itemDao.insert(item2)
    }
}
