package io.github.augustoerico.health

import io.vertx.ext.web.RoutingContext

class GetHealthHandler {

    static handler = { RoutingContext context ->
        def response = context.response()
        response.putHeader('content-type', 'application/json')
                .end("{\"status\":\"OK\",\"checkedAt\":\"${new Date()}\"}")
    }
}
