package felix_in_ktor.routes

import felix_in_ktor.data_source.db.dao.personDao
import felix_in_ktor.models.person.*
import felix_in_ktor.models.base_response.FailResponse
import felix_in_ktor.models.base_response.SuccessResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.personRoutes() {
    route("/person") {

        get {
            val persons = personDao.allPersons()

            if (persons.isNotEmpty()) {
                call.respond(SuccessResponse(data = persons))
            } else {
                call.respond(FailResponse(message = "No person found"))
            }
        }

        get ("{id?}") {
            //check isset id
            val id = call.parameters["id"] ?: return@get call.respond(
                message = FailResponse(message = "Missing person id"),
                status = HttpStatusCode.BadRequest
            )

            //find person
            val person = personDao.getPerson(id.toInt()) ?: return@get call.respond(
                message = FailResponse(message = "No person with id $id"),
                status = HttpStatusCode.NotFound
            )

            call.respond(message = SuccessResponse(data = person))
        }

        authenticate("auth-jwt") {
            post {
                val person = call.receive<Person>()
                val newPerson = personDao.addNewPerson(firstName = person.firstName, lastName = person.lastName, age = person.age)

                call.respond(message = SuccessResponse(data = newPerson), status = HttpStatusCode.Created)
            }

            put ("{id?}") {
                val id = call.parameters["id"] ?: return@put call.respond(
                    message = FailResponse(message = "Missing person id"),
                    status = HttpStatusCode.BadRequest
                )

                val receiveData = call.receive<UpdatePerson>()
                personDao.getPerson(id.toInt()) ?: return@put call.respond(
                    message = FailResponse(message = "No person with id $id"),
                    status = HttpStatusCode.NotFound
                )

                val result = personDao.editPerson(id = id.toInt(), firstName = receiveData.firstName, lastName = receiveData.lastName, age = receiveData.age)
                if (!result) return@put call.respond(message = FailResponse(message = "Something wrong, please contact admin"), status = HttpStatusCode.NotModified)

                call.respond(message = SuccessResponse(data = "Data with id $id has updated"), status = HttpStatusCode.Accepted)
            }

            delete("{id?}") {
                val id = call.parameters["id"] ?: return@delete call.respond(
                    message = FailResponse(message = "Missing person id"),
                    status = HttpStatusCode.BadRequest
                )

                if (personDao.deletePerson(id.toInt())) {
                    call.respond(message = SuccessResponse(data = "Data with id $id has deleted"), status = HttpStatusCode.Accepted)
                } else {
                    call.respond(
                        message = FailResponse(message = "Not found"),
                        status = HttpStatusCode.NotFound
                    )
                }
            }
        }
    }
}