package io.github.augustoerico.api.expenses

import io.github.augustoerico.api.AuthenticatedApiSpec
import org.apache.http.HttpStatus

class ListExpensesApiSpec extends AuthenticatedApiSpec {

    @Override
    def setupContextSpec() {
        this.populate()
        super.setupContextSpec()
    }

    def 'Should list the expenses associated to logged account'() {

        when:
        def response = restClient.get path: '/expenses'
        def data = response.reponseData

        then:
        response.status == HttpStatus.SC_OK

        and:
        data
        data.size() == 3
        data.every {
            it._id && it.account_id && it.amount && it.description && it.datetime &&
                    it.account_id == '1'
        }

    }

    // TODO implement list with date filter

}
