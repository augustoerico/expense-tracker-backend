package io.github.augustoerico.expenses.handlers

import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.github.augustoerico.models.Expense
import io.vertx.core.Future
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

class CreateExpenseHandler {

    static final Logger LOGGER = LoggerFactory.getLogger CreateExpenseHandler

    HttpServerResponse response

    static create() {
        new CreateExpenseHandler()
    }

    def handle = { RoutingContext context ->

        LOGGER.info "[POST] ${context.normalisedPath()}"

        response = context.response()

        def body = context.getBodyAsJson().map
        def user = context.user().jwtToken.map
        def expense = new Expense([account_id: user._id] + body)

        Repository.create(context.vertx())
                .save(Env.expensesCollection(), expense.asJson(),
                    handleResult.curry(expense))
    }

    def handleResult = { Expense expense, Future future ->
        if (future.succeeded()) {
            expense.set_id(future.result())
            response.setStatusCode(201).end(expense.asJson().encodePrettily())
        } else {
            def ex = future.cause()
            LOGGER.error ex
            response.setStatusCode(500).end(ex.message)
        }
    }

}
