package com.applydigital.hackernews.di

import com.applydigital.hackernews.domain.service.NetworkConnectivity
import com.applydigital.hackernews.framework.service.NetworkConnectivityImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindNetworkConnectivity(
        networkConnectivityImpl: NetworkConnectivityImpl
    ): NetworkConnectivity
}
