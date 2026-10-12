package example.functions

import org.junit.Assert.*
import org.junit.Test
import kotlin.random.Random

class FunctionsTest {
    @Test fun waterChangesAtCorrectBoundaries() {
        assertFalse(shouldChangeWater("Monday",30,30))
        assertTrue(shouldChangeWater("Monday",31,20))
        assertTrue(shouldChangeWater("Monday",22,31))
        assertTrue(shouldChangeWater("Sunday"))
        assertFalse(shouldChangeWater("Tuesday"))
    }
    @Test fun foodAndRandomDays() {
        assertEquals("flakes",fishFood("Monday"))
        assertEquals("nothing",fishFood("Tuesday"))
        assertEquals("nothing",fishFood("Saturday"))
        assertEquals("plankton",fishFood("Sunday"))
        val random = Random(42)
        repeat(100) { assertTrue(randomDay(random) in week) }
    }
    @Test fun lazySequenceDoesNotWorkUntilConsumed() {
        var visits=0
        val sequence=decorations.asSequence().filter { it.startsWith('p') }.map { visits++; it }
        assertEquals(0,visits)
        assertEquals("pagoda",sequence.first())
        assertEquals(1,visits)
        assertEquals(listOf("pagoda","plastic plant"),sequence.toList())
        assertEquals(3,visits)
    }
    @Test fun higherOrderFunctions() {
        assertEquals(15,updateDirty(30) { it/2 })
        assertEquals(16,updateDirty(15,::increaseDirty))
        assertEquals(42,updateDirty(19) { it+23 })
    }
}
