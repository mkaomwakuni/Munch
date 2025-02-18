/*
 * MIT License
 * 
 * Copyright (c) 2025 Husty9 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.est.munchy.di


import android.app.Application
import android.content.Context
import com.est.munchy.MunchApp
import com.est.munchy.data.network.MunchApi
import com.est.munchy.utils.AppConstants
import com.est.munchy.utils.CryptoHelper
import com.est.munchy.utils.NetworkChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * [NetworkModule] provides dependencies related to network operations, such as Retrofit and OkHttp.
 *
 * This Hilt module is responsible for creating and providing instances of the OkHttpClient,
 * GsonConverterFactory, Retrofit, and MunchApi. These components are essential for making
 * network requests and handling API responses.
 *
 * The module is installed in [SingletonComponent], ensuring that these instances are available
 * throughout the application's lifecycle as singletons.
 *
 * @Module Indicates that this is a Hilt module.
 * @InstallIn(SingletonComponent::class) Specifies that the module should be installed in the
 *           application-scoped component.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a singleton instance of [OkHttpClient].
     *
     * This method configures the OkHttpClient with read and connect timeouts.
     *
     * @return A singleton instance of [OkHttpClient].
     *
     * @Provides Indicates that this method provides a dependency.
     * @Singleton Ensures only one instance of OkHttpClient is created.
     */
    @Provides
    @Singleton
    fun providesHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val cryptoHelper = (context.applicationContext as MunchApp).cryptoHelper

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                // Get encrypted API key
                val apiKey = cryptoHelper.getApiKey() ?: throw IllegalStateException("API key not configured")

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apiKey", apiKey)
                    .build()

                val requestBuilder = original.newBuilder().url(url)
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    /**
     * Provides a singleton instance of [GsonConverterFactory].
     *
     * This factory is used to convert JSON responses to Kotlin objects.
     *
     * @return A singleton instance of [GsonConverterFactory].
     *
     * @Provides Indicates that this method provides a dependency.
     * @Singleton Ensures only one instance of GsonConverterFactory is created.
     */
    @Singleton
    @Provides
    fun providesConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides a singleton instance of [Retrofit].
     *
     * This method configures the Retrofit instance with the base URL, OkHttpClient, and
     * GsonConverterFactory.
     *
     * @param okHttpClient The configured [OkHttpClient] instance. Injected by Hilt.
     * @param gsonConverterFactory The [GsonConverterFactory] instance. Injected by Hilt.
     * @return A singleton instance of [Retrofit].
     *
     * @Provides Indicates that this method provides a dependency.
     * @Singleton Ensures only one instance of Retrofit is created.
     */
    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    /**
     * Provides a singleton instance of [MunchApi].
     *
     * This method creates the [MunchApi] interface using the provided Retrofit instance.
     *
     * @param retrofit The configured [Retrofit] instance. Injected by Hilt.
     * @return A singleton instance of [MunchApi].
     *
     * @Provides Indicates that this method provides a dependency.
     * @Singleton Ensures only one instance of MunchApi is created.
     */
    @Singleton
    @Provides
    fun provideApiInstance(retrofit: Retrofit): MunchApi {
        return retrofit.create(MunchApi::class.java)
    }
}

/**
 * [NetworkCheckerModule] provides the dependency for the [NetworkChecker].
 *
 * @Module Indicates that this is a Hilt module.
 * @InstallIn(SingletonComponent::class) Specifies that the module should be installed in the
 *           application-scoped component.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkCheckerModule {

    /**
     * Provides an instance of [NetworkChecker].
     *
     * @param context The application context. Injected by Hilt.
     * @return An instance of [NetworkChecker].
     *
     * @Provides Indicates that this method provides a dependency.
     */
    @Provides
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
        return NetworkChecker(context)
    }
}

/**
 * [AppModule] provides application-level dependencies, such as the application [Context].
 *
 * @Module Indicates that this is a Hilt module.
 * @InstallIn(SingletonComponent::class) Specifies that the module should be installed in the
 *           application-scoped component.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Provides the application [Context].
     *
     * @param application The application instance. Injected by Hilt.
     * @return The application context.
     *
     * @Provides Indicates that this method provides a dependency.
     */
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
        fun provideCryptoHelper(
            @ApplicationContext context: Context
        ): CryptoHelper = CryptoHelper(context)
}