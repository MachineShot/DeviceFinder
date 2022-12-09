package com.example.devicefinder2.data.model

class Coordinate(
    val x : Int,
    val y : Int,
    var mark : Boolean,
    var onUser : Boolean
)

class UserCalc(
    val mac: String,
    val strengths: ArrayList<Int>
)

class StrengthCalc(
    val calculation: Int,
    val strengths: ArrayList<Int>
)

class Location(
    val mac : String,
    var distance: Double,
    var calculationId: Int
)