package io.github.augustoerico.models

import spock.lang.Specification

class PasswordHashUnitSpec extends Specification {

    def 'Should create a password hash'() {

        when:
        def passwordHash = new PasswordHash('password')

        then:
        passwordHash
        passwordHash.hash

    }

    def 'Should throw an exception when password is not provided'() {

        when:
        new PasswordHash()

        then:
        def ex = thrown RuntimeException
        ex.message == 'password not provided'

    }

    def 'Should throw an exception for short password'() {

        when:
        new PasswordHash('foo')

        then:
        def ex = thrown RuntimeException
        ex.message == 'password is too short'

    }

    def 'Should generate the same hash'() {

        when:
        def hash1 = new PasswordHash('password').hash
        def hash2 = new PasswordHash('password').hash

        then:
        hash1 == hash2

    }

}
