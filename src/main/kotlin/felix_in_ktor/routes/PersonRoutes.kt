package felix_in_ktor.routes

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
            if (personStorage.isNotEmpty()) {
                call.respond(SuccessResponse<List<Person>>(data = personStorage))
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
            val person = personStorage.find { it.id == id } ?: return@get call.respond(
                message = FailResponse(message = "No person with id $id"),
                status = HttpStatusCode.NotFound
            )

            call.respond(message = SuccessResponse(data = person))
        }

        authenticate("auth-jwt") {
            post {
                val person = call.receive<Person>()

                val availablePerson = personStorage.find { it.id == person.id }
                if(availablePerson != null)  {
                    return@post call.respond(
                        message = FailResponse(message = "Person with id ${person.id} is already available"),
                        status = HttpStatusCode.NotAcceptable
                    )
                }

                personStorage.add(person)
                call.respond(message = SuccessResponse(data = person), status = HttpStatusCode.Created)
            }

            put ("{id?}") {
                val id = call.parameters["id"] ?: return@put call.respond(
                    message = FailResponse(message = "Missing person id"),
                    status = HttpStatusCode.BadRequest
                )

                val receiveData = call.receive<UpdatePerson>()
                val person = personStorage.find { it.id == id }
                    ?: return@put call.respond(
                        message = FailResponse(message = "No person with id $id"),
                        status = HttpStatusCode.NotFound
                    )

                val newData = Person(id = id, firstName = receiveData.firstName, lastName = receiveData.lastName, age = receiveData.age)
                personStorage[personStorage.indexOf(person)] = newData

                return@put call.respond(message = SuccessResponse(data = newData), status = HttpStatusCode.Accepted)
            }

            delete("{id?}") {
                val id = call.parameters["id"] ?: return@delete call.respond(
                        message = FailResponse(message = "Missing person id"),
                        status = HttpStatusCode.BadRequest
                )

                if (personStorage.removeIf { it.id == id }) {
                    call.respond(message = SuccessResponse(data = "$id deleted"), status = HttpStatusCode.Accepted)
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