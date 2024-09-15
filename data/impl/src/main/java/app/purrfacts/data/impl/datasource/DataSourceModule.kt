package app.purrfacts.data.impl.datasource

import app.purrfacts.data.impl.datasource.remote.DefaultRemoteFactDataSource
import app.purrfacts.data.impl.datasource.remote.RemoteFactDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindRemoteDataSource(impl: DefaultRemoteFactDataSource): RemoteFactDataSource
}