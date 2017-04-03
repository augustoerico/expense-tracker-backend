package io.github.augustoerico.auth.handlers

import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.github.augustoerico.models.PasswordHash
import io.vertx.core.Future
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.RoutingContext

class SignInHandler {

    static final Logger LOGGER = LoggerFactory.getLogger SignInHandler

    JWTAuth jwtAuthProvider
    HttpServerResponse response

    SignInHandler(JWTAuth jwtAuthProvider) {
        this.jwtAuthProvider = jwtAuthProvider
    }

    static create(JWTAuth jwtAuthProvider) {
        new SignInHandler(jwtAuthProvider)
    }

    def handle = { RoutingContext context ->

        LOGGER.info "[POST] ${context.normalisedPath()}"

        response = context.response()

        def body = context.getBodyAsJson().map
        def password = new PasswordHash(body.password as String).hash
        def query = new JsonObject([username: body.username, password: password])

        Repository.create(context.vertx()).find(Env.accountsCollection(), query, handleFindAccountResult)

    }

    def handleFindAccountResult = { Future future ->

        if (future.succeeded()) {
            def result = (future.result() as List<JsonObject>).map
            if (result) {
                def userInfo = result.first()
                def permissions = [userInfo.type.toLowerCase()]
                def token = jwtAuthProvider.generateToken(userInfo, [permissions: permissions])
                response.setStatusCode(201).end(new JsonObject([token: "Bearer $token".toString()]).encodePrettily())
            } else {
                def message = 'Wrong username/password combination'
                response.setStatusCode(422).end(new JsonObject([message: message]).encodePrettily())
            }
        } else {
            def ex = future.cause()
            LOGGER.error ex.message, ex
            def json = new JsonObject([message: ex.message]).encodePrettily()
            response.setStatusCode(500).end(json)
        }

    }
}
