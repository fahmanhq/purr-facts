package app.purrfacts.data.impl.repository

import app.purrfacts.data.api.FactRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideRepository(impl: DefaultFactRepository): FactRepository
}