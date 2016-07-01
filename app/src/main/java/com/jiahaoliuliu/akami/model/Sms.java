package com.jiahaoliuliu.akami.model;

/**
 * Created by jiahaoliuliu on 7/1/16.
 */
public class Sms {

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_THREAD_ID = "thread_id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PERSON = "person";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DATE_SENT = "date_sent";
    public static final String COLUMN_PROTOCOL = "protocol";
    public static final String COLUMN_READ = "read";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_REPLY_PATH_PRESENT = "reply_path_present";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_SERVICE_CENTER = "service_center";
    public static final String COLUMN_LOCKED = "locked";
    public static final String COLUMN_ERROR_CODE = "error_code";
    public static final String COLUMN_SEEN = "seen";
    public static final String COLUMN_TIMED = "timed";
    public static final String COLUMN_DELETED = "deleted";
    public static final String COLUMN_SYNC_STATE = "sync_state";
    public static final String COLUMN_MARKER = "marker";
    public static final String COLUMN_SOURCE = "source";
    public static final String COLUMN_BIND_ID = "bind_id";
    public static final String COLUMN_MX_STATUS = "mx_status";
    public static final String COLUMN_MX_ID = "mx_id";
    public static final String COLUMN_OUT_TIME = "out_time";
    public static final String COLUMN_ACCOUNT = "account";
    public static final String COLUMN_SIM_ID = "sim_id";
    public static final String COLUMN_BLOCK_TYPE = "block_type";
    public static final String COLUMN_ADVANCED_SEEN = "advanced_seen";
    public static final String COLUMN_B2C_TTL = "b2c_ttl";
    public static final String COLUMN_B2C_NUMBERS = "b2c_numbers";
    public static final String COLUMN_FAKE_CELL_TYPE = "fake_cell_type";
    public static final String COLUMN_URL_RISKY_TYPE = "url_risky_type";

    // Fields
    private String _id;
    private String threadId;
    private String address;
    private String person;
    private String date;
    private String dateSent;
    private String protocol;
    private String read;
    private String status;
    private String type;
    private String replyPathPresent;
    private String subject;
    private String body;
    private String serviceCenter;
    private String locked;
    private String errorCode;
    private String seen;
    private String timed;
    private String deleted;
    private String syncState;
    private String marker;
    private String source;
    private String bindId;
    private String mxStatus;
    private String mxId;
    private String outTime;
    private String account;
    private String simId;
    private String blockType;
    private String advancedSeen;
    private String b2cTtl;
    private String b2cNumbers;
    private String fakeCellType;
    private String urlRiskyType;

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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReplyPathPresent() {
        return replyPathPresent;
    }

