package io.github.augustoerico.models

import spock.lang.Specification

class ExpenseUnitSpec extends Specification {

    static final ACCOUNT_ID = '1'
    static final ITEM_DESCRIPTION = 'Item description'

    def 'Should create an expense object'() {

        given:
        def data = [account_id: ACCOUNT_ID, description: ITEM_DESCRIPTION, amount: 10.0]

        when:
        def expense = new Expense(data)

        then:
        expense
        expense.account_id == ACCOUNT_ID
        expense.description == ITEM_DESCRIPTION
        expense.amount in BigDecimal && expense.amount == 10.0
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
        def data = [description: ITEM_DESCRIPTION, amount: 15.0]

        when:
        new Expense(data)

        then:
        thrown(RuntimeException)

    }

    def 'Should not create an expense without \'amount\' field'() {

        given:
        def data = [account_id: ACCOUNT_ID, description: ITEM_DESCRIPTION]

        when:
        new Expense(data)

        then:
        thrown(RuntimeException)

    }

}
