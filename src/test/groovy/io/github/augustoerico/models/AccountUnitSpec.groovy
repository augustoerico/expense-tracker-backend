package io.github.augustoerico.models

import io.github.augustoerico.auth.enums.AccountType
import spock.lang.Specification

class AccountUnitSpec extends Specification {

    def 'Should create a regular account'() {

        when:
        def account = new Account([username: 'username', password: 'password'])

        then:
        account
        account.username == 'username'
        account.password
        account.type == AccountType.REGULAR.name()

    }

    def 'Should create an admin account'() {

        when:
        def account = new Account([username: 'username', password: 'password', type: 'admin'])

        then:
        account
        account.username == 'username'
        account.password
        account.type == AccountType.ADMIN.name()

    }

    def 'Should throw an exception for short username'() {

        when:
        new Account([username: 'us', 'password': 'password'])

        then:
        def ex = thrown RuntimeException
        ex.message == 'username is too short'

    }

    def 'Should create a regular account for any type other than "admin"'() {

        when:
        def account = new Account([username: 'username', password: 'password', type: 'invalid'])

        then:
        account
        account.username == 'username'
        account.password
        account.type == AccountType.REGULAR.name()

    }

    def 'Should create an admin account ignoring type case'() {

        when:
        def account = new Account([username: 'username', password: 'password', type: 'AdMiN'])

        then:
        account
        account.username == 'username'
        account.password
        account.type == AccountType.ADMIN.name()

    }

}
