package com.hometest.currencylistdemo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.hometest.currencylistdemo.data.repository.CurrencyRepositoryImpl
import com.hometest.currencylistdemo.data.source.local.AppDatabase
import com.hometest.currencylistdemo.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME).allowMainThreadQueries().build()
    }

    @Provides
    fun provideUserRepository(@ApplicationContext context: Context, appDatabase: AppDatabase): CurrencyRepository {
        return CurrencyRepositoryImpl(context, appDatabase)
    }
}