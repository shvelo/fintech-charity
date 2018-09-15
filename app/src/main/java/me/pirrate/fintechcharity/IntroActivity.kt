package me.pirrate.fintechcharity

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class IntroActivity : AppCompatActivity(), BeneficiaryFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }

    val beneficiaryFragment = BeneficiaryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        supportFragmentManager.beginTransaction()
                .add(R.id.contentFrame, beneficiaryFragment)
                .commit()
    }
}
