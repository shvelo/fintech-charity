package me.pirrate.fintechcharity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.pirrate.fintechcharity.api.models.Beneficiary
import me.pirrate.fintechcharity.api.models.PaymentScheme

class IntroActivity : AppCompatActivity(), BeneficiaryFragment.OnFragmentInteractionListener, PaymentSchemeFragment.OnFragmentInteractionListener {
    private val beneficiaryFragment = BeneficiaryFragment()
    private val paymentSchemeFragment = PaymentSchemeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, beneficiaryFragment)
                .commit()
    }

    override fun onFragmentInteraction(beneficiary: Beneficiary) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.contentFrame, paymentSchemeFragment)
                .commit()
    }

    override fun onFragmentInteraction(paymentScheme: PaymentScheme) {

    }
}
