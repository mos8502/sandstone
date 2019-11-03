package hu.nemi.sandstone.app.di

import com.apollographql.apollo.ApolloClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import hu.nemi.sandstone.BuildConfig
import hu.nemi.sandstone.app.AuthInterceptor
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module
object NetworkModule {
    @get:Singleton
    @get:Provides
    @JvmStatic
    val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    @Singleton
    @Provides
    @JvmStatic
    @User
    fun userOkHttpClient(
        okHttpClient: OkHttpClient,
        authInterceptor: AuthInterceptor
    ): OkHttpClient =
        okHttpClient.newBuilder()
            .addInterceptor(authInterceptor)
            .build()

    @Singleton
    @Provides
    @JvmStatic
    fun retrofit(okHttpClientProvider: Provider<OkHttpClient>): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call =
                    okHttpClientProvider.get().newCall(request)
            })
            .build()

    @Singleton
    @Provides
    @JvmStatic
    @User
    fun userRetrofit(retrofit: Retrofit, @User okHttpClientProvider: Provider<OkHttpClient>): Retrofit =
        retrofit.newBuilder()
            .callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call =
                    okHttpClientProvider.get().newCall(request)
            })
            .build()

    @JvmStatic
    @Singleton
    @Provides
    fun apolloClient(@User okHttpClient: OkHttpClient): ApolloClient =
        ApolloClient.builder()
            .okHttpClient(okHttpClient)
            .serverUrl("${BuildConfig.BASE_URL}api")
            .build()
}