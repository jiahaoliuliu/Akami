package com.jiahaoliuliu.akami.model

import android.text.TextUtils
import java.util.Date
import java.util.regex.Matcher
import java.util.regex.Pattern

data class Sms (val body: String, val date: Date){
    companion object {
        const val COLUMN_ADDRESS = "address";
        const val COLUMN_DATE = "date"
        const val COLUMN_TYPE = "type"
        const val COLUMN_BODY = "body"
    }

    var type: SmsType = SmsType.UNKNOWN

    init {
        type = getSmsTypeFromBody(body)
    }

    private fun getSmsTypeFromBody(body: String): SmsType {
        if (TextUtils.isEmpty(body)) {
            return SmsType.UNKNOWN
        }

        for (smsType: SmsType in SmsType.values()) {
            val pattern: Pattern = Pattern.compile(smsType.regExpression)
            val matcher: Matcher = pattern.matcher(body)
            if (matcher.find()) {
                return smsType
            }
        }

        return SmsType.UNKNOWN
    }
}