package com.aura.bootcounter.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aura.bootcounter.uc.WorkUseCase
import javax.inject.Inject

@HiltWorker
class NotificationWork @Inject constructor(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    @Inject
    lateinit var workUseCase: WorkUseCase
    override suspend fun doWork(): Result {
        kotlin.runCatching {
            workUseCase.handle()
        }
        return Result.success()
    }

}