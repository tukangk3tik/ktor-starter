package felix_in_ktor.models.person

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val age: Int
)

val personStorage = mutableListOf(
    Person(id = 1, firstName = "Yusuf", lastName = "Hai", age = 24),
    Person(id = 2, firstName = "Reno", lastName = "Karno", age = 20)
)