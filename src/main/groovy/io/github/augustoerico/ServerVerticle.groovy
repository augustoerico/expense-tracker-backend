package io.github.augustoerico

import io.github.augustoerico.auth.AuthRouter
import io.github.augustoerico.config.Env
import io.github.augustoerico.health.HealthRouter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpMethod
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.CorsHandler

class ServerVerticle extends AbstractVerticle {

    Router router

    @Override
    void start(Future future) {
        router = Router.router(vertx)

        def cors = CorsHandler.create('*')
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader('Content-Type')
        router.route().handler(cors)

        registerAppRoutes()

        vertx.createHttpServer()
                .requestHandler(router.&accept)
                .listen(Env.port(), Env.address(), handleResult.curry(future))
    }

    def handleResult = { Future future, result ->
        if (result.succeeded()) {
            println "Server running on http://${Env.address()}:${Env.port()}"
            future.complete()
        } else {
            def ex = result.cause()
            ex.printStackTrace()
            future.fail(ex)
        }
    }

    def registerAppRoutes() {
        def authProvider = JWTAuth.create(vertx, Env.authProviderConfig())

        HealthRouter.create(router).route()
        AuthRouter.create(router, authProvider).route()
    }

}
