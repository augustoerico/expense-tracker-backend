package io.github.augustoerico.auth.handlers

import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

class SignUpHandler {

    static final Logger LOGGER = LoggerFactory.getLogger SignUpHandler

    static create() {
        new SignUpHandler()
    }

    def handle = { RoutingContext context ->

        LOGGER.info "[GET] ${context.normalisedPath()}"

        def response = context.response()
        def body = context.getBodyAsJson().map

        response.setStatusCode(200).end('not implemented')

    }

}
