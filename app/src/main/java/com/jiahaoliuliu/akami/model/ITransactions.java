package com.jiahaoliuliu.akami.model;

import android.text.TextUtils;
import android.util.Log;

import java.util.Date;

/**
 * Created by jiahaoliuliu on 7/7/16.
 */
public interface ITransactions {

    static final String TAG = "ITransactions";

    // TODO: Set colour
    // TODO: Create the super class Transactions which contains the follow classes
    // The type of the transactions
    enum Type {
        EXPENSE,
        WITHDRAW,
        SALARY,
        UNKNOWN;
    }

    // Average from: http://www.x-rates.com/average/?from=RUB&to=AED&amount=1&year=2016
    enum Currency {
        DIRHAMS("AED" ,1.00f), // United Arab Emirates
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

        // The code received from the SMS
        private String mCode;

        // The rate of exchange
        // TODO: Make it dynamic
        private float mExchangeRate;

        Currency(String code, float exchangeRate) {
            this.mCode = code;
            this.mExchangeRate = exchangeRate;
        }

        public String getCode() {
            return mCode;
        }

        public float getExchangeRate() {
            return mExchangeRate;
        }

        public static Currency toCurrency(String code) {
            // Corner case
            if (TextUtils.isEmpty(code)) {
                Log.w(TAG, "The code for currency is empty. Returning Unknown currency");
                return UNKNOWN;
            }

            for (Currency currency : values()) {
                if (currency.getCode().equalsIgnoreCase(code)) {
                    return currency;
                }
            }

            return UNKNOWN;
        }
    }

    String getId();

    Type getType();

    Currency getCurrency();

    float getQuantity();

    float getOriginalCurrencyQuantity();

    String getSource();

    String getDestination();

    Date getDate();

    boolean isFirstTransactionOfTheMonth();

    void setFirstTransactionOfTheMonth(boolean firstTransactionOfTheMonth);
}
