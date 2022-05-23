package com.app.composetestingdemo.data.di

import android.content.Context
import androidx.room.Room
import com.app.composetestingdemo.data.local.UsersDatabase
import com.app.composetestingdemo.data.repository.UsersRepositoryImpl
import com.app.composetestingdemo.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUsersDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        UsersDatabase::class.java,
        UsersDatabase.NAME
    ).build()

    @Provides
    @Singleton
    fun provideUsersRepository(database: UsersDatabase): UsersRepository = UsersRepositoryImpl(database)
}