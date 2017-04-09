package io.github.augustoerico.api

import groovyx.net.http.HttpResponseException
import org.apache.http.HttpStatus

class ExpenseTrackerApiSpec extends AuthenticatedApiSpec {

    @Override
    def runBefore() {
        // load fixtures to database
        super.runBefore()
        this.populate()
    }

    /**
     * HealthApiSpec.groovy
     */
    def 'Should get server health'() {

        when:
        def response = restClient.get path: '/health'

        then:
        response.status == HttpStatus.SC_OK

    }

    /**
     * SignUpApiSpec.groovy
     */
    static final SIGN_UP_PATH = '/sign_up'

    def 'Should create a new regular account'() {

        given:
        def body = [username: 'guto', password: 'guto-password']

        when:
        def response = restClient.post path: SIGN_UP_PATH, body: body
        def data = response.responseData

        then:
        response.status == HttpStatus.SC_CREATED

        and:
        data
        data._id
        data.username == 'guto'
        data.password ==~ /^.{43}=$/
        data.type == 'REGULAR'

    }

    def 'Should create a new admin account'() {

        given:
        def body = [username: 'kiko', password: 'kiko-password', type: 'admin']

        when:
        def response = restClient.post path: SIGN_UP_PATH, body: body
        def data = response.responseData

        then:
        response.status == HttpStatus.SC_CREATED

        and:
        data
        data._id
        data.username == 'kiko'
        data.password ==~ /^.{43}=$/
        data.type == 'ADMIN'

    }

    /**
     * SignInApiSpec.groovy
     */
    static final SIGN_IN_PATH = '/sign_in'

    def 'Should authenticate the user and return a token'() {

        given:
        def body = [username: 'erico', password: 'erico-password']

        when:
        def response = restClient.post path: SIGN_IN_PATH, body: body
        def data = response.responseData

        then:
        data
        data.token ==~ /^Bearer .+=$/

    }

    def 'Should not authenticate the user with invalid password'() {

        given:
        def body = [usernam: 'erico', password: 'invalid-password']

        when:
        restClient.post path: SIGN_IN_PATH, body: body

        then:
        def ex = thrown(HttpResponseException)
        ex.statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY
    }

    /**
     * CreateExpenseApiSpec.groovy
     */
    static final ITEM_DESCRIPTION = 'Item description'

    def 'Should create an expense entry'() {

        given:
        def body = [amount: 10.0, description: ITEM_DESCRIPTION]

        when:
        def response = restClient.post path: '/expenses', body: body
        def data = response.responseData

        then:
        response.status == HttpStatus.SC_CREATED

        and:
        data
        data._id
        data.account_id == '1'
        data.amount == 10.0
        data.description == ITEM_DESCRIPTION
        data.datetime

    }

    def 'Should list the expenses associated to logged account'() {

        when:
        def response = restClient.get path: '/expenses'
        def data = response.responseData

        then:
        response.status == HttpStatus.SC_OK

        and:
        data
        data.size() == 4
        data.every {
            it._id && it.account_id && it.amount && it.description && it.datetime &&
                    it.account_id == '1'
        }

    }

    /**
     * UpdateExpenseApiSpec.groovy
     */
    static final DATETIME = '2017-04-03T12:54:13Z'
    static final BODY = [amount: 99.99, description: ITEM_DESCRIPTION, datetime: DATETIME]

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
        data.description == ITEM_DESCRIPTION
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

    /**
     * AdminListExpensesApiSpec.groovy
     */

    static final ADMIN_TOKEN = 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiIzIiwidXNlcm5hbWUiOiJhZG1pbiIsI' +
            'nBhc3N3b3JkIjoiam5EOXZRUUF0NklWT2YwViswcTRiQktmZkwyWkpoMjdEWlhCamZqZXdYYz0iLCJ0eXBlIjoiQURNSU4iLCJwZXJta' +
            'XNzaW9ucyI6WyJhZG1pbiJdLCJpYXQiOjE0OTE3NTUzODN9.vMt43wsX2OntQWTjaxBjmfLmEjP0SWbkah_7r_F_KqA='

    def 'Should list all expenses'() {

        given:
        restClient.setHeaders([Authorization: ADMIN_TOKEN])

        when:
        def response = restClient.get path: '/admin/expenses'
        def data = response.responseData

        then:
        response.status == HttpStatus.SC_OK

        and:
        data
        data.size() == 6
        data.every {
            it._id && it.account_id && it.amount && it.description && it.datetime
        }

    }
}
