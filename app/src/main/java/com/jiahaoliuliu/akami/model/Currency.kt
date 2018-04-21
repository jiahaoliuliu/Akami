package com.jiahaoliuliu.akami.model

import android.text.TextUtils

// TODO: Make it dynamic
enum class Currency (val code: String, val exchangeRate: Float){
    //Average from: http://www.x-rates.com/average/?from=RUB&to=AED&amount=1&year=2016
    DIRHAMS("AED", 1.00f), // United Arab Emirates
    DOLLAR("USD", 3.67f), // US dollar
    EURO("EUR", 4.10f), // Euro
    YEN("JPY", 0.036f), // Japanese Yen
    POUND("GBP", 0.48f), // British pound
    AUSTRALIAN_DOLLAR("AUD", 2.75f), // Australian dollar
    SWISS_FRANC("CHF", 3.74f),
    MEXICAN_PESO("MXN", 0.197f),
    CHINESE_YUAN("CNY", 0.55f),
    NEW_ZELAND_DOLLAR("NZD", 2.62f),
    SWEDISH_KRONA("SEK", 0.43f),
    RUSSIAN_RUBLE("RUB", 0.056f),
    HONG_KONG_DOLLAR("HKD", 0.473f),
    NORWEGIAN_KRONE("NOK", 0.43f),
    SINGAPORE_DOLLAR("SGD", 2.70f),
    TURKISH_LIRA("TRY", 1.23f),
    SOUTH_KOREAN_WON("KRW", 0.0032f),
    SOUTH_AFRICA_RAND("ZAR", 0.25f),
    BRAZILIAN_REAL("BRL", 1.12f),
    INDIAN_RUPEE("INR", 0.054f),
    PAKISTAN_RUPEE("PKR", 0.035f),
    UNKNOWN("Unknown", 1.00f); // The rate for unknown currency is 1:1

    companion object {
        fun toCurrency(code: String): Currency {
            if(TextUtils.isEmpty(code)) {
                return UNKNOWN
            }

            for (currency: Currency in values()) {
                if (currency.code == code) {
                    return currency
                }
            }

            return UNKNOWN
        }
    }
}