package io.github.augustoerico.models

import io.vertx.core.json.JsonObject

import java.math.RoundingMode

class Expense {

    def _id
    def account_id
    def description
    def amount
    def datetime

    Expense(Map data) {
        validate(data)

        this._id = data._id
        this.account_id = data.account_id
        this.description = data.description

        this.amount = new BigDecimal(data.amount ?: 0).setScale(2, RoundingMode.HALF_UP)
        this.datetime = new Date().format('yyyy-MM-dd\'T\'HH:mm:ssXXX', TimeZone.getTimeZone('UTC'))
    }

    static validate(data) {
        def errorMessages = []

        if (!data.account_id) {
            errorMessages << '\'account_id\' must not be empty'
        }
        if (!data.amount) {
            errorMessages << '\'amount\' must not be empty'
        } else if (!(data.amount in Number)) {
            errorMessages << '\'amount\' must be a number'
        }

        if (errorMessages) {
            throw new RuntimeException("Error messages: $errorMessages")
        }
    }

    def asJson() {
        def obj = [account_id: account_id, description: description, amount: amount, datetime: datetime] +
                (_id ? [_id: _id] : [:])
        new JsonObject(obj)
    }
}
