package com.example.lunchtray
import com.example.lunchtray.ui.OrderViewModel
import com.example.lunchtray.datasource.DataSource
import org.junit.Assert.*
import org.junit.Test
class OrderViewModelTest {
    @Test fun replacementDoesNotDoubleChargeAndCancelResets() {
        val model=OrderViewModel()
        model.updateEntree(DataSource.entreeMenuItems[0])
        model.updateEntree(DataSource.entreeMenuItems[1])
        model.updateSideDish(DataSource.sideDishMenuItems[0])
        val expected=DataSource.entreeMenuItems[1].price+DataSource.sideDishMenuItems[0].price
        assertEquals(expected,model.uiState.value.itemTotalPrice,0.001)
        assertEquals(expected*1.08,model.uiState.value.orderTotalPrice,0.001)
        model.resetOrder()
        assertNull(model.uiState.value.entree)
        assertEquals(0.0,model.uiState.value.orderTotalPrice,0.001)
    }
}
