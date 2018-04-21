package com.jiahaoliuliu.akami.model

import java.util.Date

/**
 * Basic class for the transactions
 */
interface ITransactions {

    var date: Date

    var type: TransactionsType

    var currency: Currency

    var quantity: Float

    /**
     * The quantity on the original currency
     */
    var originalCurrencyQuantity: Float

    /**
     * The source of the transaction
     */
    var source: String

    var destination: String

    var firstTransactionOfTheMonth: Boolean
}