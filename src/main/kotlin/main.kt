import kotlin.math.ceil
import kotlin.math.max

fun main() {
    transfer(20_000, 590_000) // отказ из-за месячного лимита
    transfer(200_000 ) // отказ из-за дневного лимита
    transfer(20_000, 65_000, "Mastercard") // перевод сверх лимита. комиссия - 60 + 20
    transfer(20_000, 15_000, "Mastercard") // перевод до лимита. комиссия - 0
    transfer(1_000, 15_000, "Visa") // перевод с комиссией ниже минимальной. комиссия - 35
    transfer(10_000, 15_000, "Visa") // перевод с комиссией выше минимальной. комиссия - 75
    transfer(150_000) // по карте Мир комиссия равна 0
    transfer(150_000, cardType = "American Express") // Отказ в обслуживании

}

fun transfer(amount: Int, previousMonthAmount: Int = 0, cardType: String = "Мир") {
    val monthLimit = 600_000
    val dayLimit = 150_000
    if (amount > dayLimit || amount + previousMonthAmount > monthLimit) {
        println("Перевод отклонен, Превышен лимит по карте")
    } else {
        val tax = when (cardType) {
            "Mastercard" -> {
                val mcLimit = 75_000
                val rate = 0.006
                if (amount + previousMonthAmount > mcLimit)
                    (amount + previousMonthAmount - mcLimit) * rate + 20 else 0.0
            }

            "Visa" -> {
                val rate = 0.0075
                val minTax = 35.0
                max(minTax, amount * rate)
            }

            else -> 0.0
        }

        if (cardType == "Мир" || cardType == "Mastercard" || cardType == "Visa") {
            println("Вы перевели с карты $cardType $amount р. Комиссия составила ${ceil(tax)} р.")
        } else {
            println("Данный тип карты не поддерживается")
        }
    }
}
