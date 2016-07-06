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

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    // Static variables for parsing. Since the parsing is done in each expense, it does make sense to have them as static
    private static final SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(DATE_FORMAT);

    // TODO: Set colour
    // TODO: Create the super class Transactions which contains the follwo classes
    public enum ExpenseType {
        EXPENSE,
        WITHDRAW,
        SALARY,
        PAYMENT
    }

    private ExpenseType expenseType;

    private Date date;

    private float quantity;

    private String creditCard;

    private String companyId;

    // The constructor which creates expense from the sms
    //
    public Expense(Sms sms) {
        switch (sms.getType()) {
            case EXPENSE_1:
            case EXPENSE_2:
                parseRegExpExpense(sms);
                break;
            case UNKNOWN:
            default:
                throw new IllegalArgumentException("The sms type is unknown " + sms);
        }
    }

    // Other methods
    private void parseRegExpExpense(Sms sms) {
        Pattern pattern = Pattern.compile(sms.getType().getRegExpression());
        Matcher matcher = pattern.matcher(sms.getBody());
        if (!matcher.find()) {
            throw new IllegalArgumentException("The type of the sms suppose to be expense 1 but it does not matches");
        }

        // Get the first expense
        try {
            this.quantity = Float.valueOf(matcher.group(1));
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException("The value of quantity is not correct: " + matcher.group(1));
        }
        this.creditCard = matcher.group(2);
        try {
            this.date = simpleDateFormatter.parse(matcher.group(3));
        } catch (ParseException parseException) {
            // Use the date of the message instead
            this.date = sms.getDate();
        }
        this.companyId = matcher.group(4);
        this.expenseType = ExpenseType.EXPENSE;
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
