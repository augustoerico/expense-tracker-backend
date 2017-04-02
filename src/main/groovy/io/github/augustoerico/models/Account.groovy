package io.github.augustoerico.models

import io.github.augustoerico.models.enums.AccountType
import io.github.augustoerico.config.Env
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory

class Account {

    def _id
    def username
    def password
    def type

    Account(Map map) {
        validate(map)

        _id = map._id
        username = map.username
        password = new PasswordHash(map.password as String).hash
        type = (map.type ?: '').equalsIgnoreCase(AccountType.ADMIN.name()) ?
                AccountType.ADMIN.name() : AccountType.REGULAR.name()
    }

    static validate(Map map) {

        if (!map.username) {
            throw new RuntimeException('username not provided')
        } else if (map.username.size() < Env.usernameSize()) {
            throw new RuntimeException('username is too short')
        }

    }

    def asJson() {
        def obj = [username: username, password: password, type: type] +
                (_id ? [_id: _id] : [:])
        new JsonObject(obj)
    }
}
