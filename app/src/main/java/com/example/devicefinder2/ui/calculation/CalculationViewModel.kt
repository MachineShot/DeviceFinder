package com.example.devicefinder2.ui.calculation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devicefinder2.data.model.*
import com.example.devicefinder2.repository.AppRepository
import com.example.devicefinder2.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class CalculationViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val _strengthList = MutableLiveData<Result<List<Strength>>>()
    private val _userList = MutableLiveData<Result<List<User>>>()
    private val _calculationList = MutableLiveData<Result<List<Calculation>>>()
    val strengthList = _strengthList
    val userList = _userList
    val calculationList = _calculationList

    var _strengths : List<Strength> = ArrayList()
    var _calculations : List<Calculation> = ArrayList()
    var calculations : ArrayList<Calculation> = ArrayList()
    var strengths : ArrayList<StrengthCalc> = ArrayList()

    init {
        fetchStrengths()
        fetchUsers()
        fetchCalculations()
    }

    fun findUser(user: UserCalc) : String {
        for (i in _strengths.indices) {
            val foundStrength = strengths.find { it.calculation == _strengths[i].calculation }
            if (foundStrength == null) {
                val strengthToAdd = StrengthCalc(_strengths[i].calculation, ArrayList())
                strengthToAdd.strengths.add(_strengths[i].strength)
                strengths.add(strengthToAdd)
            }
            else {
                foundStrength.strengths.add(_strengths[i].strength)
            }
        }

        var result = Location(user.mac, 999.0, 0)
        var crd = Calculation(1, -10, -10, -10.0)
        for (str in strengths) {
            val calc = distance(user.strengths, str.strengths)
            if (calc < result.distance) {
                result.distance = calc
                result.calculationId = str.calculation
            }

            crd = _calculations.find { it.id == result.calculationId }!!
        }
        Log.e("User mac: ", result.mac)
        Log.e("Coordinates: ", crd.x.toString() + ";" + crd.y.toString())
        Log.e("Min: ", result.distance.toString())

        return "Coordinates (" + crd.x.toString() + ";" + crd.y.toString() + "), distance: " + result.distance.toString()
    }

    private fun distance(vect : ArrayList<Int>, dist: ArrayList<Int>) : Double {
        return sqrt( (vect[0] - dist[0]).toDouble().pow(2) + (vect[1] - dist[1]).toDouble().pow(2) + (vect[2] - dist[2]).toDouble().pow(2))
    }

    private fun fetchStrengths() {
        viewModelScope.launch {
            appRepository.fetchStrengths().collect {
                _strengthList.value = it
                if (it != null) {
                    if (it.status == Result.Status.SUCCESS) {
                        _strengths = it.data!!
                    }
                }
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
                if (it != null) {
                    if (it.status == Result.Status.SUCCESS) {
                        _calculations = it.data!!
                    }
                }
            }
        }
    }
}