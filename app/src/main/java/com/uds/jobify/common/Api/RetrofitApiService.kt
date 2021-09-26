package com.uds.jobify.common.Api

import com.uds.jobify.candidates.model.CApply
import com.uds.jobify.candidates.model.CJobList
import com.uds.jobify.common.RegisterModel.Login
import com.uds.jobify.common.RegisterModel.Register
import com.uds.jobify.common.utils.Constants
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