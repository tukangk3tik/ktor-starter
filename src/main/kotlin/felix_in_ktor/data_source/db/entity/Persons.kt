package felix_in_ktor.data_source.db.entity

import org.jetbrains.exposed.sql.*

object Persons: Table() {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 255)
    val lastName = varchar("last_name", 255)
    val age = integer("age")

    override val primaryKey = PrimaryKey(id)
}
