package com.jiahaoliuliu.akami.model

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.regex.Pattern

/**
 * Model created to contain Withdraws. This is an example:
 * - AED 4200.00 withdrawn from acc. XXX132001 on Feb  1 2015  6:32PM at ATM-CBD ATM MARINA BR 2501.
 *   Avail.Bal.AED12283.31.Exercise caution with large amount of cash.Visit www.adcb.com/mobileapp to
 *   download the ADCB Mobile Banking App.
 * - AED200.00 was withdrawn from your Credit Card XXX4921 on 25/05/2015 21:04:56  at ADIB METRO DIC DXB,DUBAI-AE.
 *   Available credit limit is now AED 32655.38
 * Created by jiahaoliuliu on 7/7/16.
 */
class Withdraw (val sms: Sms): ITransactions {

    companion object {
        const val WITHDRAW_TITLE = "Withdraw"
        private const val TAG = "Withdraw"
        private val DATE_FORMAT_WITHDRAW_1 = "MMM dd yyyy HH:mmaa"
        private val DATE_FORMAT_WITHDRAW_2 = "dd/MM/yyyy HH:mm:ss"
        private val DATE_FORMATTER_WITHDRAW_1 = SimpleDateFormat(DATE_FORMAT_WITHDRAW_1)
        private val DATE_FORMATTER_WITHDRAW_2 = SimpleDateFormat(DATE_FORMAT_WITHDRAW_2)
    }

    override var date = sms.date
    override var type = TransactionsType.UNKNOWN
    override var currency = Currency.UNKNOWN
    override var quantity = 0.0F
    override var originalCurrencyQuantity = 0.0F
    override var source = ""
    override var destination = ""
    override var firstTransactionOfTheMonth = false

    init {
        when(sms.type) {
            SmsType.WITHDRAW_1, SmsType.WITHDRAW_2 -> parseWithdraw(sms)
            SmsType.UNKNOWN -> throwSmsUnknownException(sms)
            else -> throwSmsUnknownException(sms)
        }
    }

    private fun throwSmsUnknownException(sms: Sms): Unit =
            throw IllegalArgumentException("The sms type is unknown $sms")

    private fun parseWithdraw(sms: Sms) {
        val pattern = Pattern.compile(sms.type.regExpression)
        val matcher = pattern.matcher(sms.body)

        if (!matcher.find()) {
            throw IllegalArgumentException("The type of the sms suppose to be withdraw 1 but it " +
                    "does not matches: ${sms.body}" )
        }

        // Currency
        val currencyAndQuantity = matcher.group(1)
        currency = Currency.Companion.toCurrency(currencyAndQuantity.substring(0, 3))

        // Quantity
        try {
            quantity = currencyAndQuantity.substring(4).toFloat()
        } catch (numberFormatException: NumberFormatException) {
            throw IllegalArgumentException("Error parsing the quantity " +
                    "${currencyAndQuantity.substring(4)}. The data to be parsed is " +
                    "$currencyAndQuantity and the sms is ${sms.body}")
        }

        // Source
        source = matcher.group(2)

        // Date
        try {
            when(sms.type) {
                SmsType.WITHDRAW_1 -> date = DATE_FORMATTER_WITHDRAW_1.parse(matcher.group(3))
                SmsType.WITHDRAW_2 -> date = DATE_FORMATTER_WITHDRAW_2.parse(matcher.group(3))
            }
        } catch (parseException: ParseException) {
            Log.w(TAG, "Error parsing the date \"${matcher.group(3)}\"");

            // Use the date of the message instead
            date = sms.date
        }

        // Destination
        destination = matcher.group(4)

    }
}