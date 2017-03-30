package io.github.augustoerico.api

import io.github.augustoerico.Application
import io.github.augustoerico.config.Env
import io.vertx.core.Vertx
import io.vertx.core.http.HttpClient
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

class HealthApiSpec extends Specification {

    @Shared
    HttpClient client

    def setupServer() {
        Application.main()
        Thread.sleep(1000)
    }

    def setup() {
        setupServer()
        Vertx vertx = Vertx.vertx()
        client = vertx.createHttpClient()
    }

    def 'Should get server health'() {
        def async = new AsyncConditions()

        when:
        client.get(3000, 'localhost', '/health') { response ->
            if (response.statusCode() == 200) {
                async.evaluate { true }
            }
        }
        .end()

        then:
        async.await(Env.testWaitTime())

    }

}
