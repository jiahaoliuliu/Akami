package com.jiahaoliuliu.akami.model;

import java.util.Date;

/**
 * Created by jiahaoliuliu on 7/7/16.
 */
public interface ITransactions {

    // TODO: Set colour
    // TODO: Create the super class Transactions which contains the follwo classes
    enum Type {
        EXPENSE,
        WITHDRAW,
        SALARY,
        UNKNOWN;
    }

    String getId();

    Type getType();

    float getQuantity();

    String getSource();

    String getDestination();

    Date getDate();

    boolean isFirstTransactionOfTheMonth();

    void setFirstTransactionOfTheMonth(boolean firstTransactionOfTheMonth);
}
