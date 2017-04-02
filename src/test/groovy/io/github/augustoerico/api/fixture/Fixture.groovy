package io.github.augustoerico.api.fixture

class Fixture {

    static final ACCOUNTS = [
            [_id: '1', username: 'erico', password: 'erico-password'],
            [_id: '2', username: 'kiko', password: 'kiko-password'],
            [_id: '3', username: 'admin', password: 'admin-password', type: 'ADMIN']
    ]

    static final EXPENSES = [
            [_id: '1', account_id: '1', amount: 199.99, description: 'A Game - Collector\'s Edition',
                datetime: '2017-03-31T12:54:12Z'],
            [_id: '2', account_id: '1', amount: 40.0, description: 'The Book - Step by Step',
                datetime: '2017-04-01T16:31:08Z'],
            [_id: '3', account_id: '2', amount: 12.8, description: 'Good\'O Soda Retro',
                datetime: '2017-04-02T08:12:43Z'],
            [_id: '4', account_id: '1', amount: 399.0, description: 'A BadAss Guitar',
                datetime: '2017-04-02T11:40:15Z'],
            [_id: '5', account_id: '2', amount: 67.8, description: 'DIY Toolkit',
                datetime: '2017-04-03T16:24:50Z']
    ]

}
