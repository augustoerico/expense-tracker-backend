package io.github.augustoerico.models

import spock.lang.Specification

class ExpenseUnitSpec extends Specification {

    static final ACCOUNT_ID = '1'
    static final DESCRIPTION = 'Item description'
    static final DATETIME = '2017-03-31T12:35:00Z'

    def 'Should create an expense object'() {

        given:
        def data = [account_id: ACCOUNT_ID, description: DESCRIPTION, amount: 10.0, datetime: DATETIME]

        when:
        def expense = new Expense(data)

        then:
        expense
        expense.account_id == ACCOUNT_ID
        expense.description == DESCRIPTION
        expense.amount in BigDecimal && expense.amount == 10.0
        expense.datetime == DATETIME

    }

    def 'Should create an expense object without datetime'() {

        given:
        def data = [account_id: ACCOUNT_ID, description: DESCRIPTION, amount: 16.09]

        when:
        def expense = new Expense(data)

        then:
        expense
        expense.account_id == ACCOUNT_ID
        expense.description == DESCRIPTION
        expense.amount in BigDecimal && expense.amount == 16.09
        expense.datetime

    }

    def 'Should create an expense object rounding the amount'() {

        given:
        def data = [account_id: ACCOUNT_ID, description: DESCRIPTION, amount: 20.09678]

        when:
        def expense = new Expense(data)

        then:
        expense
        expense.account_id == ACCOUNT_ID
        expense.description == DESCRIPTION
        expense.amount in BigDecimal && expense.amount == 20.10
        expense.datetime

    }

    def 'Should not create an empty expense'() {

        when:
        new Expense()

        then:
        thrown(RuntimeException)

    }

    def 'Should not create an expense without \'account_id\' field'() {

        given:
        def data = [description: DESCRIPTION, amount: 15.0]

        when:
        new Expense(data)

        then:
        thrown(RuntimeException)

    }

    def 'Should not create an expense without \'amount\' field'() {

        given:
        def data = [account_id: ACCOUNT_ID, description: DESCRIPTION]

        when:
        new Expense(data)

        then:
        thrown(RuntimeException)

    }

    def 'Should not create an expense with invalid \'datetime\' field'() {

        given:
        def data = [account_id: ACCOUNT_ID, description: DESCRIPTION, amount: 14.0, datetime: 'invalid']

        when:
        new Expense(data)

        then:
        thrown(RuntimeException)

    }

}
