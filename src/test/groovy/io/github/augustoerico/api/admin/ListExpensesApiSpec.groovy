package io.github.augustoerico.api.admin

import io.github.augustoerico.api.AuthenticatedApiSpec
import org.apache.http.HttpStatus

class ListExpensesApiSpec extends AuthenticatedApiSpec {

    @Override
    def setupContextSpec() {
        this.populate()
        this.credentials = [username: 'admin', password: 'admin-password']
        super.setupContextSpec()
    }

    def 'Should list all expenses'() {

        when:
        def response = restClient.get path: '/admin/expenses'
        def data = response.responseData

        then:
        response.status == HttpStatus.SC_OK

        and:
        data
        data.size() == 5
        data.every {
            it._id && it.account_id && it.amount && it.description && it.datetime
        }

    }

    // TODO implement list with date filter

}
