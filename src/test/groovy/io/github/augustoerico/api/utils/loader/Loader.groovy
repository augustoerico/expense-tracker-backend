package io.github.augustoerico.api.utils.loader

import io.github.augustoerico.config.Env
import io.github.augustoerico.db.Repository
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import spock.util.concurrent.AsyncConditions

import java.util.concurrent.atomic.AtomicInteger

class Loader {

    Vertx vertx
    String collection
    List<JsonObject> items

    Loader() { }

    private Loader(Vertx vertx) {
        this.vertx = vertx
    }

    static create(Vertx vertx) {
        new Loader(vertx)
    }

    def withCollection(String collection) {
        this.collection = collection
        this
    }

    def withItems(List items) {
        this.items = items
        this
    }

    def load() {
        def atomic = new AtomicInteger()
        def async = new AsyncConditions()

        items.collect {
            Repository.create(vertx).client.insert(collection, it) { Future future ->

                if (future.succeeded()) {
                    atomic.incrementAndGet()
                    if (atomic.get() == items.size()) {
                        async.evaluate { true }
                    }
                } else {
                    future.cause().printStackTrace()
                }

            }
        }
        async.await(Env.testWaitTime())
    }

}
