package felix_in_ktor.models.base_response

import felix_in_ktor.utils.successFetch
import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse<T>(
    val status : String = successFetch,
    val data: T? = null
)
