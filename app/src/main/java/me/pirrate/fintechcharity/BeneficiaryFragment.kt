package me.pirrate.fintechcharity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import me.pirrate.fintechcharity.api.models.Beneficiary


class BeneficiaryFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var selectedBeneficiary: Beneficiary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_beneficiary, container, false)
        val bottomButton = layout.findViewById<Button>(R.id.buttomButton)
        val beneficiaryAdapter = BeneficiaryAdapter(PrototypeHelpers.getBeneficiaries())
        val beneficiaryList = layout.findViewById<RecyclerView>(R.id.beneficiaryList)

        beneficiaryList.adapter = beneficiaryAdapter
        beneficiaryList.layoutManager = LinearLayoutManager(context)

        beneficiaryAdapter.onItemSelectedListener = {
            selectedBeneficiary = it
            bottomButton.isEnabled = true
        }

        bottomButton.setOnClickListener {
            onButtonPressed()
        }

        return layout
    }

    fun setBeneficiary(beneficiary: Beneficiary) {

    }

    fun onButtonPressed() {
        if (selectedBeneficiary != null)
            listener?.onFragmentInteraction(selectedBeneficiary!!)
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
        fun onFragmentInteraction(beneficiary: Beneficiary)
    }

    companion object {
        fun forBeneficiary(beneficiary: Beneficiary?): BeneficiaryFragment {
            val fragment = BeneficiaryFragment()
            if (beneficiary != null)
                fragment.setBeneficiary(beneficiary)
            return fragment
        }
    }
}
