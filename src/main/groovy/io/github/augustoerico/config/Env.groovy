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
     * Authentication & Authorisation
     */
    static keystore() {
        // 1. Create keystore file:
        //      keytool -genseckey -keystore keystore.jceks -storetype jceks \
        //          -storepass <password> -keyalg HMacSHA256 -keysize 2048 -alias HS256 -keypass <password>
        // 2. Place keystore file in src/main/resources
        System.getenv().KEYSTORE ?: 'keystore.jceks::jceks'
    }

    static keystorePassword() {
        System.getenv().KEYSTORE_PASSWORD ?: 'expense-tracker-password'
    }

    static authProviderConfig() {
        def keyStore = keystore().split('::')
        [keyStore: [path: keyStore[0], type: keyStore[1], password: keystorePassword()]]
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
