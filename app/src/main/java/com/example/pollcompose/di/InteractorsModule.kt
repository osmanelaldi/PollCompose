package com.example.pollcompose.di

import android.app.Application
import android.content.Context
import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.interactors.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideGetPolls(
        pollService: PollService
    ) : GetPolls{
        return GetPolls(pollService = pollService)
    }

    @Singleton
    @Provides
    fun provideGetSignUp(
        pollService: PollService,
        context : Application
    ): SignUp{
        return SignUp(pollService = pollService, context = context)
    }

    @Singleton
    @Provides
    fun provideLogin(
        pollService: PollService,
        context : Application
    ): Login{
        return Login(pollService = pollService, context = context)
    }

    @Singleton
    @Provides
    fun provideAuthToken(
        pollService: PollService
    ): AuthToken{
        return AuthToken(pollService = pollService)
    }

    @Singleton
    @Provides
    fun provideCreateUser(
        pollService: PollService
    ): CreateUser{
        return CreateUser(pollService = pollService)
    }
}