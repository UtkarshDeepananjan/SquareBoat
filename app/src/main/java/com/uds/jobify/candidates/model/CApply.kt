package com.uds.jobify.candidates.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CApply(
    @SerializedName("data")
    @Expose
    val data: Data? = null,

    @SerializedName("code")
    @Expose
    val code: Int? = null,

    @SerializedName("success")
    @Expose
    val success: Boolean? = null,

    @SerializedName("message")
    @Expose
    val message: String? = null
) {

    class Data(
        @SerializedName("id")
        @Expose
        val id: String? = null,

        @SerializedName("updatedAt")
        @Expose
        val updatedAt: String? = null,

        @SerializedName("createdAt")
        @Expose
        val createdAt: String? = null
    )
}
