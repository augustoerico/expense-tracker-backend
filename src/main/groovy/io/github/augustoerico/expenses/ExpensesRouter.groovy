package io.github.augustoerico.expenses

import io.github.augustoerico.expenses.handlers.CreateExpenseHandler
import io.github.augustoerico.expenses.handlers.ListExpensesHandler
import io.github.augustoerico.expenses.handlers.UpdateExpenseHandler
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.JWTAuthHandler

class ExpensesRouter {

    Router router
    JWTAuth jwtAuthProvider

    private ExpensesRouter(Router router, JWTAuth jwtAuthProvider) {
        this.router = router
        this.jwtAuthProvider = jwtAuthProvider
    }

    static create(Router router, JWTAuth jwtAuthProvider) {
        new ExpensesRouter(router, jwtAuthProvider)
    }

    def route() {
        router.route('/expenses/*')
                .handler JWTAuthHandler.create(jwtAuthProvider).&handle

        router.post('/expenses')
                .handler CreateExpenseHandler.create().handle
        router.get('/expenses')
                .handler ListExpensesHandler.create().handle
        router.put('/expenses/:expenseId')
                .handler UpdateExpenseHandler.create().handle
    }

}
