import kotlin.math.ceil
import kotlin.math.max

fun main() {
    var tax = transfer(20_000, 590_000) // отказ из-за месячного лимита
    println(tax)

    tax = transfer(200_000) // отказ из-за дневного лимита
    println(tax)

    tax = transfer(20_000, 65_000, "Mastercard") // часть перевода сверх лимита. комиссия - 60 + 20
    println(tax)

    tax = transfer(20_000, 75_000, "Mastercard") // весь перевод сверх лимита. комиссия - 0
    println(tax)

    tax = transfer(20_000, 15_000, "Mastercard") // перевод до лимита. комиссия - 140
    println(tax)

    tax = transfer(1_000, 15_000, "Visa") // перевод с комиссией ниже минимальной. комиссия - 35
    println(tax)

    tax = transfer(10_000, 15_000, "Visa") // перевод с комиссией выше минимальной. комиссия - 75
    println(tax)

    tax = transfer(150_000) // по карте Мир комиссия равна 0
    println(tax)

    tax = transfer(150_000, cardType = "American Express") // Отказ в обслуживании
    println(tax)


}

fun transfer(amount: Int, previousMonthAmount: Int = 0, cardType: String = "Мир"): Int {
    val monthLimit = 600_000
    val dayLimit = 150_000
    if (amount > dayLimit || amount + previousMonthAmount > monthLimit) {
        println("Перевод отклонен, Превышен лимит по карте")
        return 0
    }

    val tax = when (cardType) {
        "Mastercard" -> {
            val mcLimit = 75_000
            val rate = 0.006
            when {
                previousMonthAmount >= mcLimit ->
                    ceil(amount * rate + 20).toInt()

                amount + previousMonthAmount > mcLimit ->
                    ceil((amount + previousMonthAmount - mcLimit) * rate + 20).toInt()

                else -> 0
            }
        }

        "Visa" -> {
            val rate = 0.0075
            val minTax = 35.0
            ceil(max(minTax, amount * rate)).toInt()
        }

        else -> 0
    }

    if (cardType == "Мир" || cardType == "Mastercard" || cardType == "Visa") {
        println("Вы перевели с карты $cardType $amount р. Комиссия составила $tax р.")
    } else {
        println("Данный тип карты не поддерживается")
    }
    return tax
}

