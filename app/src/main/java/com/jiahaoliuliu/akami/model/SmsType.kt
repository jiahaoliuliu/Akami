package com.jiahaoliuliu.akami.model

enum class SmsType (val regExpression: String) {
    EXPENSE_1("A purchase transaction of (.*?) has been performed on your Credit Card (.*?) on (.*?) at (.*?) \\."),
    EXPENSE_2("Purchase transaction of (.*?) performed on your Credit Card (.*?) on (.*?) at (.*?)\\."),
    WITHDRAW_1("(.*?) withdrawn from acc. (.*?) on (.*?) at (.*?)\\."),
    WITHDRAW_2("(.*?) was withdrawn from your (.*?) on (.*?)  at (.*?)\\."),
    UNKNOWN("^$");
}