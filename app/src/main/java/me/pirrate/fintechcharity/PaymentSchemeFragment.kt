package me.pirrate.fintechcharity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import me.pirrate.fintechcharity.api.models.PaymentScheme

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PaymentSchemeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class PaymentSchemeFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var selectedPaymentScheme: PaymentScheme = PaymentScheme(null, -1, -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_payment_scheme, container, false)
        val bottomButton = layout.findViewById<Button>(R.id.buttomButton)
        val selectPercentage = layout.findViewById<RadioButton>(R.id.selectPercentage)
        val selectRound = layout.findViewById<RadioButton>(R.id.selectRound)
        val formRound = layout.findViewById<LinearLayout>(R.id.formRound)
        val formPercentage = layout.findViewById<LinearLayout>(R.id.formPercentage)

        selectPercentage.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                bottomButton.isEnabled = true
                selectedPaymentScheme.type = PaymentScheme.TYPE_PERCENTAGE
                formPercentage.visibility = View.VISIBLE
            } else {
                formPercentage.visibility = View.GONE
            }
        }

        selectRound.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                bottomButton.isEnabled = true
                selectedPaymentScheme.type = PaymentScheme.TYPE_ROUND
                formRound.visibility = View.VISIBLE
            } else {
                formRound.visibility = View.GONE
            }
        }

        bottomButton.setOnClickListener {
            onButtonPressed()
        }

        return layout
    }

    fun setPaymentScheme(paymentScheme: PaymentScheme) {

    }

    fun onButtonPressed() {
        listener?.onFragmentInteraction(selectedPaymentScheme)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(paymentScheme: PaymentScheme)
    }


    companion object {
        fun forPaymentScheme(paymentScheme: PaymentScheme?): PaymentSchemeFragment {
            val fragment = PaymentSchemeFragment()

            if (paymentScheme != null)
                fragment.setPaymentScheme(paymentScheme)

            return fragment
        }
    }
}
