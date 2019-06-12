package wallet.client.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import wallet.api.model.Balance
import wallet.api.model.DepositRequest
import wallet.api.model.WithdrawRequest


interface WalletServer {

    @POST("deposit")
    fun deposit(@Body depositRequest: DepositRequest): Call<Void>

    @POST("withdraw")
    fun withdraw(@Body withdrawRequest: WithdrawRequest): Call<Void>

    @GET("balance")
    fun balance(@Query("userId") userId: Long): Call<Balance>

    companion object {
        val logger = KotlinLogging.logger {}
        fun create(apiUrl: String): WalletServer = Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(getHttpLoggingInterceptorLevel()))
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(
                JacksonConverterFactory.create(
                ObjectMapper().findAndRegisterModules().apply {
                    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                }
            ))
            .build()
            .create(WalletServer::class.java)

        private fun getHttpLoggingInterceptorLevel() = when (true) {
            logger.isTraceEnabled -> HttpLoggingInterceptor.Level.BODY
            logger.isDebugEnabled -> HttpLoggingInterceptor.Level.BASIC
            else -> HttpLoggingInterceptor.Level.NONE
        }

    }
}