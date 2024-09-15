package app.purrfacts.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FactDbEntity.TABLE_NAME)
data class FactDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val fact: String,
    val length: Int
) {
    companion object {
        const val TABLE_NAME = "facts"
    }
}
