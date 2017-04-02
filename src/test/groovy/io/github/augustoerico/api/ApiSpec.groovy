package io.github.augustoerico.api

import de.flapdoodle.embed.mongo.MongodExecutable
import groovyx.net.http.RESTClient
import io.github.augustoerico.api.loader.AccountsLoader
import io.github.augustoerico.api.loader.ExpensesLoader
import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.vertx.core.Vertx
import spock.lang.Shared
import spock.lang.Specification

class ApiSpec extends Specification {

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

        setupContextSpec()
    }

    def cleanupSpec() {
        executable.stop()
        vertx.close()
    }

    def populate() {
        AccountsLoader.create(vertx).load()
        ExpensesLoader.create(vertx).load()
    }

    /**
     * Override to add behavior to setupSpec
     */
    def setupContextSpec() { }

}
