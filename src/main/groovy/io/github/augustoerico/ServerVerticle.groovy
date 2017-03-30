package io.github.augustoerico

import io.github.augustoerico.config.Env
import io.github.augustoerico.health.HealthRouter

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.CorsHandler

class ServerVerticle extends AbstractVerticle {

    @Override
    void start(Future future) {
        Router router = Router.router(vertx)

        def cors = CorsHandler.create('*')
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader('Content-Type')

        router.route().handler(cors)

        HealthRouter.create(router).route()
        // Your app routes go here!

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

}
