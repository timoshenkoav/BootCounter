package com.aura.bootcounter.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aura.bootcounter.uc.WorkUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val workUseCase: WorkUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        kotlin.runCatching {
            workUseCase.handle()
        }.getOrThrow()
        return Result.success()
    }

}