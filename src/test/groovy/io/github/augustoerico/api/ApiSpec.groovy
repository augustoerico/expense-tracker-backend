package io.github.augustoerico.api

import de.flapdoodle.embed.mongo.MongodExecutable
import groovyx.net.http.RESTClient
import io.github.augustoerico.api.utils.loader.AccountsLoader
import io.github.augustoerico.api.utils.loader.ExpensesLoader
import io.github.augustoerico.api.utils.TestUtils
import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.vertx.core.Vertx
import spock.lang.Shared
import spock.lang.Specification

abstract class ApiSpec extends Specification {

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

        TestUtils.setupServer(vertx)

        executable = TestUtils.getMongodExecutable()
        executable.start()

        repository = Repository.create(vertx).getInstance()

        restClient = new RESTClient(Env.appUrl())
        restClient.setContentType('application/json')

        runBefore()

    }

    def cleanupSpec() {
        vertx.close {
            println 'vertx closed'
            executable.stop()
            restClient.shutdown()
        }
    }

    def populate() {
        AccountsLoader.create(vertx).load()
        ExpensesLoader.create(vertx).load()
    }

    /**
     * Override to add behavior to setupSpec
     */
    def runBefore() { }

}
