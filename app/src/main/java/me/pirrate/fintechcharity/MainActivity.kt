package me.pirrate.fintechcharity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.pirrate.fintechcharity.api.models.Beneficiary
import me.pirrate.fintechcharity.api.models.PaymentScheme

class MainActivity : AppCompatActivity(),
        MainFragment.OnFragmentInteractionListener,
        BeneficiaryFragment.OnFragmentInteractionListener,
        PaymentSchemeFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(interaction: String) {
        when (interaction) {
            MainFragment.INTERACTION_ACTIVATE -> activate()
            MainFragment.INTERACTION_DEACTIVATE -> deactivate()
            MainFragment.INTERACTION_LOGOUT -> logout()
            MainFragment.INTERACTION_CHANGE_BENEFICIARY -> changeBeneficiary()
            MainFragment.INTERACTION_CHANGE_PAYMENT_SCHEME -> changePaymentScheme()
        }
    }

    override fun onFragmentInteraction(beneficiary: Beneficiary) {
        Globals.beneficiary = beneficiary

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, mainFragment)
                .commit()
    }

    override fun onFragmentInteraction(paymentScheme: PaymentScheme) {
        Globals.paymentScheme = paymentScheme

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, mainFragment)
                .commit()
    }

    private val mainFragment = MainFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, mainFragment)
                .commit()
    }

    fun logout() {
        Globals.clear(this)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun activate() {
        Globals.active = true
        Globals.firstRun = false
        Globals.save(this)
    }

    fun deactivate() {
        Globals.active = false
        Globals.save(this)
    }

    private fun changePaymentScheme() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, PaymentSchemeFragment.forPaymentScheme(Globals.paymentScheme))
                .addToBackStack(null)
                .commit()
    }

    private fun changeBeneficiary() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, BeneficiaryFragment.forBeneficiary(Globals.beneficiary))
                .addToBackStack(null)
                .commit()
    }
}
