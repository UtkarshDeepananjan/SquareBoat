package com.uds.job24.common.RegisterModel

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Login(
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
){
    
    class Data(@SerializedName("email")
               @Expose
                val email: String? = null,

        @SerializedName("name")
    @Expose
     val name: String? = null,

    @SerializedName("skills")
    @Expose
     val skills: String? = null,

    @SerializedName("userRole")
    @Expose
     val userRole: Int? = null,

    @SerializedName("createdAt")
    @Expose
     val createdAt: String? = null,

    @SerializedName("updatedAt")
    @Expose
     val updatedAt: String? = null,

    @SerializedName("id")
    @Expose
     val id: String? = null,

    @SerializedName("token")
    @Expose
     val token: String? = null)

}
