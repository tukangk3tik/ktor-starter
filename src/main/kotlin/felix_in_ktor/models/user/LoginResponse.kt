package felix_in_ktor.models.user

@kotlinx.serialization.Serializable
data class LoginResponse(
    val tokenType : String,
    val expiresIn : Int,
    val accessToken: String
)