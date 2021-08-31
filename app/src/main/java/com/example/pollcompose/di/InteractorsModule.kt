package com.example.pollcompose.di

import com.example.pollcompose.data.network.PollService
import com.example.pollcompose.interactors.AuthToken
import com.example.pollcompose.interactors.GetPolls
import com.example.pollcompose.interactors.Login
import com.example.pollcompose.interactors.SignUp
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
        pollService: PollService
    ): SignUp{
        return SignUp(pollService = pollService)
    }

    @Singleton
    @Provides
    fun provideLogin(
        pollService: PollService
    ): Login{
        return Login(pollService = pollService)
    }

    @Singleton
    @Provides
    fun provideAuthToken(
        pollService: PollService
    ): AuthToken{
        return AuthToken(pollService = pollService)
    }
}