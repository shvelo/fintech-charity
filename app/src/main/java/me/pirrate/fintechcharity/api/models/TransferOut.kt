package me.pirrate.fintechcharity.api.models

data class TransferOut(
        var SrcAcctKey: Int,
        var BenefAcctNo: String,
        var BenefName: String,
        var PayerName: String,
        var Amount: Float,
        var Ccy: String,
        var Nomination: String
)