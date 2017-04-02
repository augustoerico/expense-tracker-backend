package io.github.augustoerico.api.expenses

import io.github.augustoerico.api.AuthenticatedApiSpec
import org.apache.http.HttpStatus
import spock.lang.Stepwise

@Stepwise
class CreateExpenseApiSpec extends AuthenticatedApiSpec {

    static final DESCRIPTION = 'Item description'

    @Override
    def setupContextSpec() {
        this.populate()
    }

    def 'Should create an expense entry'() {

        given:
        def body = [amount: 10.0, description: DESCRIPTION]

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
        data.description == DESCRIPTION
        data.datetime

    }

}
