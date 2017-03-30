package io.github.augustoerico

import io.vertx.core.Vertx

class Application {

    static main(args) {
        Vertx vertx = Vertx.vertx()
        vertx.with {
            deployVerticle(ServerVerticle.name) {
                println 'Server verticle deployed'
            }
        }
    }

}
