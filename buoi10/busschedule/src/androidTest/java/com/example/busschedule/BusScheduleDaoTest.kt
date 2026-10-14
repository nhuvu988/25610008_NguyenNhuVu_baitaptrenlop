package com.example.busschedule

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.busschedule.data.AppDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BusScheduleDaoTest {
    @Test fun assetLoads31RowsSortedByArrivalAndFiltersTheSelectedStop() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val name = "bus-asset-test.db"
        context.deleteDatabase(name)
        val database = Room.databaseBuilder(context, AppDatabase::class.java, name)
            .createFromAsset("database/bus_schedule.db").build()
        try {
            val dao = database.busScheduleDao()
            val schedules = dao.getAll().first()
            assertEquals(31, schedules.size)
            assertEquals(schedules.sortedBy { it.arrivalTimeInMillis }, schedules)
            val mainStreet = dao.getByStopName("Main Street").first()
            assertEquals(4, mainStreet.size)
            assertTrue(mainStreet.all { it.stopName == "Main Street" })
            assertEquals(mainStreet.sortedBy { it.arrivalTimeInMillis }, mainStreet)
            assertTrue(dao.getByStopName("Unknown stop").first().isEmpty())
        } finally {
            database.close()
            context.deleteDatabase(name)
        }
    }
}
