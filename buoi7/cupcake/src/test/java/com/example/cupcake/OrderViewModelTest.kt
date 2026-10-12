package com.example.cupcake
import com.example.cupcake.ui.OrderViewModel
import java.text.NumberFormat
import org.junit.Assert.*
import org.junit.Test
class OrderViewModelTest {
    @Test fun pricesSameDayAndResetsCancelledOrder() {
        val model=OrderViewModel()
        val currency=NumberFormat.getCurrencyInstance()
        model.setQuantity(6)
        model.setFlavor("Chocolate")
        model.setDate(model.uiState.value.pickupOptions[0])
        assertEquals(currency.format(15.0),model.uiState.value.price)
        model.setDate(model.uiState.value.pickupOptions[1])
        assertEquals(currency.format(12.0),model.uiState.value.price)
        model.resetOrder()
        assertEquals(0,model.uiState.value.quantity)
        assertEquals("",model.uiState.value.flavor)
    }
}
