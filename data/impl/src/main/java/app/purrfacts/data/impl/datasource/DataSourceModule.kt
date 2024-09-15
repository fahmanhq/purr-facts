package app.purrfacts.data.impl.datasource

import app.purrfacts.data.impl.datasource.local.DefaultLocalFactDataSource
import app.purrfacts.data.impl.datasource.local.LocalFactDataSource
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
    fun bindRemoteFactDataSource(impl: DefaultRemoteFactDataSource): RemoteFactDataSource

    @Binds
    fun bindLocalFactDataSource(impl: DefaultLocalFactDataSource): LocalFactDataSource
}