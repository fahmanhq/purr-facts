package app.purrfacts.database

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.purrfacts.database.dao.FactDao
import app.purrfacts.database.model.FactDbEntity

@Database(entities = [FactDbEntity::class], version = 1)
abstract class PurrFactsDb : RoomDatabase() {

    abstract fun factDao(): FactDao

    companion object {
        const val DB_NAME = "purr-facts-db"

        @VisibleForTesting
        fun createInMemoryDb(appContext: Context) =
            Room.inMemoryDatabaseBuilder(
                context = appContext,
                PurrFactsDb::class.java
            ).build()
    }
}