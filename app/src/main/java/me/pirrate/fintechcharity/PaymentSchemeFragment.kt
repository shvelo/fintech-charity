package me.pirrate.fintechcharity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

    private var bottomButton: Button? = null
    private var selectPercentage: RadioButton? = null
    private var selectRound: RadioButton? = null
    private var selectRound1: RadioButton? = null
    private var selectRound5: RadioButton? = null
    private var formRound: ViewGroup? = null
    private var formPercentage: ViewGroup? = null
    private var inputPercentage: EditText? = null
    private var inputUpperLimit: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_payment_scheme, container, false)
        bottomButton = layout.findViewById(R.id.buttomButton)
        selectPercentage = layout.findViewById(R.id.selectPercentage)
        selectRound = layout.findViewById(R.id.selectRound)
        selectRound1 = layout.findViewById(R.id.selectRound1)
        selectRound5 = layout.findViewById(R.id.selectRound5)
        formRound = layout.findViewById(R.id.formRound)
        formPercentage = layout.findViewById(R.id.formPercentage)
        inputPercentage = layout.findViewById(R.id.inputPercentage)
        inputUpperLimit = layout.findViewById(R.id.inputUpperLimit)

        selectPercentage?.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                percentageSelected()
            }
        }

        selectRound?.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                roundSelected()
            }
        }

        selectRound1?.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                selectedPaymentScheme.value = 1
            }
        }
        selectRound5?.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                selectedPaymentScheme.value = 5
            }
        }

        inputPercentage?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                selectedPaymentScheme.value = s.toString().toInt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {            }
        })
        inputUpperLimit?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                selectedPaymentScheme.upperLimit = s.toString().toInt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {            }
        })

        bottomButton?.setOnClickListener {
            onButtonPressed()
        }

        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setPaymentScheme(selectedPaymentScheme)
    }

    fun roundSelected() {
        formRound?.visibility = View.VISIBLE
        formPercentage?.visibility = View.GONE
        selectedPaymentScheme.type = PaymentScheme.TYPE_ROUND
        bottomButton?.isEnabled = true

        if (selectedPaymentScheme.value == 1) {
            selectRound1?.isChecked = true
        } else if (selectedPaymentScheme.value == 5) {
            selectRound5?.isChecked = true
        } else {
            selectedPaymentScheme.value = 1
            selectRound1?.isChecked = true
        }
    }

    fun percentageSelected() {
        selectedPaymentScheme.type = PaymentScheme.TYPE_PERCENTAGE
        formPercentage?.visibility = View.VISIBLE
        formRound?.visibility = View.GONE
        bottomButton?.isEnabled = true

        if (selectedPaymentScheme.value == -1) {
            selectedPaymentScheme.value = 10
        } else {
            inputPercentage?.setText(selectedPaymentScheme.value.toString())
        }

        if (selectedPaymentScheme.upperLimit != -1) {
            inputUpperLimit?.setText(selectedPaymentScheme.upperLimit.toString())
        }
    }

    fun setPaymentScheme(paymentScheme: PaymentScheme) {
        selectedPaymentScheme = paymentScheme

        if (paymentScheme.type == PaymentScheme.TYPE_PERCENTAGE) {
            selectPercentage?.isChecked = true
            percentageSelected()
        } else if (paymentScheme.type == PaymentScheme.TYPE_ROUND) {
            selectRound?.isChecked = true
            roundSelected()
        }
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
