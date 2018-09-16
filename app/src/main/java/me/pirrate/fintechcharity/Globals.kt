package me.pirrate.fintechcharity

import android.content.Context
import android.content.SharedPreferences
import me.pirrate.fintechcharity.api.models.Beneficiary
import me.pirrate.fintechcharity.api.models.PaymentScheme
import me.pirrate.fintechcharity.api.models.UserDetails

object Globals {
    private var sharedPrefs: SharedPreferences? = null

    var sessionId: String? = null
    var userDetails: UserDetails? = null
    var beneficiary: Beneficiary? = null
    var paymentScheme: PaymentScheme? = null
    var firstRun = true
    var active = false

    fun save(context: Context?) {
        if (context == null && sharedPrefs == null)
            return

        if (sharedPrefs == null && context != null)
            sharedPrefs = context.getSharedPreferences("globals", Context.MODE_PRIVATE)

        val editor = sharedPrefs!!.edit()

        editor.putBoolean("firstRun", firstRun)
        editor.putBoolean("active", active)
        editor.putString("sessionId", sessionId)

        if (beneficiary != null) {
            editor.putString("beneficiary", beneficiary?.IBAN)
        }

        if (paymentScheme != null) {
            editor.putString("paymentScheme_type", paymentScheme!!.type)

            editor.putInt("paymentScheme_value", paymentScheme!!.value)

            editor.putInt("paymentScheme_upperLimit", paymentScheme!!.upperLimit)
        }

        editor.apply()
    }

    fun restore(context: Context?) {
        if (context == null && sharedPrefs == null)
            return

        if (sharedPrefs == null)
            sharedPrefs = context?.getSharedPreferences("globals", Context.MODE_PRIVATE)

        firstRun = sharedPrefs!!.getBoolean("firstRun", true)
        active = sharedPrefs!!.getBoolean("active", false)
        sessionId = sharedPrefs!!.getString("sessionId", null)

        if (sharedPrefs!!.contains("paymentScheme_type")) {
            paymentScheme = PaymentScheme(
                    sharedPrefs!!.getString("paymentScheme_type", null),
                    sharedPrefs!!.getInt("paymentScheme_upperLimit", -1),
                    sharedPrefs!!.getInt("paymentScheme_value", -1))
        }

        if (sharedPrefs!!.contains("beneficiary")) {
            val beneficiaryIBAN = sharedPrefs!!.getString("beneficiary", null)
            beneficiary = PrototypeHelpers.getBeneficiaries().find { it.IBAN == beneficiaryIBAN }
        }
    }

    fun clear(context: Context?) {
        if (context == null && sharedPrefs == null)
            return

        if (sharedPrefs == null)
            sharedPrefs = context?.getSharedPreferences("globals", Context.MODE_PRIVATE)

        sessionId = null
        userDetails = null
        beneficiary = null
        paymentScheme = null
        firstRun = true
        active = false

        sharedPrefs!!.edit()
                .clear()
                .apply()
    }
}