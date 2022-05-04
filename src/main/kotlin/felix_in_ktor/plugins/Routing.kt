package felix_in_ktor.plugins

import felix_in_ktor.routes.indexRoutes
import felix_in_ktor.routes.personRoutes
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing() {
        indexRoutes()
        route("/api") {
            indexRoutes()
            personRoutes()
        }
    }
}
