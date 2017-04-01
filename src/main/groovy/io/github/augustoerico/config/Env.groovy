package io.github.augustoerico.config

class Env {

    /**
     * Infrastructure
     */
    static port() {
        def port = System.getenv().PORT ?: '3000'
        Integer.parseInt(port)
    }

    static address() {
        System.getenv().ADDRESS ?: '0.0.0.0'
    }

    /**
     * Mongo DB
     */
    static mongoDbUri() {
        System.getenv().MONGO_DB_URI ?: 'your-connection-uri'
    }

    static mongoDbName() {
        System.getenv().MONGO_DB_NAME ?: 'db-name'
    }

    /**
     * App defaults
     */
    static usernameSize() {
        def usernameSize = System.getenv().USERNAME_SIZE ?: '3'
        Integer.parseInt(usernameSize)
    }

    static passwordSize() {
        def passwordSize = System.getenv().PASSWORD_SIZE ?: '6'
        Integer.parseInt(passwordSize)
    }

    /**
     * Tests
     */
    static Double testWaitTime() {
        def time = System.getenv().TEST_WAIT_TIME ?: '5.0'
        Double.parseDouble(time)
    }

}
