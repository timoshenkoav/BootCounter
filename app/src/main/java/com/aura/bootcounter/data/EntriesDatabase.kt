package com.aura.bootcounter.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity
data class BootEntry(
    @PrimaryKey
    @ColumnInfo(name = "ts")
    val ts:Long
)

@Dao
interface BootEntryDao{
    @Insert
    suspend fun insert(entry: BootEntry)
    @Query("select * from bootentry order by ts asc")
    fun listen(): Flow<List<BootEntry>>
    @Query("select * from bootentry order by ts asc")
    suspend fun query(): List<BootEntry>
}

@Database(version = 1, entities = [BootEntry::class])
abstract class EntriesDatabase : RoomDatabase() {
    abstract fun dao(): BootEntryDao
}