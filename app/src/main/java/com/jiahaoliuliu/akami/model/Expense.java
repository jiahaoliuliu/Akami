package com.jiahaoliuliu.akami.model;

import android.util.Log;

import java.util.Date;

/**
 * Created by jiahaoliuliu on 7/2/16.
 */
public class Expense {

    private static final String TAG = "Expense";

    // TODO: Set colour
    public enum ExpenseType {
        EXPENSE,
        WITHDRAW,
        SALARY
    }

    private String description;

    private ExpenseType expenseType;

    private Date date;

    private float quantity;

    private String creditCard;

    private String companyId;

    // The constructor which creates expense from the sms
    public Expense(Sms sms) {
        Log.v(TAG, "parsing sms " + sms);
    }

    public String getDescription() {
        return description;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public Date getDate() {
        return date;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public String getCompanyId() {
        return companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (Float.compare(expense.getQuantity(), getQuantity()) != 0) return false;
        if (getDescription() != null ? !getDescription().equals(expense.getDescription()) : expense.getDescription() != null)
            return false;
        if (getExpenseType() != expense.getExpenseType()) return false;
        if (getDate() != null ? !getDate().equals(expense.getDate()) : expense.getDate() != null)
            return false;
        if (getCreditCard() != null ? !getCreditCard().equals(expense.getCreditCard()) : expense.getCreditCard() != null)
            return false;
        return getCompanyId() != null ? getCompanyId().equals(expense.getCompanyId()) : expense.getCompanyId() == null;

    }

    @Override
    public int hashCode() {
        int result = getDescription() != null ? getDescription().hashCode() : 0;
        result = 31 * result + (getExpenseType() != null ? getExpenseType().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getQuantity() != +0.0f ? Float.floatToIntBits(getQuantity()) : 0);
        result = 31 * result + (getCreditCard() != null ? getCreditCard().hashCode() : 0);
        result = 31 * result + (getCompanyId() != null ? getCompanyId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "description='" + description + '\'' +
                ", expenseType=" + expenseType +
                ", date=" + date +
                ", quantity=" + quantity +
                ", creditCard='" + creditCard + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
