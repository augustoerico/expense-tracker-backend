package io.github.augustoerico.models

import io.vertx.core.json.JsonObject

import java.math.RoundingMode

class Expense {

    static final DATETIME_UTC_REGEX = /^\d{4}(-\d{2}){2}T(\d{2}:){2}\d{2}Z$/

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
        this.datetime = data.datetime ?:
                new Date().format('yyyy-MM-dd\'T\'HH:mm:ssXXX', TimeZone.getTimeZone('UTC'))
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
        if (data.datetime && !(data.datetime.toUpperCase() ==~ DATETIME_UTC_REGEX)) {
            errorMessages << '\'datetime\' must be on UTC format'
        }

        if (errorMessages) {
            throw new RuntimeException("Error messages: $errorMessages")
        }
    }

    def asJson() {
        def obj = [account_id: account_id, description: description, amount: amount.toDouble(), datetime: datetime] +
                (_id ? [_id: _id] : [:])
        new JsonObject(obj)
    }

}
