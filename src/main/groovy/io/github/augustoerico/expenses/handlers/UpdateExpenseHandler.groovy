package io.github.augustoerico.expenses.handlers

import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.github.augustoerico.models.Expense
import io.vertx.core.Future
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

class UpdateExpenseHandler {

    static final Logger LOGGER = LoggerFactory.getLogger UpdateExpenseHandler

    HttpServerResponse response
    Boolean notFound

    static create() {
        new UpdateExpenseHandler()
    }

    def handle = { RoutingContext context ->

        LOGGER.info "[PUT] ${context.normalisedPath()}"

        response = context.response()

        def body = context.getBodyAsJson().map
        def user = context.user().jwtToken.map
        def expenseId = context.request().getParam('expenseId')

        def query = [_id: expenseId, account_id: user._id]
        def expense = new Expense(body + query)

        Repository.create(context.vertx())
                .find(Env.expensesCollection(), new JsonObject(query), handleFind)
                .save(Env.expensesCollection(), expense.asJson(),
                    handleSave.curry(expense))

    }

    def handleFind = { Future future ->
        if (future.succeeded()) {
            notFound = !future.result()
        } else {
            def ex = future.cause()
            LOGGER.error ex.message, ex
            response.setStatusCode(500).end(ex.message)
        }
    }

    def handleSave = { Expense expense, Future future ->
        if (notFound) {
            response.setStatusCode(404).end()
            return
        }
        if (future.succeeded()) {
            response.setStatusCode(200).end(expense.asJson().encodePrettily())
        } else {
            def ex = future.cause()
            LOGGER.error ex.message, ex
            response.setStatusCode(500).end(ex.message)
        }
    }

}
