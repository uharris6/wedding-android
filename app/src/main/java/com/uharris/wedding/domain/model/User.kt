package com.uharris.wedding.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") var id: String = "",
    @SerializedName("firstname") var firstName: String = "",
    @SerializedName("lastname") var lastName: String = "",
    @SerializedName("nickname") var nickname: String = "")