package felix_in_ktor

import felix_in_ktor.plugins.*
import felix_in_ktor.utils.StringConfig
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    //instance config
    val strConfig = StringConfig.getInstance()
    strConfig.jwtSecret = environment.config.property("jwt.secret").getString()
    strConfig.jwtIssuer = environment.config.property("jwt.issuer").getString()
    strConfig.jwtAudience = environment.config.property("jwt.audience").getString()
    strConfig.jwtMyRealm = environment.config.property("jwt.realm").getString()

    configureRouting()
    configureSerialization()
    configureSecurity()
}
