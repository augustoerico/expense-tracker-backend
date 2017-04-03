package io.github.augustoerico

import io.github.augustoerico.admin.AdminRouter
import io.github.augustoerico.auth.AuthRouter
import io.github.augustoerico.config.Env
import io.github.augustoerico.expenses.ExpensesRouter
import io.github.augustoerico.health.HealthRouter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpMethod
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.CorsHandler

class ServerVerticle extends AbstractVerticle {

    static final Logger LOGGER = LoggerFactory.getLogger ServerVerticle

    Router router

    @Override
    void start(Future future) {
        router = Router.router(vertx)

        def cors = CorsHandler.create('*')
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader('Content-Type')
                .allowedHeader('Authorization')
        router.route().handler(cors)

        registerAppRoutes()

        vertx.createHttpServer()
                .requestHandler(router.&accept)
                .listen(Env.port(), Env.address(), handleResult.curry(future))
    }

    def handleResult = { Future future, Future result ->
        if (result.succeeded()) {
            LOGGER.info "Server running on http://${Env.address()}:${Env.port()}"
            future.complete()
        } else {
            def ex = result.cause()
            LOGGER.error ex.message, ex
            future.fail(ex)
        }
    }

    def registerAppRoutes() {
        router.route().handler BodyHandler.create()

        def authProvider = JWTAuth.create(vertx, Env.authProviderConfig())

        HealthRouter.create(router).route()
        AuthRouter.create(router, authProvider).route()
        ExpensesRouter.create(router, authProvider).route()
        AdminRouter.create(router, authProvider).route()
    }

}
