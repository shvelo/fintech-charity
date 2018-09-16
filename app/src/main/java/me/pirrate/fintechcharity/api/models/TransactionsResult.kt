package me.pirrate.fintechcharity.api.models

data class TransactionsResult(
        val OutcomeSum: Float,
        val IncomeSum: Float,
        val MyOperations: List<Transaction>
)