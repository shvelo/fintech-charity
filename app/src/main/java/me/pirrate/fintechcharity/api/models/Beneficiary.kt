package me.pirrate.fintechcharity.api.models

data class Beneficiary(
        var Name: String,
        var Description: String?,
        var IBAN: String,
        var pictureUrl: String? = null,
        var website: String? = null
)