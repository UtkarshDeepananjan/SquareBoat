package com.uds.job24.common.Api

import com.uds.job24.candidates.model.CApply
import com.uds.job24.candidates.model.CJobList
import com.uds.job24.common.RegisterModel.Login
import com.uds.job24.common.RegisterModel.Register
import com.uds.job24.common.utils.Constants
import com.google.gson.JsonObject
import retrofit2.http.*

interface RetrofitApiService {
    @POST(Constants.register)
    suspend fun register(
        @Body body: JsonObject
    ): Register

    @POST(Constants.login)
    suspend fun login(
        @Body body: JsonObject
    ): Login

    @GET(Constants.cjob)
    suspend fun getJob(): CJobList

    @POST(Constants.cjob)
    suspend fun applyJob(
        @Body body: JsonObject): CApply

    @GET(Constants.capplied)
    suspend fun appliedJobList(): CJobList

}