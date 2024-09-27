package app.purrfacts.network

import android.content.Context
import app.purrfacts.network.serviceapi.FactService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
internal object NetworkModule {

    private const val API_BASE_URL = "https://catfact.ninja/"
    private val jsonConfiguration = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideOkHttp(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .addInterceptor(ChuckerInterceptor(context))
            .build()

    @Provides
    fun provideFactService(okHttpClient: OkHttpClient): FactService =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_BASE_URL)
            .addConverterFactory(
                jsonConfiguration.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            .build()
            .create(FactService::class.java)
}