package me.pirrate.fintechcharity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MainFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_main, container, false)
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
