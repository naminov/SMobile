package com.naminov.smobile.di

import com.naminov.smobile.BuildConfig
import com.naminov.smobile.data.network.interceptor.AuthorizationInterceptor
import com.naminov.smobile.data.network.interceptor.HostInterceptor
import com.naminov.smobile.data.network.Constants
import com.naminov.smobile.domain.usecase.authorization.GetAuthorizationUseCase
import com.naminov.smobile.domain.usecase.connection.GetConnectionUseCase
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
    fun provideHostInterceptor(
        getConnectionUseCase: GetConnectionUseCase
    ): HostInterceptor {
        return HostInterceptor(
            Constants.BASE_URL,
            getConnectionUseCase
        )
    }

    @Provides
    fun provideAuthorizationInterceptor(
        getAuthorizationUseCase: GetAuthorizationUseCase
    ): AuthorizationInterceptor {
        return AuthorizationInterceptor(getAuthorizationUseCase)
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        hostInterceptor: HostInterceptor,
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(hostInterceptor)
            .addInterceptor(authorizationInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }
}