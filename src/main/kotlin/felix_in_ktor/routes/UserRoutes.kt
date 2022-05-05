package felix_in_ktor.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import felix_in_ktor.models.base_response.SuccessResponse
import felix_in_ktor.models.user.LoginResponse
import felix_in_ktor.models.user.UserLogin
import felix_in_ktor.utils.StringConfig
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoutes() {
    val strConfig = StringConfig.getInstance()

    post("/login") {
        val user = call.receive<UserLogin>()
        val expired = 60000

        val token = JWT.create()
            .withAudience(strConfig.jwtAudience)
            .withIssuer(strConfig.jwtIssuer)
            .withClaim("username", user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + expired))
            .sign(Algorithm.HMAC256(strConfig.jwtSecret))

        val response = SuccessResponse(data = LoginResponse("Bearer", expired, token))
        call.respond(response)
    }
}