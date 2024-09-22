package app.purrfacts.database

import android.content.Context
import androidx.room.Room
import app.purrfacts.database.dao.FactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDb(@ApplicationContext appContext: Context): PurrFactsDb =
        Room.databaseBuilder(
            appContext,
            PurrFactsDb::class.java,
            PurrFactsDb.DB_NAME
        ).build()

    @Provides
    fun provideDao(db: PurrFactsDb): FactDao = db.factDao()

}