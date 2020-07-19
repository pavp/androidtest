package com.example.testandroid.model

import com.google.gson.annotations.SerializedName

data class Asset (
    @SerializedName("profile")
    var user: User? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("record_video")
    var recordVideo: String? = null,
    @SerializedName("preview_img")
    var previewImg: String? = null
)

