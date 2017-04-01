package io.github.augustoerico.api.auth

import org.apache.http.HttpStatus

class SignUpApiSpec extends AuthApiSpec{

    static final PATH = '/sign_up'

    def setupContext() {
        // Create 2 entries in DB
        load()
    }

    def 'Should create a new regular account'() {

        given:
        def body = [username: 'guto', password: 'guto-password']

        when:
        def response = restClient.post path: PATH, body: body
        def data = response.responseData

        then:
        response.status == HttpStatus.SC_CREATED

        and:
        data
        data.id
        data.username == 'guto'
        data.password ==~ /\.{44}=/
        data.type == 'REGULAR'

    }

    def 'Should create a new admin account'() {

        given:
        def body = [username: 'guto', password: 'guto-password', type: 'admin']

        when:
        def response = restClient.post path: PATH, body: body
        def data = response.responseData

        then:
        response.status == HttpStatus.SC_CREATED

        and:
        data
        data.id
        data.username == 'guto'
        data.password ==~ /\.{44}=/
        data.type == 'ADMIN'

    }

}
