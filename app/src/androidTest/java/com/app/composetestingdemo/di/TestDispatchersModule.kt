package com.app.composetestingdemo.di

import com.app.composetestingdemo.data.di.DefaultDispatcher
import com.app.composetestingdemo.data.di.IoDispatcher
import com.app.composetestingdemo.data.di.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object TestDispatchersModule {
    @Provides
    @DefaultDispatcher
    fun providesDefaultDispatcher(): CoroutineDispatcher = TestCoroutineDispatcher()

    @Provides
    @IoDispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = TestCoroutineDispatcher()

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = TestCoroutineDispatcher()
}