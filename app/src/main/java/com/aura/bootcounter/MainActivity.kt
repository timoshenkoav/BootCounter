package com.aura.bootcounter

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.aura.bootcounter.databinding.ActivityMainBinding
import com.aura.bootcounter.uc.SaveBootCompletedUseCase
import com.aura.bootcounter.uc.WorkUseCase
import com.aura.bootcounter.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.activity.viewModels
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var saveBootCompletedUseCase: SaveBootCompletedUseCase
    @Inject
    lateinit var workUseCase: WorkUseCase
    private val vm:MainViewModel by viewModels()
    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ ->
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
        binding.send.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                saveBootCompletedUseCase.save()
            }
        }
        binding.check.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                workUseCase.handle()
            }
        }
        vm.ui.observe(this){
            binding.content.text = it
        }

    }
}