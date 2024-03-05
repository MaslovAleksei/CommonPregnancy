package com.margarin.commonpregnancy.di

import android.content.Context
import com.margarin.commonpregnancy.data.RepositoryImpl
import com.margarin.commonpregnancy.data.local.db.AppDatabase
import com.margarin.commonpregnancy.data.local.db.TaskDao
import com.margarin.commonpregnancy.data.local.db.TermDao
import com.margarin.commonpregnancy.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {

        @AppScope
        @Provides
        fun provideAppDatabase(context: Context): AppDatabase {
            return AppDatabase.getInstance(context)
        }

        @Provides
        @AppScope
        fun provideTermDao(context: Context): TermDao {
            return AppDatabase.getInstance(context).termDao()
        }

        @Provides
        @AppScope
        fun provideTaskDao(context: Context): TaskDao {
            return AppDatabase.getInstance(context).taskDao()
        }
    }
}