package felix_in_ktor.models.base_response

import felix_in_ktor.utils.failFetch
import kotlinx.serialization.Serializable

@Serializable
data class FailResponse(
    val status : String = failFetch,
    val message: String
)
