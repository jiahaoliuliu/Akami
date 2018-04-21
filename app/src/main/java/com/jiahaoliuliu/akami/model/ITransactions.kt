package com.jiahaoliuliu.akami.model

import java.util.Date

/**
 * Basic class for the transactions
 */
interface ITransactions {

    /**
     * Get the type of the transaction
     */
    fun getType(): TransactionsType

    /**
     * Get the transactions's currency
     */
    fun getCurrency(): Currency

    /**
     * The quantity of the transaction (Converted to the default currency)
     */
    fun getQuantity(): Float

    /**
     * Get the quantity on the original currency
     */
    fun getOriginalCurrencyQuantity(): Float

    /**
     * Get the source of the transaction
     */
    fun getSource(): String

    /**
     * Get where does the money goes
     */
    fun getDestination(): String

    /**
     * Get the date of the transaction
     */
    fun getDate(): Date

    // TODO: Optimize this
    /**
     * Check if this is the first transaction of the month
     */
    fun isFirstTransactionOfTheMonth(): Boolean

    /**
     * Set the current transaction as the first transaction of the month
     */
    fun setFirstTransactionOfTheMonth(isFirstTransactionOfTheMonth:Boolean)
}