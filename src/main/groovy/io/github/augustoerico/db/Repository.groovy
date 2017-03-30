package io.github.augustoerico.db

import io.github.augustoerico.config.Env
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient

class Repository {

    static Repository instance

    MongoClient client

    private Repository(MongoClient client) {
        this.client = client
    }

    static create(Vertx vertx) {
        if (!instance) {
            def json = new JsonObject()
                    .put('connection_string', Env.mongoDbUri())
                    .put('db_name', Env.mongoDbName())
            instance = new Repository(MongoClient.createShared(vertx, json))
        }
        instance
    }

    def save(String collection, JsonObject json, Handler handler) {
        client.save(collection, json, handler)
        this
    }

    def find(String collection, Handler handler) {
        client.find(collection, new JsonObject(), handler)
        this
    }

    def findOne(String collection, String id, Handler handler) {
        def query = new JsonObject().put('_id', id)
        client.findOne(collection, query, new JsonObject(), handler)
    }

}
