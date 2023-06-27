package com.aura.bootcounter.uc

import com.aura.bootcounter.data.EntriesRepository
import com.aura.bootcounter.pres.NotifTitlePresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class UiUseCase @Inject constructor(
    private val entriesRepository: EntriesRepository,
    private val pres: NotifTitlePresenter
){
    fun text(): Flow<String> {
        return entriesRepository.listen().mapLatest {entries->
            if (entries.isEmpty()){
                pres.noEntries()
            }else{
                pres.entriesList(entries)
            }
        }
    }
}