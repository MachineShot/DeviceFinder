package com.example.devicefinder2.ui.home

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
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _strengthList = MutableLiveData<Result<List<Strength>>>()
    private val _userList = MutableLiveData<Result<List<User>>>()
    private val _calculationList = MutableLiveData<Result<List<Calculation>>>()
    val strengthList = _strengthList
    val userList = _userList
    val calculationList = _calculationList

    init {
        fetchStrengths()
        fetchUsers()
        fetchCalculations()
    }

    private fun fetchStrengths() {
        viewModelScope.launch {
            appRepository.fetchStrengths().collect {
                _strengthList.value = it
            }
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            appRepository.fetchUsers().collect {
                _userList.value = it
            }
        }
    }

    private fun fetchCalculations() {
        viewModelScope.launch {
            appRepository.fetchCalculations().collect {
                _calculationList.value = it
            }
        }
    }
}