package com.naminov.smobile.di

import com.naminov.smobile.domain.usecase.order.HasOrderChangesUseCase
import com.naminov.smobile.domain.usecase.order.HasOrderChangesUseCaseImpl
import dagger.Module
import dagger.Provides

@Module
class HasOrderChangesUseCaseModule {
    @Provides
    fun provideHasOrderChangesUseCase(): HasOrderChangesUseCase {
        return HasOrderChangesUseCaseImpl()
    }
}