package com.example.devicefinder2.ui.notifications

import androidx.lifecycle.*
import com.example.devicefinder2.data.model.Strength
import com.example.devicefinder2.data.model.User
import com.example.devicefinder2.repository.AppRepository
import com.example.devicefinder2.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class StrengthDetailsViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _strength: LiveData<Result<Strength>> = _id.distinctUntilChanged().switchMap {
        liveData {
            appRepository.fetchStrength(it).onStart {
                emit(Result.loading())
            }.collect {
                if (it != null) {
                    emit(it)
                }
            }
        }
    }
    val strength = _strength

    fun getStrengthDetail(id: Int) {
        _id.value = id
    }
}