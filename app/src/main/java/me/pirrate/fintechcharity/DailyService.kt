package me.pirrate.fintechcharity

import android.app.IntentService
import android.content.Intent
import me.pirrate.fintechcharity.api.FintechAPI
import me.pirrate.fintechcharity.api.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyService : IntentService("DailyService") {
    override fun onHandleIntent(intent: Intent?) {
        if(Globals.sessionId == null || Globals.paymentScheme == null || Globals.beneficiary == null || !Globals.active)
            return

        FintechAPI.service.getTransactions(Globals.sessionId!!).enqueue(object: Callback<TransactionsResult> {
            override fun onFailure(call: Call<TransactionsResult>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<TransactionsResult>?, response: Response<TransactionsResult>?) {
                val transactions = response?.body()?.MyOperations
                if(transactions != null)
                    handleTransactions(transactions)
            }
        })
    }

    fun handleTransactions(transactions: List<Transaction>) {
        var totalSum = 0f

        for(transaction in transactions) {
            if (Globals.paymentScheme?.type == PaymentScheme.TYPE_ROUND) {
                totalSum += transaction.Amount.rem(Globals.paymentScheme!!.value)
            } else if (Globals.paymentScheme?.type == PaymentScheme.TYPE_PERCENTAGE) {
                var percentage: Float = transaction.Amount % (Globals.paymentScheme!!.value / 100)
                if (Globals.paymentScheme!!.upperLimit != -1 && percentage > Globals.paymentScheme!!.upperLimit)
                    percentage = Globals.paymentScheme!!.upperLimit.toFloat()

                totalSum += percentage
            }
        }

        if (totalSum > 0)
            transfer(totalSum, Globals.beneficiary!!)
    }

    fun transfer(amount: Float, beneficiary: Beneficiary) {
        val transferOut = TransferOut(1, beneficiary.IBAN, beneficiary.Name, Globals.userDetails!!.Name, amount, "GEL", "Charity" )
        FintechAPI.service.transferOut(transferOut).execute()
    }
}