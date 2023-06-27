package com.aura.bootcounter.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EntriesRepository @Inject constructor(
    private val db:EntriesDatabase
) {

    suspend fun save(){
        withContext(Dispatchers.IO){
            db.dao().insert(BootEntry(System.currentTimeMillis()))
        }
    }

    fun listen(): Flow<List<BootEntry>> {
        return db.dao().listen()
    }
    suspend fun query(): List<BootEntry> {
        return db.dao().query()
    }
}