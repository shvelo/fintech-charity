package me.pirrate.fintechcharity

import me.pirrate.fintechcharity.api.models.Beneficiary

object PrototypeHelpers {
    fun getBeneficiaries(): List<Beneficiary> {
        val beneficiaryList = ArrayList<Beneficiary>()
        beneficiaryList.add(Beneficiary("Charity 1", "Charity 1 description", "GE11DUMMY00000000GEL", "https://www.entyce-creative.com/wp-content/uploads/2015/11/entyce-share-charity-logo11.jpg"))
        beneficiaryList.add(Beneficiary("Charity 2", "Charity 2 description", "GE11DUMMY00000000GEL", "https://s-media-cache-ak0.pinimg.com/originals/61/cd/a3/61cda34e4073a1ac7d061e21079b3345.jpg"))
        beneficiaryList.add(Beneficiary("Charity 3", "Charity 3 description", "GE11DUMMY00000000GEL", "https://i.pinimg.com/564x/b8/a1/8d/b8a18dbbe6c31c278f8402a82c0b4d08.jpg"))
        return beneficiaryList
    }
}