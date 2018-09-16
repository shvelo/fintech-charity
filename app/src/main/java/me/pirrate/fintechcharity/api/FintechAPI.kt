package me.pirrate.fintechcharity.api

import me.pirrate.fintechcharity.api.models.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FintechAPI {
    companion object {
        fun create(): FintechAPI {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.fintech.ge/")
                    .client(client)
                    .build()
            return retrofit.create(FintechAPI::class.java)
        }

        val service by lazy {
            create()
        }
    }

    @GET("/api/Clients/Login/{username}/{password}")
    fun login(@Path("username") username: String, @Path("password") password: String): Call<LoginResult>

    @GET("api/Clients/ClientInfo/{sessionId}")
    fun getClientInfo(@Path("sessionId") sessionId: String): Call<ClientInfo>

    @GET("/api/Products/Transactions/{sessionId}")
    fun getTransactions(@Path("sessionId") sessionId: String): Call<TransactionsResult>

    @GET("api/Products/Accounts/{sessionId}")
    fun getAccounts(@Path("sessionId") sessionId: String): Call<List<Account>>

    @POST("/api/Transfers/TransferOut")
    fun transferOut(@Body transferOut: TransferOut): Call<ResponseBody>
}