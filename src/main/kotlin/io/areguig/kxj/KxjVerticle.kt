package io.areguig.kxj

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.Router
import org.jooq.DSLContext
import org.jooq.impl.DSL


class KxjVerticle : AbstractVerticle() {

    override fun start(startFuture: Future<Void>) {
        val db_config = config().getJsonObject("dataSource")

        val dsl = DSL.using(
                db_config.getString("url"),
                db_config.getString("username"),
                db_config.getString("password")
        )

        val router = createRouter(dsl)
        println(config())


        vertx.createHttpServer()
                .requestHandler { router.accept(it) }
                .listen(config().getInteger("http.port", 8080)) { result ->
                    if (result.succeeded()) {
                        startFuture.complete()
                    } else {
                        startFuture.fail(result.cause())
                    }
                }

    }

    private fun createRouter(dsl: DSLContext) = Router.router(vertx).apply {
        get("/").handler({ req -> req.response().end(JsonArray(getCharacters(dsl)).encode()) })
    }


}