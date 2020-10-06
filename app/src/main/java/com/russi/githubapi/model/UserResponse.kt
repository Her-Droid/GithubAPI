package com.russi.githubapi.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class UserResponse(

    @SerializedName("users")
    val users: List<DataUser>
)

@Parcelize
data class DataUser(

    @SerializedName("follower")
    val follower: Int = 0,

    @SerializedName("following")
    val following: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("company")
    val company: String = "",

    @SerializedName("location")
    val location: String = "",

    @SerializedName("avatar")
    var avatar: String = "",

    @SerializedName("repository")
    val repository: Int = 0,

    @SerializedName("username")
    val username: String = "",

    @SerializedName("login")
    var login: String = "",

    @SerializedName("avatar_url")
    var avatarUrl: String = ""
) : Parcelable
