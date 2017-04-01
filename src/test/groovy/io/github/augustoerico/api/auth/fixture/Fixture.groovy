package io.github.augustoerico.api.auth.fixture

import io.github.augustoerico.models.PasswordHash

class Fixture {

    static final ACCOUNTS = [
            [_id: '1', username: 'erico', password: new PasswordHash('erico-password')],
            [_id: '2', username: 'kiko', password: new PasswordHash('kiko-password')]
    ]

}
