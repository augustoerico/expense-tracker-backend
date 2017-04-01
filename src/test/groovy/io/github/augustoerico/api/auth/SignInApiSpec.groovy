package io.github.augustoerico.api.auth

import groovyx.net.http.HttpResponseException
import org.apache.http.HttpStatus

class SignInApiSpec extends AuthApiSpec {

    static final PATH = '/sign_in'

    @Override
    def setupContextSpec() {
        // load fixtures to database
        load()
    }

    def 'Should authenticate the user and return a token'() {

        given:
        def body = [username: 'erico', password: 'erico-password']

        when:
        def response = restClient.post path: PATH, body: body
        def data = response.responseData

        then:
        data
        data.token ==~ /^.+=$/

    }

    def 'Should not authenticate the user with invalid password'() {

        given:
        def body = [usernam: 'erico', password: 'invalid-password']

        when:
        restClient.post path: PATH, body: body

        then:
        def ex = thrown(HttpResponseException)
        ex.statusCode == HttpStatus.SC_UNPROCESSABLE_ENTITY
    }

}
