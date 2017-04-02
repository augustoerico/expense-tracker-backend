package io.github.augustoerico.admin

import io.github.augustoerico.admin.handlers.AdminListExpensesHandler
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.JWTAuthHandler

class AdminRouter {

    Router router
    JWTAuth jwtAuthProvider

    private AdminRouter(Router router, JWTAuth jwtAuthProvider) {
        this.router = router
        this.jwtAuthProvider = jwtAuthProvider
    }

    static create(Router router, JWTAuth jwtAuthProvider) {
        new AdminRouter(router, jwtAuthProvider)
    }

    def route() {
        router.route('/admin/*')
                .handler JWTAuthHandler.create(jwtAuthProvider)
                    .addAuthority('admin').&handle

        router.get('/admin/expenses')
                .handler AdminListExpensesHandler.create().handle
    }

}
