package me.pirrate.fintechcharity.api.models

data class ClientDetails(
        val CategoryType: String,
        val FirstName: String,
        val LastName: String,
        val FirstNameInt: String,
        val LastNameInt: String,
        val Sex: String,
        val BirthDate: Long,
        val TranStatus: String,
        val Resident: String,
        val Pin: String,
        val ClientCategory: String
)