package com.jiahaoliuliu.akami.model

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.regex.Pattern

data class Expense2 (val sms: Sms): ITransactions {

    companion object {
        private const val TAG: String = "Expense"
        private const val DATE_FORMAT: String = "dd/MM/yyyy HH:mm:ss"
        private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)
    }

    // Initialize the values
    // The date when the SMS was received. It should be replaced by the date inside of the SMS
    // when it is parsed
    override var date = sms.date
        set(value) {
            throw IllegalAccessException("Once the date is set on init it cannot be set again")
        }

    override var type = TransactionsType.UNKNOWN
        set(value) {
            throw IllegalAccessException("Once the type is set on init it cannot be set again")
        }

    override var currency = Currency.UNKNOWN
        set(value) {
            throw IllegalAccessException("Once the type is set on init it cannot be set again")
        }

    override var quantity = 0.0F
        set(value) {
            throw IllegalAccessException("Once the quantity is set on init it cannot be set again")
        }

    override var originalCurrencyQuantity: Float = 0.0F
        set(value) {
            throw IllegalAccessException("Once the original currency quantity is set on init it" +
                    "cannot be set again")
        }

    override var source: String = ""
        set(value) {
            throw IllegalAccessException("Once the source is set on init it cannot be set again")
        }

    override var destination: String = ""
        set(value) {
            throw IllegalAccessException("Once the destination is set on init it cannot be" +
                    " set again")
        }

    override var firstTransactionOfTheMonth: Boolean = false
        set(value) {
            throw IllegalAccessException("Once the first destination of the month is set on init it" +
                    " cannot be set again")
        }

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

        // Date
        try {
            this.date = simpleDateFormat.parse(matcher.group(3))
        } catch (parseException: ParseException) {
            Log.w(TAG, "Error parsing the date ${matcher.group(3)}", parseException)
        }

        // Destination = company id
        this.destination = matcher.group(4)
    }
}