    public void setReplyPathPresent(String replyPathPresent) {
        this.replyPathPresent = replyPathPresent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(String serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getTimed() {
        return timed;
    }

    public void setTimed(String timed) {
        this.timed = timed;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getSyncState() {
        return syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getMxStatus() {
        return mxStatus;
    }

    public void setMxStatus(String mxStatus) {
        this.mxStatus = mxStatus;
    }

    public String getMxId() {
        return mxId;
    }

    public void setMxId(String mxId) {
        this.mxId = mxId;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getAdvancedSeen() {
        return advancedSeen;
    }

    public void setAdvancedSeen(String advancedSeen) {
        this.advancedSeen = advancedSeen;
    }

    public String getB2cTtl() {
        return b2cTtl;
    }

    public void setB2cTtl(String b2cTtl) {
        this.b2cTtl = b2cTtl;
    }

    public String getB2cNumbers() {
        return b2cNumbers;
    }

    public void setB2cNumbers(String b2cNumbers) {
        this.b2cNumbers = b2cNumbers;
    }

    public String getFakeCellType() {
        return fakeCellType;
    }

    public void setFakeCellType(String fakeCellType) {
        this.fakeCellType = fakeCellType;
    }

    public String getUrlRiskyType() {
        return urlRiskyType;
    }

    public void setUrlRiskyType(String urlRiskyType) {
        this.urlRiskyType = urlRiskyType;
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
        if (getPerson() != null ? !getPerson().equals(sms.getPerson()) : sms.getPerson() != null)
            return false;
        if (getDate() != null ? !getDate().equals(sms.getDate()) : sms.getDate() != null)
            return false;
        if (getDateSent() != null ? !getDateSent().equals(sms.getDateSent()) : sms.getDateSent() != null)
            return false;
        if (getProtocol() != null ? !getProtocol().equals(sms.getProtocol()) : sms.getProtocol() != null)
            return false;
        if (getRead() != null ? !getRead().equals(sms.getRead()) : sms.getRead() != null)
            return false;
        if (getStatus() != null ? !getStatus().equals(sms.getStatus()) : sms.getStatus() != null)
            return false;
        if (getType() != null ? !getType().equals(sms.getType()) : sms.getType() != null)
            return false;
        if (getReplyPathPresent() != null ? !getReplyPathPresent().equals(sms.getReplyPathPresent()) : sms.getReplyPathPresent() != null)
            return false;
        if (getSubject() != null ? !getSubject().equals(sms.getSubject()) : sms.getSubject() != null)
            return false;
        if (getBody() != null ? !getBody().equals(sms.getBody()) : sms.getBody() != null)
            return false;
        if (getServiceCenter() != null ? !getServiceCenter().equals(sms.getServiceCenter()) : sms.getServiceCenter() != null)
            return false;
        if (getLocked() != null ? !getLocked().equals(sms.getLocked()) : sms.getLocked() != null)
            return false;
        if (getErrorCode() != null ? !getErrorCode().equals(sms.getErrorCode()) : sms.getErrorCode() != null)
            return false;
        if (getSeen() != null ? !getSeen().equals(sms.getSeen()) : sms.getSeen() != null)
            return false;
        if (getTimed() != null ? !getTimed().equals(sms.getTimed()) : sms.getTimed() != null)
            return false;
        if (getDeleted() != null ? !getDeleted().equals(sms.getDeleted()) : sms.getDeleted() != null)
            return false;
        if (getSyncState() != null ? !getSyncState().equals(sms.getSyncState()) : sms.getSyncState() != null)
            return false;
        if (getMarker() != null ? !getMarker().equals(sms.getMarker()) : sms.getMarker() != null)
            return false;
        if (getSource() != null ? !getSource().equals(sms.getSource()) : sms.getSource() != null)
            return false;
        if (getBindId() != null ? !getBindId().equals(sms.getBindId()) : sms.getBindId() != null)
            return false;
        if (getMxStatus() != null ? !getMxStatus().equals(sms.getMxStatus()) : sms.getMxStatus() != null)
            return false;
        if (getMxId() != null ? !getMxId().equals(sms.getMxId()) : sms.getMxId() != null)
            return false;
        if (getOutTime() != null ? !getOutTime().equals(sms.getOutTime()) : sms.getOutTime() != null)
            return false;
        if (getAccount() != null ? !getAccount().equals(sms.getAccount()) : sms.getAccount() != null)
            return false;
        if (getSimId() != null ? !getSimId().equals(sms.getSimId()) : sms.getSimId() != null)
            return false;
        if (getBlockType() != null ? !getBlockType().equals(sms.getBlockType()) : sms.getBlockType() != null)
            return false;
        if (getAdvancedSeen() != null ? !getAdvancedSeen().equals(sms.getAdvancedSeen()) : sms.getAdvancedSeen() != null)
            return false;
        if (getB2cTtl() != null ? !getB2cTtl().equals(sms.getB2cTtl()) : sms.getB2cTtl() != null)
            return false;
        if (getB2cNumbers() != null ? !getB2cNumbers().equals(sms.getB2cNumbers()) : sms.getB2cNumbers() != null)
            return false;
        if (getFakeCellType() != null ? !getFakeCellType().equals(sms.getFakeCellType()) : sms.getFakeCellType() != null)
            return false;
        return getUrlRiskyType() != null ? getUrlRiskyType().equals(sms.getUrlRiskyType()) : sms.getUrlRiskyType() == null;

    }

    @Override
    public int hashCode() {
        int result = get_id() != null ? get_id().hashCode() : 0;
        result = 31 * result + (getThreadId() != null ? getThreadId().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPerson() != null ? getPerson().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getDateSent() != null ? getDateSent().hashCode() : 0);
        result = 31 * result + (getProtocol() != null ? getProtocol().hashCode() : 0);
        result = 31 * result + (getRead() != null ? getRead().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getReplyPathPresent() != null ? getReplyPathPresent().hashCode() : 0);
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getBody() != null ? getBody().hashCode() : 0);
        result = 31 * result + (getServiceCenter() != null ? getServiceCenter().hashCode() : 0);
        result = 31 * result + (getLocked() != null ? getLocked().hashCode() : 0);
        result = 31 * result + (getErrorCode() != null ? getErrorCode().hashCode() : 0);
        result = 31 * result + (getSeen() != null ? getSeen().hashCode() : 0);
        result = 31 * result + (getTimed() != null ? getTimed().hashCode() : 0);
        result = 31 * result + (getDeleted() != null ? getDeleted().hashCode() : 0);
        result = 31 * result + (getSyncState() != null ? getSyncState().hashCode() : 0);
        result = 31 * result + (getMarker() != null ? getMarker().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        result = 31 * result + (getBindId() != null ? getBindId().hashCode() : 0);
        result = 31 * result + (getMxStatus() != null ? getMxStatus().hashCode() : 0);
        result = 31 * result + (getMxId() != null ? getMxId().hashCode() : 0);
        result = 31 * result + (getOutTime() != null ? getOutTime().hashCode() : 0);
        result = 31 * result + (getAccount() != null ? getAccount().hashCode() : 0);
        result = 31 * result + (getSimId() != null ? getSimId().hashCode() : 0);
        result = 31 * result + (getBlockType() != null ? getBlockType().hashCode() : 0);
        result = 31 * result + (getAdvancedSeen() != null ? getAdvancedSeen().hashCode() : 0);
        result = 31 * result + (getB2cTtl() != null ? getB2cTtl().hashCode() : 0);
        result = 31 * result + (getB2cNumbers() != null ? getB2cNumbers().hashCode() : 0);
        result = 31 * result + (getFakeCellType() != null ? getFakeCellType().hashCode() : 0);
        result = 31 * result + (getUrlRiskyType() != null ? getUrlRiskyType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "_id='" + _id + '\'' +
                ", threadId='" + threadId + '\'' +
                ", address='" + address + '\'' +
                ", person='" + person + '\'' +
                ", date='" + date + '\'' +
                ", dateSent='" + dateSent + '\'' +
                ", protocol='" + protocol + '\'' +
                ", read='" + read + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", replyPathPresent='" + replyPathPresent + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", serviceCenter='" + serviceCenter + '\'' +
                ", locked='" + locked + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", seen='" + seen + '\'' +
                ", timed='" + timed + '\'' +
                ", deleted='" + deleted + '\'' +
                ", syncState='" + syncState + '\'' +
                ", marker='" + marker + '\'' +
                ", source='" + source + '\'' +
                ", bindId='" + bindId + '\'' +
                ", mxStatus='" + mxStatus + '\'' +
                ", mxId='" + mxId + '\'' +
                ", outTime='" + outTime + '\'' +
                ", account='" + account + '\'' +
                ", simId='" + simId + '\'' +
                ", blockType='" + blockType + '\'' +
                ", advancedSeen='" + advancedSeen + '\'' +
                ", b2cTtl='" + b2cTtl + '\'' +
                ", b2cNumbers='" + b2cNumbers + '\'' +
                ", fakeCellType='" + fakeCellType + '\'' +
                ", urlRiskyType='" + urlRiskyType + '\'' +
                '}';
    }
}
