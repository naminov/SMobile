package com.naminov.smobile.di

import com.naminov.smobile.BuildConfig
import com.naminov.smobile.data.api.BasicAuthorizationInterceptor
import com.naminov.smobile.domain.repository.SettingsRepository
import com.naminov.smobile.domain.usecase.authorization.GetAuthorizationUseCase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
    }

    @Provides
    fun provideBasicAuthorizationInterceptor(
        getAuthorizationUseCase: GetAuthorizationUseCase
    ): BasicAuthorizationInterceptor {
        return BasicAuthorizationInterceptor(getAuthorizationUseCase)
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        basicAuthorizationInterceptor: BasicAuthorizationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(basicAuthorizationInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        settingsRepository: SettingsRepository
    ): Retrofit {
        val settingsConnection = settingsRepository.getSettingsConnection()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(settingsConnection.url)
            .build()
    }
}