package com.example.devicefinder2.ui.dashboard

import androidx.lifecycle.*
import com.example.devicefinder2.data.local.AppDao
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.repository.AppRepository
import com.example.devicefinder2.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _user: LiveData<Result<User>> = _id.distinctUntilChanged().switchMap {
        liveData {
            appRepository.fetchUser(it).onStart {
                emit(Result.loading())
            }.collect {
                if (it != null) {
                    emit(it)
                }
            }
        }
    }
    val user = _user

    fun getUserDetail(id: Int) {
        _id.value = id
    }
}