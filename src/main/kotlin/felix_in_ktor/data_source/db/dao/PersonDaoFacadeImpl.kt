package felix_in_ktor.data_source.db.dao

import felix_in_ktor.data_source.db.DatabaseFactory.dbQuery
import felix_in_ktor.data_source.db.entity.Persons
import felix_in_ktor.models.person.Person
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*

class PersonDaoFacadeImpl: PersonDaoFacade {

    private fun resultRowToPerson(row: ResultRow) = Person(
        id = row[Persons.id],
        firstName = row[Persons.firstName],
        lastName = row[Persons.lastName],
        age = row[Persons.age],
    )

    override suspend fun allPersons(): List<Person> = dbQuery {
        Persons.selectAll().map(::resultRowToPerson)
    }

    override suspend fun getPerson(id: Int): Person? = dbQuery {
        Persons.select { Persons.id eq id }
            .map(::resultRowToPerson)
            .singleOrNull()
    }

    override suspend fun addNewPerson(firstName: String, lastName: String, age: Int): Person? = dbQuery {
        val insertStatement = Persons.insert {
            it[Persons.firstName] = firstName
            it[Persons.lastName] = lastName
            it[Persons.age] = age
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPerson)
    }

    override suspend fun editPerson(id: Int, firstName: String, lastName: String, age: Int): Boolean = dbQuery {
        Persons.update({ Persons.id eq id}) {
            it[Persons.firstName] = firstName
            it[Persons.lastName] = lastName
            it[Persons.age] = age
        } > 0
    }

    override suspend fun deletePerson(id: Int): Boolean = dbQuery {
        Persons.deleteWhere { Persons.id eq id } > 0
    }

}

val personDao: PersonDaoFacade = PersonDaoFacadeImpl().apply {
    runBlocking {
        if (allPersons().isEmpty()) {
            addNewPerson("Yusuf", "Hai", 27)
        }
    }
}