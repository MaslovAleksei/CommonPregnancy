package com.margarin.commonpregnancy.di

import android.content.Context
import com.margarin.commonpregnancy.AppScope
import com.margarin.commonpregnancy.Repository
import com.margarin.commonpregnancy.RepositoryImpl
import com.margarin.commonpregnancy.local.db.AppDatabase
import com.margarin.commonpregnancy.local.db.TaskDao
import com.margarin.commonpregnancy.local.db.TermDao
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