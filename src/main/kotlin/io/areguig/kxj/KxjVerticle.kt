package io.areguig.kxj

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import java.util.*


class KxjVerticle : AbstractVerticle () {

    private val properties = Properties()


    override fun start(startFuture: Future<Void>){
        vertx.createHttpServer()
                .requestHandler { req ->
                    req.response()
                            .putHeader("content-type", "text/plain")
                            .end("Hello from K-X-J")
                }.listen(8080)
    }
}