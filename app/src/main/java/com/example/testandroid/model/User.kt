package com.example.testandroid.model

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("name")
    var name: String? = null,
    @SerializedName("user_name")
    var username: String? = null,
    @SerializedName("weather")
    var biography: String? = null,
    @SerializedName("followers")
    var followers: String? = null,
    @SerializedName("followed")
    var followed: String? = null,
    @SerializedName("views")
    var views: String? = null,
    @SerializedName("img")
    var profilePicture: String? = null

)