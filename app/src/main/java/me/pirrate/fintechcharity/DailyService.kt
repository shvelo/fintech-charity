package me.pirrate.fintechcharity

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import me.pirrate.fintechcharity.api.FintechAPI
import me.pirrate.fintechcharity.api.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DailyService : IntentService("DailyService") {
    override fun onHandleIntent(intent: Intent?) {
        if (Globals.sessionId == null || Globals.paymentScheme == null || Globals.beneficiary == null || !Globals.active)
            return

        FintechAPI.service.getTransactions(Globals.sessionId!!).enqueue(object : Callback<TransactionsResult> {
            override fun onFailure(call: Call<TransactionsResult>?, t: Throwable?) {
                t?.printStackTrace()
            }

            override fun onResponse(call: Call<TransactionsResult>?, response: Response<TransactionsResult>?) {
                val transactions = response?.body()?.MyOperations

                Log.d("DailyService", "response ${response?.body()}")

                if (transactions != null)
                    handleTransactions(transactions)
            }
        })
    }

    fun handleTransactions(transactions: List<Transaction>) {
        var totalSum = 0f

        Log.d("DailyService", "type: ${Globals.paymentScheme?.type}, value: ${Globals.paymentScheme?.value}, limit: ${Globals.paymentScheme?.upperLimit}")

        for (transaction in transactions) {
            Log.d("DailyService", "amount: ${transaction.Amount}")
            if (Globals.paymentScheme?.type == PaymentScheme.TYPE_ROUND) {
                var rounding = transaction.Amount.rem(Globals.paymentScheme!!.value)
                totalSum += rounding
                Log.d("DailyService", "rounding: $rounding")

            } else if (Globals.paymentScheme?.type == PaymentScheme.TYPE_PERCENTAGE) {
                var percentage: Float = transaction.Amount % (Globals.paymentScheme!!.value / 100)
                if (Globals.paymentScheme!!.upperLimit != -1 && percentage > Globals.paymentScheme!!.upperLimit)
                    percentage = Globals.paymentScheme!!.upperLimit.toFloat()

                Log.d("DailyService", "percentage: $percentage")

                totalSum += percentage
            }
        }

        if (totalSum > 0)
            transfer(totalSum, Globals.beneficiary!!)
    }

    fun transfer(amount: Float, beneficiary: Beneficiary) {
        val transferOut = TransferOut(1, beneficiary.IBAN, beneficiary.Name, "User", amount, "GEL", "Charity")
        FintechAPI.service.transferOut(transferOut).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                notify(amount)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })


    }

    fun notify(amount: Float) {
        Log.d("DailyService", "transferred $amount")

        val notifManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(applicationContext, "default")
                .setContentTitle("თანხა გადარიცხულია")
                .setContentText("გადარიცხულია $amount ლარი")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.logo)
        notifManager.notify(0, mBuilder.build())
    }
}