package com.antino.job24.recruiters.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DJobList(

    @SerializedName("code")
    @Expose
    val code: Int? = null,

    @SerializedName("success")
    @Expose
    val success: Boolean? = null,

    @SerializedName("message")
    @Expose
    val message: String? = null)
