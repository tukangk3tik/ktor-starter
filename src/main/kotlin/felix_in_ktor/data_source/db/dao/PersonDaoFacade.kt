package felix_in_ktor.data_source.db.dao

import felix_in_ktor.models.person.Person

interface PersonDaoFacade {
    suspend fun allPersons(): List<Person>
    suspend fun getPerson(id: Int): Person?
    suspend fun addNewPerson(firstName: String, lastName: String, age: Int): Person?
    suspend fun editPerson(id: Int, firstName: String, lastName: String, age: Int): Boolean
    suspend fun deletePerson(id: Int): Boolean
}