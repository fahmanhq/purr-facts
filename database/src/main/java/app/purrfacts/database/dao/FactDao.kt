package app.purrfacts.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.purrfacts.database.model.FactDbEntity

@Dao
interface FactDao {

    @Query("SELECT * FROM facts")
    fun getAllFacts(): List<FactDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFact(fact: FactDbEntity)

    @Query("DELETE FROM facts")
    fun deleteAllFacts()

    @Query("SELECT * FROM facts WHERE id = :factId")
    fun getFactById(factId: Int): FactDbEntity?

    @Query("SELECT * FROM facts ORDER BY id DESC LIMIT 1")
    fun getLastSavedFact(): FactDbEntity?

}