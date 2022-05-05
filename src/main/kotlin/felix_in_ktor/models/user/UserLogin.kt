package felix_in_ktor.models.user

@kotlinx.serialization.Serializable
data class UserLogin(
    val username: String,
    val password: String
)