package io.github.augustoerico.api.health

import io.github.augustoerico.api.ApiSpec
import org.apache.http.HttpStatus

class HealthApiSpec extends ApiSpec {

    def cleanupSpec() {
        restClient.shutdown()
    }

    def 'Should get server health'() {

        when:
        def response = restClient.get path: '/health'

        then:
        response.status == HttpStatus.SC_OK

    }

}
