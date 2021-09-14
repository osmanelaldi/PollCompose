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

    @Singleton
    @Provides
    fun provideGetUser(
        pollService: PollService
    ): GetUser{
        return GetUser(pollService = pollService)
    }

    @Singleton
    @Provides
    fun providePollVote(
        pollService: PollService
    ): PollVote{
        return PollVote(pollService = pollService)
    }

    @Singleton
    @Provides
    fun provideCreatePoll(
        pollService: PollService
    ): CreatePoll{
        return CreatePoll(pollService = pollService)
    }

    @Singleton
    @Provides
    fun provideCreateOptions(
        pollService: PollService
    ): CreateOptions{
        return CreateOptions(pollService = pollService)
    }

    @Singleton
    @Provides
    fun provideDeleteVotes(
        pollService: PollService
    ): DeleteVotes{
        return DeleteVotes(pollService = pollService)
    }

    @Singleton
    @Provides
    fun provideDeleteOptions(
        pollService: PollService
    ): DeleteOptions{
        return DeleteOptions(pollService = pollService)
    }

    @Singleton
    @Provides
    fun provideDeletePoll(
        pollService: PollService
    ): DeletePoll{
        return DeletePoll(pollService = pollService)
    }
}