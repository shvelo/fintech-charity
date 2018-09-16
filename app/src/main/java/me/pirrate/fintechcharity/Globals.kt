package me.pirrate.fintechcharity

import me.pirrate.fintechcharity.api.models.Beneficiary
import me.pirrate.fintechcharity.api.models.PaymentScheme
import me.pirrate.fintechcharity.api.models.UserDetails

object Globals {
    var sessionId: String? = null
    var userDetails: UserDetails? = null
    var beneficiary: Beneficiary? = null
    var paymentScheme: PaymentScheme? = null
}