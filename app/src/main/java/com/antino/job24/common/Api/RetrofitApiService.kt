package com.antino.job24.common.Api

import com.antino.job24.candidates.model.CApply
import com.antino.job24.candidates.model.CJobList
import com.antino.job24.common.RegisterModel.Login
import com.antino.job24.common.RegisterModel.Register
import com.antino.job24.common.utils.Constants
import com.antino.job24.recruiters.model.CandidateList
import com.antino.job24.recruiters.model.CreateJob
import com.antino.job24.recruiters.model.DJobList
import com.antino.job24.recruiters.model.RJobList
import com.google.gson.JsonObject
import okhttp3.ResponseBody
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

    @GET(Constants.rjob)
    suspend fun getcreatedJob(): RJobList

    @HTTP(method = "DELETE", path = Constants.djob, hasBody = true)
    suspend fun deleteJob(
        @Body body: JsonObject): DJobList

    @POST(Constants.djob)
    suspend fun createJob(
        @Body body: JsonObject
    ): CreateJob


    @GET(Constants.appliedcandidates)
    suspend fun getcreatedJob(
        @Path("id") id: String
    ): CandidateList

}