package me.pirrate.fintechcharity.api.models

data class PaymentScheme(var type: String?, var value: Int, var upperLimit: Int) {
    companion object {
        const val TYPE_PERCENTAGE = "PERCENTAGE"
        const val TYPE_ROUND = "ROUND"
    }
}