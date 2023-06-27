package com.aura.bootcounter.uc

import android.util.Log
import com.aura.bootcounter.data.EntriesRepository
import javax.inject.Inject

class SaveBootCompletedUseCase @Inject constructor(
    private val entriesRepository: EntriesRepository
) {
    suspend fun save(){
        entriesRepository.save()
    }
}