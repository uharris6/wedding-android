package com.uharris.wedding.domain.model.body

import com.google.gson.annotations.SerializedName

data class UserBody(
    @SerializedName("firstname") var firstName: String = "",
    @SerializedName("nickname") var nickname: String = "",
    @SerializedName("lastname") var lastName: String = "")