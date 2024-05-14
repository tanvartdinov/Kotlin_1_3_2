import org.junit.Assert.*
import org.junit.Test

class MainKtTest {
    @Test
    fun transfer_failureWithExceedingDayLimit() {
        val amount = 160_000

        val actual = transfer(amount)
        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun transfer_failureWithExceedingMonthLimit() {
        val amount = 100_000
        val previousMonthAmount = 550_000

        val actual = transfer(amount, previousMonthAmount = previousMonthAmount)
        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun transfer_successWithMasterCardLessLimit() {
        val amount = 50_000
        val cardType = "Mastercard"

        val actual = transfer(amount, cardType = cardType)
        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun transfer_successWithMasterCardAllAmountMoreLimit() {
        val amount = 100_000
        val previousMonthAmount = 200_000
        val cardType = "Mastercard"

        val actual = transfer(amount, previousMonthAmount = previousMonthAmount, cardType = cardType)
        val expected = 620

        assertEquals(expected, actual)
    }

    @Test
    fun transfer_successWithMasterCardPartAmountMoreLimit() {
        val amount = 100_000
        val previousMonthAmount = 50_000
        val cardType = "Mastercard"

        val actual = transfer(amount, previousMonthAmount = previousMonthAmount, cardType = cardType)
        val expected = 470

        assertEquals(expected, actual)
    }

    @Test
    fun transfer_successWithVisaCardTaxMoreMinimal() {
        val amount = 100_000
        val cardType = "Visa"

        val actual = transfer(amount, cardType = cardType)
        val expected = 750

        assertEquals(expected, actual)
    }

    @Test
    fun transfer_successWithVisaCardTaxLessMinimal() {
        val amount = 1_000
        val cardType = "Visa"

        val actual = transfer(amount, cardType = cardType)
        val expected = 35

        assertEquals(expected, actual)
    }

    @Test
    fun transfer_successWithMirCard() {
        val amount = 100_000
        val cardType = "Мир"

        val actual = transfer(amount, cardType = cardType)
        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun transfer_successWithUnsupportedCard() {
        val amount = 100_000
        val cardType = "American Express"

        val actual = transfer(amount, cardType = cardType)
        val expected = 0

        assertEquals(expected, actual)
    }

    @Test
    fun testWithBrokenBuild() {
        val amount = 100_000
        val cardType = "Mastercard"

        val actual = transfer(amount, cardType = cardType)
        val expected = 0

        assertEquals(expected, actual)
    }
}