package com.margarin.commonpregnancy.di

import com.margarin.commonpregnancy.data.RepositoryImpl
import com.margarin.commonpregnancy.domain.Repository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

}