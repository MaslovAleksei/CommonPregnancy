package com.margarin.commonpregnancy.di

import android.content.Context
import com.margarin.commonpregnancy.data.RepositoryImpl
import com.margarin.commonpregnancy.data.local.db.AppDatabase
import com.margarin.commonpregnancy.data.local.db.PregnancyDao
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
        fun providePregnancyDao(context: Context): PregnancyDao {
            return AppDatabase.getInstance(context).pregnancyDao()
        }
    }
}