package com.aura.bootcounter

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.room.Room
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.aura.bootcounter.data.EntriesDatabase
import com.aura.bootcounter.uc.NotificationDisplayerUseCase
import com.aura.bootcounter.work.NotificationWork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerInitializer : Initializer<WorkManager> {

    @Provides
    @Singleton
    override fun create(@ApplicationContext context: Context): WorkManager {
        val configuration = Configuration.Builder().build()
        WorkManager.initialize(context, configuration)
        Log.d("Hilt Init", "WorkManager initialized by Hilt this time")
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.
        return emptyList()
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal class AppModule {
    @Provides
    @Singleton
    fun notificationManager(@ApplicationContext ctx: Context):NotificationManagerCompat{
        return NotificationManagerCompat.from(ctx)
    }

    @Provides
    @Named("background")
    @Singleton
    fun backgroundScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }


    @Provides
    @Singleton
    fun database(
        @ApplicationContext
        ctx: Context,
    ): EntriesDatabase {
        return Room.databaseBuilder(
            ctx,
            EntriesDatabase::class.java,
            "entries.db"
        ).build()
    }
}

private const val WORK_ID = "notif"

@HiltAndroidApp
class CounterApp : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

    @Inject
    lateinit var workManager: WorkManager
    @Inject
    lateinit var notificationDisplayerUseCase: NotificationDisplayerUseCase
    override fun onCreate() {
        super.onCreate()
        workManager.enqueueUniquePeriodicWork(
            WORK_ID,
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<NotificationWork>(15, TimeUnit.MINUTES).build()
        )
        notificationDisplayerUseCase.init()
    }
}