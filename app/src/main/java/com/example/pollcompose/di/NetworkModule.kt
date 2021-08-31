package com.example.pollcompose.di

import com.example.pollcompose.data.network.KtorClientFactory
import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.data.network.PollServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return KtorClientFactory().build()
    }

    @Singleton
    @Provides
    fun providePollService(
        httpClient: HttpClient,
    ): PollService {
        return PollServiceImpl(
            httpClient = httpClient,
        )
    }
}