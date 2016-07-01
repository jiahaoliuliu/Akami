package com.jiahaoliuliu.akami.model;

/**
 * Created by jiahaoliuliu on 7/1/16.
 */
public class Sms {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_THREAD_ID = "thread_id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DATE_SENT = "date_sent";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_BODY = "body";

    // Fields
    private String _id;
    private String threadId;
    private String address;
    private String date;
    private String dateSent;
    private String type;
    private String body;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sms sms = (Sms) o;

        if (get_id() != null ? !get_id().equals(sms.get_id()) : sms.get_id() != null) return false;
        if (getThreadId() != null ? !getThreadId().equals(sms.getThreadId()) : sms.getThreadId() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(sms.getAddress()) : sms.getAddress() != null)
            return false;
        if (getDate() != null ? !getDate().equals(sms.getDate()) : sms.getDate() != null)
            return false;
        if (getDateSent() != null ? !getDateSent().equals(sms.getDateSent()) : sms.getDateSent() != null)
            return false;
        if (getType() != null ? !getType().equals(sms.getType()) : sms.getType() != null)
            return false;
        return getBody() != null ? getBody().equals(sms.getBody()) : sms.getBody() == null;

    }

    @Override
    public int hashCode() {
        int result = get_id() != null ? get_id().hashCode() : 0;
        result = 31 * result + (getThreadId() != null ? getThreadId().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getDateSent() != null ? getDateSent().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "_id='" + _id + '\'' +
                ", threadId='" + threadId + '\'' +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", dateSent='" + dateSent + '\'' +
                ", type='" + type + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
