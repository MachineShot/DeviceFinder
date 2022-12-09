package com.example.devicefinder2.ui.dashboard

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
class DashboardViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _userList = MutableLiveData<Result<List<User>>>()
    val userList = _userList

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            appRepository.fetchUsers().collect {
                _userList.value = it
            }
        }
    }
}