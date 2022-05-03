package com.example.kgql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class ConferenceQuery : Query {
    fun conference() = Conference(name = "GraphQL", location = "北京")
    fun people(nameStartWith: String?) = listOf<People>(
        Speaker("Daniel", listOf("GraphQL", "OpenAPI")),
        Attendee("Eason", TicketType.FULL)
    ).filter { it.name.startsWith(nameStartWith ?: "") }

    fun schedule() = ScheduleDetails(
        greeting = "Greeting",
//        talks = listOf("Spring", "Rust", "Golang")
    )
}

class ScheduleDetails(val greeting: String) {
    suspend fun talks(): List<String> {
        delay(2000)
        return listOf("Spring", "Rust", "Golang")
    }
}


data class Conference(
    @GraphQLDescription("**awesome** name")
    val name: String,
    @Deprecated("no needed anymore")
    val location: String?
)

interface People {
    val name: String
}

data class Speaker(override val name: String, val talks: List<String>) : People

data class Attendee(override val name: String, val ticketType: TicketType) : People

enum class TicketType {
    CONFERENCE,
    TALK,
    FULL,
}