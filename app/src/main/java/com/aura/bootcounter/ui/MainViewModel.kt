package com.aura.bootcounter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.bootcounter.uc.UiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val uiUseCase: UiUseCase
):ViewModel() {
    private val _ui = MutableLiveData<String>()
    val ui:LiveData<String> = _ui

    init {
        viewModelScope.launch {
            uiUseCase.text().collect {
                _ui.value = it
            }
        }
    }
}