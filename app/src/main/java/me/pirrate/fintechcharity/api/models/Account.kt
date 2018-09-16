package me.pirrate.fintechcharity.api.models

data class Account(
        val AcctKey: Int,
        val AcctName: String,
        val PrintAcctNo: String,
        val AvailableAmounts: List<AccountAmount>,
        val Ccy: String,
        val OrderNo: Int,
        val ProductCode: Int,
        val Product: String,
        val MainAcctKey: Int,
        val ProductGroup: String,
        val ProductId: Int,
        val SubAccounts: List<Account>?
)