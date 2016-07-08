package com.jiahaoliuliu.akami.model;

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

    private String id;

    private Date date;

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

        // Id
        this.id = sms.getId();

        // Quantity
        try {
            this.quantity = Float.parseFloat(matcher.group(1));
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException("Error parsing the quantity " + matcher.group(1));
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
    public String getId() {
        return id;
    }

    @Override
    public Type getType() {
        return Type.EXPENSE;
    }

    @Override
    public float getQuantity() {
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

    @Override
    public boolean isFirstTransactionOfTheMonth() {
        return isFirstTransactionOfTheMonth;
    }

    public void setFirstTransactionOfTheMonth(boolean firstTransactionOfTheMonth) {
        isFirstTransactionOfTheMonth = firstTransactionOfTheMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (Float.compare(expense.getQuantity(), getQuantity()) != 0) return false;
        if (isFirstTransactionOfTheMonth() != expense.isFirstTransactionOfTheMonth()) return false;
        if (getId() != null ? !getId().equals(expense.getId()) : expense.getId() != null)
            return false;
        if (getDate() != null ? !getDate().equals(expense.getDate()) : expense.getDate() != null)
            return false;
        if (creditCard != null ? !creditCard.equals(expense.creditCard) : expense.creditCard != null)
            return false;
        return companyId != null ? companyId.equals(expense.companyId) : expense.companyId == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getQuantity() != +0.0f ? Float.floatToIntBits(getQuantity()) : 0);
        result = 31 * result + (creditCard != null ? creditCard.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (isFirstTransactionOfTheMonth() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", quantity=" + quantity +
                ", creditCard='" + creditCard + '\'' +
                ", companyId='" + companyId + '\'' +
                ", isFirstTransactionOfTheMonth=" + isFirstTransactionOfTheMonth +
                '}';
    }
}
