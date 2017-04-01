package io.github.augustoerico.auth

import io.github.augustoerico.auth.handlers.SignInHandler
import io.github.augustoerico.auth.handlers.SignUpHandler
import io.vertx.ext.auth.AuthProvider
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

class AuthRouter {

    Router router
    JWTAuth authProvider

    private AuthRouter(Router router, JWTAuth authProvider) {
        this.router = router
        this.authProvider = authProvider
    }

    static create(Router router, AuthProvider authProvider) {
        new AuthRouter(router, authProvider)
    }

    def route() {

        router.route().handler BodyHandler.create()

        router.post('/sign_up').handler SignUpHandler.create().handle
        router.post('/sign_in').handler SignInHandler.create(authProvider).handle

    }

}
