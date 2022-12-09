package com.example.devicefinder2.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "strengths")
data class Strength(
    @PrimaryKey
    @SerializedName("id") val id : Int,
    @SerializedName("calculation") val calculation : Int,
    @SerializedName("sensor") val sensor : String,
    @SerializedName("strength") val strength : Int
): Parcelable
