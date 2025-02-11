package com.ihff.fuelelectro.di

import android.content.Context
import com.ihff.fuelelectro.data.datastore.UserPreferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Добавляем метод для предоставления Context
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(context: Context): UserPreferencesDataStore {
        return UserPreferencesDataStore(context)
    }
}
