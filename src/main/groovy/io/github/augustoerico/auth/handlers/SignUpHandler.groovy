package io.github.augustoerico.auth.handlers

import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.github.augustoerico.models.Account
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.Future
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

class SignUpHandler {

    static final Logger LOGGER = LoggerFactory.getLogger SignUpHandler

    static create() {
        new SignUpHandler()
    }

    def handle = { RoutingContext context ->
        LOGGER.info "[POST] ${context.normalisedPath()}"

        def response = context.response()
        def account = new Account(context.getBodyAsJson().map)

        Repository.create(context.vertx()).save(Env.accountsCollection(), account.asJson(),
                handleResult.curry(response, account))
    }

    def handleResult = { HttpServerResponse response, Account account, Future future ->

        if (future.succeeded()) {
            account._id = future.result()
            response.setStatusCode(201).end(account.asJson().encodePrettily())
        } else {
            def ex = future.cause()
            LOGGER.error ex.message, ex
            def json = new JsonObject([message: ex.message]).encodePrettily()
            response.setStatusCode(500).end(json)
        }

    }

}
