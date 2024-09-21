package labs.fhq.nativeapp.impl.wiring

import app.purrfacts.data.api.datasource.LocalFactDataSource
import app.purrfacts.data.api.datasource.RemoteFactDataSource
import app.purrfacts.data.impl.datasource.local.DefaultLocalFactDataSource
import app.purrfacts.data.impl.datasource.remote.DefaultRemoteFactDataSource
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