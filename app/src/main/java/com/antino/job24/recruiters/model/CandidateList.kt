package com.antino.job24.recruiters.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CandidateList(
    @SerializedName("data")
    @Expose
    val data: List<Datum>? = null,

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

    class Datum(
        @SerializedName("email")
        @Expose
        val email: String? = null,

        @SerializedName("name")
        @Expose
        val name: String? = null,

        @SerializedName("skills")
        @Expose
        val skills: String? = null,

        @SerializedName("id")
        @Expose
        val id: String? = null
    )
}
