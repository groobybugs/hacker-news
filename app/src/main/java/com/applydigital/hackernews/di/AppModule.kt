package com.applydigital.hackernews.di

import com.applydigital.hackernews.utils.constants.Constants.IO_DISPATCHER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named(IO_DISPATCHER)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
