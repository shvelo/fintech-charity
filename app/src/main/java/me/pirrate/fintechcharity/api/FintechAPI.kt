package me.pirrate.fintechcharity.api

import me.pirrate.fintechcharity.api.models.LoginResult
import me.pirrate.fintechcharity.api.models.TransactionsResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FintechAPI {
    companion object {
        fun create(): FintechAPI {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.fintech.ge/")
                    .build()
            return retrofit.create(FintechAPI::class.java)
        }

        val service by lazy {
            create()
        }
    }

    @GET("/api/Products/Transactions/{sessionId}")
    fun getTransactions(@Path("sessionId") sessionId: String): Call<TransactionsResult>

    @GET("/api/Clients/Login/{username}/{password}")
    fun login(@Path("username") username: String, @Path("password") password: String): Call<LoginResult>
}