package io.github.augustoerico.api.utils.loader

import io.github.augustoerico.api.utils.fixture.Fixture
import io.github.augustoerico.config.Env
import io.github.augustoerico.models.Account
import io.vertx.core.Vertx

class AccountsLoader extends Loader {

    private AccountsLoader(Vertx vertx) {
        this.vertx = vertx
        this.collection = Env.accountsCollection()
        this.items = Fixture.ACCOUNTS.collect { new Account(it).asJson() }
    }

    static create(Vertx vertx) {
        new AccountsLoader(vertx)
    }

}
