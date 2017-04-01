package io.github.augustoerico.api.auth

import io.github.augustoerico.api.ApiSpec
import io.github.augustoerico.api.Loader
import io.github.augustoerico.api.auth.fixture.Fixture
import io.github.augustoerico.config.Env
import io.github.augustoerico.models.Account

abstract class AuthApiSpec extends ApiSpec {

    /**
     * Shortcut to populate the database
     */
    def load() {
        def accounts = Fixture.ACCOUNTS.collect {
            new Account(it).asJson()
        }
        Loader.create(vertx)
                .withCollection(Env.accountsCollection())
                .withItems(accounts)
                .load()
    }

    @Override
    def setupContextSpec() { }
}
