package felix_in_ktor.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.indexRoutes() {
    get {
        call.respondText("Ktor - API v1", status = HttpStatusCode.OK)
    }
}