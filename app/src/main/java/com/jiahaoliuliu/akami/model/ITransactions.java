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

    enum Currency {
        DIRHAMS("AED" ,1.00f), // United Arab Emirates
        DOLLAR("USD", 3.67f), // US dollar
        EURO("EUR", 4.10f), // Euro
        UNKNOWN("", 1.00f); // The rate for unknown currency is 1:1

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

    String getSource();

    String getDestination();

    Date getDate();

    boolean isFirstTransactionOfTheMonth();

    void setFirstTransactionOfTheMonth(boolean firstTransactionOfTheMonth);
}
