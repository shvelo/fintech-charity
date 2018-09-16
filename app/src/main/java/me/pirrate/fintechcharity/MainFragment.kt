package me.pirrate.fintechcharity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.pirrate.fintechcharity.api.FintechAPI
import me.pirrate.fintechcharity.api.models.ClientInfo
import me.pirrate.fintechcharity.api.models.PaymentScheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private var clientName: TextView? = null
    private var beneficiaryName: TextView? = null
    private var beneficiaryImage: ImageView? = null
    private var selectedPaymentScheme: TextView? = null
    private var logoutButton: Button? = null
    private var bottomButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_main, container, false)

        clientName = layout.findViewById(R.id.clientName)
        logoutButton = layout.findViewById(R.id.logoutButton)
        bottomButton = layout.findViewById(R.id.bottomButton)

        val includeBeneficiary = layout.findViewById<ViewGroup>(R.id.includeBeneficiary)

        beneficiaryName = includeBeneficiary.findViewById(R.id.beneficiaryName)
        beneficiaryImage = includeBeneficiary.findViewById(R.id.beneficiaryImage)
        selectedPaymentScheme = layout.findViewById(R.id.selectedPaymentScheme)

        logoutButton?.setOnClickListener {
            listener?.onFragmentInteraction(INTERACTION_LOGOUT)
        }

        bottomButton?.setOnClickListener {
            listener?.onFragmentInteraction(INTERACTION_ACTIVATE)
        }

        val selectPaymentScheme = layout.findViewById<View>(R.id.selectPaymentScheme)
        val selectBeneficiary = layout.findViewById<View>(R.id.selectBeneficiary)

        selectPaymentScheme.setOnClickListener {
            listener?.onFragmentInteraction(INTERACTION_CHANGE_PAYMENT_SCHEME)
        }

        selectBeneficiary.setOnClickListener {
            listener?.onFragmentInteraction(INTERACTION_CHANGE_BENEFICIARY)
        }
        includeBeneficiary.setOnClickListener {
            listener?.onFragmentInteraction(INTERACTION_CHANGE_BENEFICIARY)
        }

        return layout
    }

    fun onButtonPressed(interaction: String) {
        listener?.onFragmentInteraction(interaction)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        FintechAPI.service.getClientInfo(Globals.sessionId!!).enqueue(object : Callback<ClientInfo> {
            override fun onFailure(call: Call<ClientInfo>?, t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<ClientInfo>?, response: Response<ClientInfo>?) {
                Globals.clientdetails = response?.body()?.Client

                if (Globals.clientdetails != null)
                    activity?.runOnUiThread { clientDetailsAvailable() }
            }
        })

        fillDetails()
    }

    private fun fillDetails() {
        Log.d("MainFragment", "fillDetails")

        beneficiaryName?.text = Globals.beneficiary?.Name

        if (beneficiaryImage != null)
            Glide.with(beneficiaryImage!!)
                    .load(Globals.beneficiary?.pictureUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(beneficiaryImage!!)

        if (Globals.paymentScheme?.type == PaymentScheme.TYPE_PERCENTAGE) {
            if (Globals.paymentScheme?.upperLimit != -1) {
                selectedPaymentScheme?.text = getString(
                        R.string.paymentScheme_percentage_withLimit,
                        Globals.paymentScheme!!.value,
                        Globals.paymentScheme!!.upperLimit
                )
            } else {
                selectedPaymentScheme?.text = getString(
                        R.string.paymentScheme_percentage,
                        Globals.paymentScheme!!.value
                )
            }
        } else if (Globals.paymentScheme?.type == PaymentScheme.TYPE_ROUND) {
            selectedPaymentScheme?.text = getString(
                    R.string.paymentScheme_round,
                    Globals.paymentScheme!!.value
            )
        }
    }

    fun clientDetailsAvailable() {
        clientName?.text = getString(R.string.hello, Globals.clientdetails?.FirstName)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(interaction: String)
    }

    companion object {
        const val INTERACTION_LOGOUT = "LOGOUT"
        const val INTERACTION_CHANGE_BENEFICIARY = "CHANGE_BENEFICIARY"
        const val INTERACTION_CHANGE_PAYMENT_SCHEME = "CHANGE_PAYMENT_SCHEME"
        const val INTERACTION_DEACTIVATE = "DEACTIVATE"
        const val INTERACTION_ACTIVATE = "ACTIVATE"
    }
}
