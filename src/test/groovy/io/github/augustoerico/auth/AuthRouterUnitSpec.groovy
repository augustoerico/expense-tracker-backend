package io.github.augustoerico.auth

import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.groovy.ext.web.Router
import spock.lang.Specification

class AuthRouterUnitSpec extends Specification {

    def 'Should create an AuthRouter instance'() {

        given:
        def router = Mock(Router)
        def authProvider = Mock(JWTAuth)

        when:
        def authRouter = AuthRouter.create(router, authProvider)

        then:
        authRouter in AuthRouter

    }

    // TODO test route() method
    def 'Should create all auth routes'() {

    }

}
