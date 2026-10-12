package com.example.dessertclicker
import com.example.dessertclicker.data.Datasource
import org.junit.Assert.*
import org.junit.Test
class DessertThresholdTest {
    @Test fun selectsDessertAtProductionBoundaries() {
        val desserts=Datasource.dessertList
        assertEquals(5,determineDessertToShow(desserts,0).price)
        assertEquals(5,determineDessertToShow(desserts,4).price)
        assertEquals(10,determineDessertToShow(desserts,5).price)
        assertEquals(15,determineDessertToShow(desserts,20).price)
        assertEquals(desserts.last(),determineDessertToShow(desserts,25000))
    }
}
