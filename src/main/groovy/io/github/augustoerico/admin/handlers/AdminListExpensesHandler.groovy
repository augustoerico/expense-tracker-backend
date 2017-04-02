package io.github.augustoerico.admin.handlers

import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.vertx.core.Future
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

class AdminListExpensesHandler {

    static final Logger LOGGER = LoggerFactory.getLogger AdminListExpensesHandler

    HttpServerResponse response

    static create() {
        new AdminListExpensesHandler()
    }

    def handle = { RoutingContext context ->
        LOGGER.info "[GET] ${context.normalisedPath()}"

        response = context.response()

        Repository.create(context.vertx()).find(Env.expensesCollection(),
                handleResult)
    }

    def handleResult = { Future future ->
        if (future.succeeded()) {
            def result = future.result()*.encodePrettily()
            response.setStatusCode(200).end(result as String)
        } else {
            def ex = future.cause()
            LOGGER.error ex.message, ex
            response.setStatusCode(500).end(ex.message)
        }
    }
}
