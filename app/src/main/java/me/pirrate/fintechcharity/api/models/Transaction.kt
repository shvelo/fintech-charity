package me.pirrate.fintechcharity.api.models

data class Transaction(
        val AcctKey: Long,
        val EntryId: Long,
        val Nomination: String,
        val EntryGroup: String,
        val MerchantId: String,
        val Date: Long,
        val Amount: Float,
        val Ccy: String,
        val DocNomination: String,
        val Beneficiary: String,
        val DstAcc: String,
        val SrcAcc: String,
        val MerchantName: String,
        val MerchantNameInt: String,
        val AmountBase: Float,
        val EntryGroupName: String,
        val EntryGroupNameId: Long,
        val ServiceId: String,
        val PostDate: Long
)