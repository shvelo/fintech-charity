package me.pirrate.fintechcharity.api.models

data class LoginResult(
        val SessionId: String,
        val SessionDetails: SessionDetails,
        val UserDetails: UserDetails
)