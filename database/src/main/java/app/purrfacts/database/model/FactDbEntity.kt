package app.purrfacts.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FactDbEntity.TABLE_NAME)
data class FactDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fact: String,
) {
    companion object {
        const val TABLE_NAME = "facts"
    }
}
