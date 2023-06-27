package com.aura.bootcounter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.aura.bootcounter.uc.SaveBootCompletedUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject
    lateinit var saveBootCompletedUseCase: SaveBootCompletedUseCase

    @Inject
    @Named("background")
    lateinit var scope: CoroutineScope
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            scope.launch {
                saveBootCompletedUseCase.save()
            }
        }
    }
}