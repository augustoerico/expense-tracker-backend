package io.github.augustoerico.api.auth

import io.github.augustoerico.api.ApiSpec
import org.apache.http.HttpStatus

class SignUpApiSpec extends ApiSpec{

    static final PATH = '/sign_up'

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
        data._id
        data.username == 'guto'
        data.password ==~ /^.{43}=$/
        data.type == 'REGULAR'

    }

    def 'Should create a new admin account'() {

        given:
        def body = [username: 'kiko', password: 'kiko-password', type: 'admin']

        when:
        def response = restClient.post path: PATH, body: body
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

}
