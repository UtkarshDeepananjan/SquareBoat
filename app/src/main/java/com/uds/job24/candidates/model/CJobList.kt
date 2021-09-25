package com.uds.job24.candidates.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CJobList(
    @SerializedName("data")
    @Expose
    val data: List<Datum>? = null,

    @SerializedName("code")
    @Expose
    val code: Int? = null,

    @SerializedName("message")
    @Expose
    val message: String? = null,

    @SerializedName("success")
    @Expose
    val success: Boolean? = null,

    @SerializedName("metadata")
    @Expose
    val metadata: Metadata? = null
) {


    class Datum(
        @SerializedName("title")
        @Expose
        val title: String? = null,

        @SerializedName("description")
        @Expose
        val description: String? = null,

        @SerializedName("location")
        @Expose
        val location: String? = null,

        @SerializedName("createdAt")
        @Expose
        val createdAt: String? = null,

        @SerializedName("updatedAt")
        @Expose
        val updatedAt: String? = null,

        @SerializedName("id")
        @Expose
        val id: String? = null
    )

    class Metadata(
        @SerializedName("count")
        @Expose
        val count: Int? = null,

        @SerializedName("limit")
        @Expose
        val limit: Int? = null
    )

}
