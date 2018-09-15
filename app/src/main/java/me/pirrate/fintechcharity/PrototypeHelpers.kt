package me.pirrate.fintechcharity

import me.pirrate.fintechcharity.api.models.Beneficiary

object PrototypeHelpers {
    fun getBeneficiaries(): List<Beneficiary> {
        val beneficiaryList = ArrayList<Beneficiary>()
        beneficiaryList.add(Beneficiary("Charity 1", "Charity 1 description", "GE11DUMMY00000000GEL", ""))
        beneficiaryList.add(Beneficiary("Charity 2", "Charity 2 description", "GE11DUMMY00000000GEL", ""))
        beneficiaryList.add(Beneficiary("Charity 3", "Charity 3 description", "GE11DUMMY00000000GEL", ""))
        return beneficiaryList
    }
}