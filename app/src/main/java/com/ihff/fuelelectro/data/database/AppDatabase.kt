package com.ihff.fuelelectro.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ihff.fuelelectro.data.model.Car
import com.ihff.fuelelectro.data.model.Record
import com.ihff.fuelelectro.data.repository.CarRepository
import com.ihff.fuelelectro.data.repository.CarRepositoryImpl
import com.ihff.fuelelectro.data.repository.RecordRepository
import com.ihff.fuelelectro.data.repository.RecordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(entities = [Car::class, Record::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun recordDao(): RecordDao
}

// Модуль Hilt для внедрения зависимостей
@Module
@InstallIn(SingletonComponent::class)// Устанавливаем модуль в SingletonComponent для глобального скоупа
object AppDatabaseModule {
    // Создаем и предоставляем экземпляр базы данных через Hilt
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration() // Удаление данных при миграции
            .build()
    }

    // Предоставляем DAO для работы с Car
    @Provides
    fun provideCarDao(appDatabase: AppDatabase) : CarDao {
        return appDatabase.carDao()
    }

    // Предоставляем DAO для работы с Record
    @Provides
    fun provideRecordDao(appDatabase: AppDatabase): RecordDao {
        return appDatabase.recordDao()
    }

    // Предоставляем репозиторий для работы с Car
    @Provides
    fun provideCarRepository(carDao: CarDao): CarRepository {
        return CarRepositoryImpl(carDao)
    }

    // Предоставляем репозиторий для работы с Record
    @Provides
    fun provideRecordRepository(recordDao: RecordDao): RecordRepository {
        return RecordRepositoryImpl(recordDao)
    }

}