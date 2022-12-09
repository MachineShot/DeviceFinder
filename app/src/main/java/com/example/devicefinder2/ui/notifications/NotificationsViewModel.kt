package com.example.devicefinder2.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devicefinder2.data.model.Calculation
import com.example.devicefinder2.data.model.Strength
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.repository.AppRepository
import com.example.devicefinder2.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _strengthList = MutableLiveData<Result<List<Strength>>>()
    val strengthList = _strengthList

    init {
        fetchStrengths()
    }

    private fun fetchStrengths() {
        viewModelScope.launch {
            appRepository.fetchStrengths().collect {
                _strengthList.value = it
            }
        }
    }
}