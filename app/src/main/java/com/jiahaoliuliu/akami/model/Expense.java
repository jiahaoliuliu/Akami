package com.jiahaoliuliu.akami.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiahaoliuliu on 7/2/16.
 */
public class Expense {

    private static final String TAG = "Expense";
    private static final String REG_EXP_EXPENSE_1 = "A purchase transaction of AED(.*?) has been performed on your Credit Card (.*?) on (.*?) at (.*?) . ";

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private SimpleDateFormat simpleDateFormatter;

    // TODO: Set colour
    public enum ExpenseType {
        EXPENSE,
        WITHDRAW,
        SALARY
    }

    private ExpenseType expenseType;

    private Date date;

    private float quantity;

    private String creditCard;

    private String companyId;

    // The constructor which creates expense from the sms
    //
    public Expense(Sms sms) {
//        Log.v(TAG, "parsing sms " + sms);
        Pattern patternExpense1 = Pattern.compile(REG_EXP_EXPENSE_1);
        Matcher matcherExpense1 = patternExpense1.matcher(sms.getBody());
        if (matcherExpense1.find()) {
            // Get the first expense
            this.quantity = Float.parseFloat(matcherExpense1.group(1));
            this.creditCard = matcherExpense1.group(2);
            this.simpleDateFormatter = new SimpleDateFormat(DATE_FORMAT);
            try {
                this.date = simpleDateFormatter.parse(matcherExpense1.group(3));
            } catch (ParseException parseException) {
                // Use the date of the message instead
                this.date = sms.getDate();
            }
            this.companyId = matcherExpense1.group(4);
            this.expenseType = ExpenseType.EXPENSE;
        } else {
            throw new IllegalArgumentException("Unknown expense: " + sms.getBody());
        }
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
        if (getExpenseType() != expense.getExpenseType()) return false;
        if (getDate() != null ? !getDate().equals(expense.getDate()) : expense.getDate() != null)
            return false;
        if (getCreditCard() != null ? !getCreditCard().equals(expense.getCreditCard()) : expense.getCreditCard() != null)
            return false;
        return getCompanyId() != null ? getCompanyId().equals(expense.getCompanyId()) : expense.getCompanyId() == null;

    }

    @Override
    public int hashCode() {
        int result = getExpenseType() != null ? getExpenseType().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getQuantity() != +0.0f ? Float.floatToIntBits(getQuantity()) : 0);
        result = 31 * result + (getCreditCard() != null ? getCreditCard().hashCode() : 0);
        result = 31 * result + (getCompanyId() != null ? getCompanyId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseType=" + expenseType +
                ", date=" + date +
                ", quantity=" + quantity +
                ", creditCard='" + creditCard + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
