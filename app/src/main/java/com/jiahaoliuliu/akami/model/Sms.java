package com.jiahaoliuliu.akami.model;

import android.text.TextUtils;
import android.util.Log;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiahaoliuliu on 7/1/16.
 */
public class Sms {

    private static final String TAG = "Sms";

    /**
     * The type of SMS
     */
    public enum Type {
        EXPENSE_1("A purchase transaction of AED(.*?) has been performed on your Credit Card (.*?) on (.*?) at (.*?) \\."),
        EXPENSE_2("Purchase transaction of AED(.*?) performed on your Credit Card (.*?) on (.*?) at (.*?)\\."),
        UNKNOWN("^$");

//        EXPENSE_2;

        private String mRegExpression;

        Type(String regExpression) {
            this.mRegExpression = regExpression;
        }

        public String getRegExpression() {
            return mRegExpression;
        }
    }

//    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_BODY = "body";

    // Fields
//    private String _id;
    private Date date;
    private String body;
    private Type type = Type.UNKNOWN;

//    public String get_id() {
//        return _id;
//    }
//
//    public void set_id(String _id) {
//        this._id = _id;
//    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(Long date) {
        this.date = new Date(date);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;

        // Set the sms type
        if (TextUtils.isEmpty(body)) {
            Log.e(TAG, "The body of the sms is empty");
            return;
        }

        // Go through the list of types to see which one matches
        for (Type type : Type.values()) {
            Pattern pattern = Pattern.compile(type.getRegExpression());
            Matcher matcher = pattern.matcher(body);
            if (matcher.find()) {
                this.type = type;
                break;
            }
        }
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sms sms = (Sms) o;

        if (getDate() != null ? !getDate().equals(sms.getDate()) : sms.getDate() != null)
            return false;
        return getBody() != null ? getBody().equals(sms.getBody()) : sms.getBody() == null;

    }

    @Override
    public int hashCode() {
        int result = getDate() != null ? getDate().hashCode() : 0;
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "date=" + date +
                ", body='" + body + '\'' +
                '}';
    }
}
