package io.github.augustoerico.api.auth

import de.flapdoodle.embed.mongo.MongodExecutable
import groovyx.net.http.RESTClient
import io.github.augustoerico.api.Loader
import io.github.augustoerico.api.TestHelper
import io.github.augustoerico.api.auth.fixture.Fixture
import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.github.augustoerico.models.Account
import io.vertx.core.Vertx
import spock.lang.Shared
import spock.lang.Specification

abstract class AuthApiSpec extends Specification {

    @Shared
    Vertx vertx
    @Shared
    MongodExecutable executable

    @Shared
    RESTClient restClient
    @Shared
    Repository repository

    def setupSpec() {
        vertx = Vertx.vertx()

        TestHelper.setupServer(vertx)

        executable = TestHelper.getMongodExecutable()
        executable.start()

        repository = Repository.create(vertx).getInstance()

        restClient = new RESTClient(Env.appUrl())
        restClient.setContentType('application/json')

        setupContext()
    }

    def cleanupSpec() {
        executable.stop()
        vertx.close()
    }

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

    abstract setupContext()
}
