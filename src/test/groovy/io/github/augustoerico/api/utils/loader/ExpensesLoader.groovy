package io.github.augustoerico.api.utils.loader

import io.github.augustoerico.api.utils.fixture.Fixture
import io.github.augustoerico.config.Env
import io.github.augustoerico.models.Expense
import io.vertx.core.Vertx

class ExpensesLoader extends Loader {

    private ExpensesLoader(Vertx vertx) {
        this.vertx = vertx
        this.collection = Env.expensesCollection()
        this.items = Fixture.EXPENSES.collect { new Expense(it).asJson() }
    }

    static create(Vertx vertx) {
        new ExpensesLoader(vertx)
    }

}
