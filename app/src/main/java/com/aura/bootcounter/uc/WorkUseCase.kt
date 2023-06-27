package com.aura.bootcounter.uc

import com.aura.bootcounter.data.EntriesRepository
import com.aura.bootcounter.pres.NotifTitlePresenter
import javax.inject.Inject

class WorkUseCase @Inject constructor(
    private val notificationDisplayerUseCase: NotificationDisplayerUseCase,
    private val entriesRepository: EntriesRepository,
    private val pres:NotifTitlePresenter
){
    suspend fun handle(){
        val entries = entriesRepository.query()
        if (entries.isEmpty()){
            notificationDisplayerUseCase.display(pres.noEntries())
        }else if (entries.size==1){
            val f = entries.first()
            notificationDisplayerUseCase.display(pres.singleEntry(f.ts))
        }else {
            val items = entries.takeLast(2)
            val f = items[0]
            val l = items[1]
            notificationDisplayerUseCase.display(pres.multiEntry(l.ts - f.ts))
        }
    }

}