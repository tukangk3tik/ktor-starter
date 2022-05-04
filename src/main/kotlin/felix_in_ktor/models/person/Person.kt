package felix_in_ktor.models.person

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val id: String,
    val firstName: String,
    val lastName: String,
    val age: Int
)

val personStorage = mutableListOf(
    Person(id = "P0001", firstName = "Yusuf", lastName = "Hai", age = 24),
    Person(id = "P0002", firstName = "Reno", lastName = "Karno", age = 20)
)