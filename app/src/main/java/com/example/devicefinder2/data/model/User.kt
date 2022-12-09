package com.example.devicefinder2.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User (
    @PrimaryKey
    @SerializedName("id") val id : Int,
    @SerializedName("mac") val mac : String,
    @SerializedName("sensor") val sensor : String,
    @SerializedName("strength") val strength : Int
): Parcelable

