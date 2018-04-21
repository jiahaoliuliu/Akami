package com.jiahaoliuliu.akami.model

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.regex.Pattern

data class Expense (val sms: Sms): ITransactions {

    companion object {
        private const val TAG: String = "Expense"
        private const val DATE_FORMAT: String = "dd/MM/yyyy HH:mm:ss"
        private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)
    }

    // Initialize the values
    // The date when the SMS was received. It should be replaced by the date inside of the SMS
    // when it is parsed
    override var date = sms.date
    override var type = TransactionsType.UNKNOWN
    override var currency = Currency.UNKNOWN
    override var quantity = 0.0F
    override var originalCurrencyQuantity: Float = 0.0F
    override var source: String = ""
    override var destination: String = ""
    override var firstTransactionOfTheMonth: Boolean = false

    init {
        when(sms.type) {
            SmsType.EXPENSE_1, SmsType.EXPENSE_2 -> parseSms(sms)
            SmsType.UNKNOWN -> throwSmsUnknownException(sms)
            else -> throwSmsUnknownException(sms)
        }
        parseSms(sms)
    }

    private fun throwSmsUnknownException(sms: Sms): Unit =
            throw IllegalArgumentException("The sms type is unknown $sms")

    /**
     * Parse the sms based on the main constructor's parameter
     */
    private fun parseSms(sms: Sms) {
        val pattern = Pattern.compile(sms.type.regExpression)
        val matcher = pattern.matcher(sms.body)
        if (!matcher.find()) {
            throwSmsUnknownException(sms)
        }

        // Date
        try {
            this.date = simpleDateFormat.parse(matcher.group(3))
        } catch (parseException: ParseException) {
            Log.w(TAG, "Error parsing the date ${matcher.group(3)}", parseException)
        }

        this.type = TransactionsType.EXPENSE

        // Set the currency
        val currencyAndQuantity = matcher.group(1)
        this.currency = Currency.Companion.toCurrency(currencyAndQuantity.substring(0, 3))

        // Set the quantity
        try {
            this.quantity = currencyAndQuantity.substring(3).toFloat()
        } catch (numberFormatException: NumberFormatException) {
            throw IllegalArgumentException(
                    "Error parsing the quantity ${currencyAndQuantity.substring(3)}. " +
                            "Currency and quantity: \" $currencyAndQuantity \"\n" +
                            "SMS: \" $sms \"")
        }

        // Source = credit card
        this.source = matcher.group(2)

        // Destination = company id
        this.destination = matcher.group(4)
    }
}