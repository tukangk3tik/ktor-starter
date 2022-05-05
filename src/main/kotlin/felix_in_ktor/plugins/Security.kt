package felix_in_ktor.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import felix_in_ktor.models.base_response.FailResponse
import felix_in_ktor.utils.StringConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureSecurity() {

    val strConfig = StringConfig.getInstance()

    install(Authentication) {
        jwt("auth-jwt") {
            realm = strConfig.jwtMyRealm
            verifier(JWT.require(Algorithm.HMAC256(strConfig.jwtSecret))
                .withAudience(strConfig.jwtAudience)
                .withIssuer(strConfig.jwtIssuer)
                .build())

            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, FailResponse(message = "Token is invalid or expired"))
            }
        }
    }
}