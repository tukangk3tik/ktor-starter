package felix_in_ktor.models.person

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePerson(
    val firstName: String,
    val lastName: String,
    val age: Int
)