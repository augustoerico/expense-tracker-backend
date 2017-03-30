package io.github.augustoerico.models

import io.github.augustoerico.config.Env

import java.security.MessageDigest

class PasswordHash {

    def hash

    PasswordHash(String password) {
        validate(password)

        hash = MessageDigest.getInstance('SHA-256')
                .digest(password.getBytes('UTF-8'))
                .encodeBase64()
                .toString()
    }

    def validate(password) {

        if (!password) {
            throw new RuntimeException('password not provided')
        } else if (password.size() < Env.passwordSize()) {
            throw new RuntimeException('password is too short')
        }

    }

}
