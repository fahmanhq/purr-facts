package labs.fhq.nativeapp.impl.wiring

import app.purrfacts.data.api.repository.FactRepository
import app.purrfacts.data.impl.repository.DefaultFactRepository
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