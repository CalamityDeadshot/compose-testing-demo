package com.app.composetestingdemo.di

import android.content.Context
import androidx.room.Room
import com.app.composetestingdemo.data.di.IoDispatcher
import com.app.composetestingdemo.data.local.UsersDatabase
import com.app.composetestingdemo.data.repository.UsersRepositoryImpl
import com.app.composetestingdemo.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestDataModule {

    @Provides
    @Singleton
    fun provideUsersDatabase(
        @ApplicationContext context: Context,
        @IoDispatcher testDispatcher: CoroutineDispatcher,
    ) = Room.inMemoryDatabaseBuilder(
        context,
        UsersDatabase::class.java
    )
        .setTransactionExecutor(testDispatcher.asExecutor())
        .setQueryExecutor(testDispatcher.asExecutor())
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideUsersRepository(database: UsersDatabase): UsersRepository = UsersRepositoryImpl(database)
}