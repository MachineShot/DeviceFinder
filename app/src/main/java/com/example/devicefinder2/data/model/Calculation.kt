package com.example.devicefinder2.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "calculations")
data class Calculation(
    @PrimaryKey
    @SerializedName("id") val id : Int,
    @SerializedName("x") val x : Int,
    @SerializedName("y") val y : Int,
    @SerializedName("length") val length : Double
) : Parcelable