package felix_in_ktor.data_source.db

import felix_in_ktor.data_source.db.entity.Persons
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcUrl = "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbcUrl, driverClassName)

        transaction(database) {
            SchemaUtils.create(Persons)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}