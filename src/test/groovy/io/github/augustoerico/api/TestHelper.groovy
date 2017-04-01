package io.github.augustoerico.api

import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.IMongodConfig
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import io.github.augustoerico.ServerVerticle
import io.github.augustoerico.config.Env
import io.vertx.core.Vertx
import spock.util.concurrent.AsyncConditions

class TestHelper {

    static setupServer(Vertx vertx) {
        def async = new AsyncConditions()
        vertx.deployVerticle(ServerVerticle.name) {
            async.evaluate { true }
        }
        async.await(Env.testWaitTime())
    }

    static getMongodExecutable() {
        MongodStarter starter = MongodStarter.getDefaultInstance()

        String bindIp = Env.mongoDbHost()
        int port = Env.mongoDbPort()

        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build()

        starter.prepare(mongodConfig)
    }
}
