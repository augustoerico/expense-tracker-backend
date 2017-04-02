package io.github.augustoerico.api.expenses

import groovyx.net.http.HttpResponseException
import io.github.augustoerico.api.AuthenticatedApiSpec
import org.apache.http.HttpStatus

class UpdateExpenseApiSpec extends AuthenticatedApiSpec {

    static final DESCRIPTION = 'Just A Game'
    static final DATETIME = '2017-04-03T12:54:13Z'
    static final BODY = [amount: 99.99, description: DESCRIPTION, datetime: DATETIME]

    @Override
    def setupContextSpec() {
        this.populate()
        super.setupContextSpec()
    }

    def 'Should update an expense entry'() {

        given:
        def expenseId = '1' // it belongs to logged user

        when:
        def response = restClient.put path: "/expenses/$expenseId", body: BODY
        def data = response.responseData

        then:
        response.status == HttpStatus.SC_OK

        and:
        data
        data._id == '1'
        data.account_id == '1'
        data.amount == 99.99
        data.description == DESCRIPTION
        data.datetime == DATETIME

    }

    def 'Should not update an expense that does not belong to logged user'() {

        given:
        def expenseId = '3' // it does not belong to logged user

        when:
        restClient.put path: "/expenses/$expenseId", body: BODY

        then:
        def ex = thrown(HttpResponseException)
        ex.statusCode == HttpStatus.SC_NOT_FOUND

    }

}
