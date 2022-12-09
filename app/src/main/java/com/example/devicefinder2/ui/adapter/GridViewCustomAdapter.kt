package com.example.devicefinder2.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.example.devicefinder2.R
import com.example.devicefinder2.data.model.*
import kotlin.math.pow
import kotlin.math.sqrt


class GridViewCustomAdapter(var layoutInflater: LayoutInflater) :
    BaseAdapter() {

    private var calculations: List<Calculation> = ArrayList()
    private var _strengths: ArrayList<Strength> = ArrayList()
    private var _users: ArrayList<User> = ArrayList()

    private var strengths: ArrayList<StrengthCalc> = ArrayList()
    private var users: ArrayList<UserCalc> = ArrayList()
    private var coords: ArrayList<Coordinate> = ArrayList()

    override fun getCount(): Int {
        return coords.size
    }

    override fun getItem(position: Int): Any {
        return coords[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView  = layoutInflater.inflate(R.layout.item, parent, false)
        val tv: Button = convertView.findViewById(R.id.button) as Button

        if (coords[position].mark) {
            tv.text = "1"
            tv.setTextColor(Color.GREEN)
        } else {
            tv.text = "0"
            tv.setTextColor(Color.BLACK)
        }

        if (coords[position].onUser) {
            tv.text = "U"
            tv.setTextColor(Color.WHITE)
        }

        return convertView
    }

    fun submitCalculationList(calculationList: List<Calculation>){
        calculations = calculationList
        for (i in 35 downTo -11) {
            for (j in -5..6) {
                if (calculations.any { it.x == j && it.y == i}) {
                    coords.add(Coordinate(j,i,true, false))
                }
                else {
                    coords.add(Coordinate(j,i,false, false))
                }
            }
        }
        findUsers()
        notifyDataSetChanged()
    }

    fun submitStrengthList(strengthList: List<Strength>) {
        _strengths = strengthList as ArrayList<Strength>
    }

    fun submitUserList(userList: List<User>) {
        _users = userList as ArrayList<User>
    }

    private fun findUsers() {
        for (i in _users.indices) {
            val foundUser = users.find { it.mac == _users[i].mac }
            if (foundUser == null) {
                val userToAdd = UserCalc(_users[i].mac, ArrayList())
                userToAdd.strengths.add(_users[i].strength)
                users.add(userToAdd)
            }
            else {
                foundUser.strengths.add(_users[i].strength)
            }
        }

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

        for (user in users) {
            var result = Location(user.mac, 999.0, 0)
            for (str in strengths) {
                val calc = distance(user.strengths, str.strengths)
                if (calc < result.distance) {
                    result.distance = calc
                    result.calculationId = str.calculation
                }
            }

            val crd = calculations.find { it.id == result.calculationId }
            val coordinate = coords.find { it.x == (crd?.x ?: -20) && it.y == (crd?.y ?: -20) }
            if (coordinate != null) {
                coordinate.onUser = true
            }

            Log.e("User mac: ", result.mac)
            Log.e("Coordinates: ", calculations.find { it.id == result.calculationId}?.x.toString() + ";" + calculations.find { it.id == result.calculationId}?.y.toString())
            Log.e("Min: ", result.distance.toString())
        }
    }

    private fun distance(vect : ArrayList<Int>, dist: ArrayList<Int>) : Double {
        return sqrt( (vect[0] - dist[0]).toDouble().pow(2) + (vect[1] - dist[1]).toDouble().pow(2) + (vect[2] - dist[2]).toDouble().pow(2))
    }

    companion object {
        private var inflater: LayoutInflater? = null
    }
}