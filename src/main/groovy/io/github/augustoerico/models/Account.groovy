package io.github.augustoerico.models

import io.github.augustoerico.models.enums.AccountType
import io.github.augustoerico.config.Env

class Account {

    def username
    def password
    def type

    Account(Map map) {
        validate(map)

        username = map.username
        password = new PasswordHash(map.password as String).hash
        type = (map.type ?: '').equalsIgnoreCase(AccountType.ADMIN.name()) ?
                AccountType.ADMIN.name() : AccountType.REGULAR.name()
    }

    def validate(Map map) {

        if (!map.username) {
            throw new RuntimeException('username not provided')
        } else if (map.username.size() < Env.usernameSize()) {
            throw new RuntimeException('username is too short')
        }

    }

}
