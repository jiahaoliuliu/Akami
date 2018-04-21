package com.jiahaoliuliu.akami.model;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiahaoliuliu on 7/2/16.
 */
public class Expense implements ITransactions {

    private static final String TAG = "Expense";

    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    // Static variables for parsing. Since the parsing is done in each expense, it does make sense to have them as static
    private static final SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(DATE_FORMAT);

    private Date date;

    private Currency currency;

    private float quantity;

    private String creditCard;

    private String companyId;

    private boolean isFirstTransactionOfTheMonth;

    // The constructor which creates expense from the sms
    public Expense(Sms sms) {
        switch (sms.getType()) {
            case EXPENSE_1:
            case EXPENSE_2:
                parseSms(sms);
                break;
            case UNKNOWN:
            default:
                throw new IllegalArgumentException("The sms type is unknown " + sms);
        }
    }

    // Other methods
    private void parseSms(Sms sms) {
        Pattern pattern = Pattern.compile(sms.getType().getRegExpression());
        Matcher matcher = pattern.matcher(sms.getBody());
        if (!matcher.find()) {
            throw new IllegalArgumentException("The type of the sms suppose to be expense 1 but it does not matches");
        }

        // Currency
        String currencyAndQuantity = matcher.group(1);

        // The first three characters are the currency
        this.currency = Currency.Companion.toCurrency(currencyAndQuantity.substring(0, 3));

        // Quantity
        try {
            this.quantity = Float.parseFloat(currencyAndQuantity.substring(3));
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException("Error parsing the quantity " + currencyAndQuantity.substring(3) +  "Full: \"" + currencyAndQuantity +"\"" +
                    "SMS: \"" + sms.getBody() + "\"");
        }

        this.creditCard = matcher.group(2);
        try {
            this.date = simpleDateFormatter.parse(matcher.group(3));
        } catch (ParseException parseException) {
            // Use the date of the message instead
            this.date = sms.getDate();
        }
        this.companyId = matcher.group(4);
    }

    @Override
    public TransactionsType getType() {
        return TransactionsType.EXPENSE;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public float getQuantity() {
        return quantity * currency.getExchangeRate();
    }

    @Override
    public float getOriginalCurrencyQuantity() {
        return quantity;
    }

    @Override
    public String getSource() {
        return creditCard;
    }

    @Override
    public String getDestination() {
        return companyId;
    }

    @Override
    public Date getDate() {
        return date;
    }

    public void setFirstTransactionOfTheMonth(boolean firstTransactionOfTheMonth) {
        isFirstTransactionOfTheMonth = firstTransactionOfTheMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (Float.compare(expense.quantity, quantity) != 0) return false;
        if (isFirstTransactionOfTheMonth != expense.isFirstTransactionOfTheMonth) return false;
        if (date != null ? !date.equals(expense.date) : expense.date != null) return false;
        if (currency != expense.currency) return false;
        if (creditCard != null ? !creditCard.equals(expense.creditCard) : expense.creditCard != null)
            return false;
        return companyId != null ? companyId.equals(expense.companyId) : expense.companyId == null;

    }

    @Override
    public void setDate(@NotNull Date date){
        throw new IllegalArgumentException("This field shouldn't be set once initialized");
    }

    @Override
    public void setType(@NotNull TransactionsType transactionsType) {
        throw new IllegalArgumentException("This field shouldn't be set once initialized");
    }

    @Override
    public void setCurrency(@NotNull Currency currency) {
        throw new IllegalArgumentException("This field shouldn't be set once initialized");
    }

    @Override
    public void setQuantity(float v) {
        throw new IllegalArgumentException("This field shouldn't be set once initialized");
    }

    @Override
    public void setOriginalCurrencyQuantity(float v) {
        throw new IllegalArgumentException("This field shouldn't be set once initialized");
    }

    @Override
    public void setSource(@NotNull String s) {
        throw new IllegalArgumentException("This field shouldn't be set once initialized");
    }

    @Override
    public void setDestination(@NotNull String s) {
        throw new IllegalArgumentException("This field shouldn't be set once initialized");
    }

    @Override
    public boolean getFirstTransactionOfTheMonth() {
        return false;
    }
}
