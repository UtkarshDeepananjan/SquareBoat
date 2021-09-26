package com.antino.job24.common.Api

import android.content.Context
import android.util.Log
import com.antino.job24.candidates.model.CApply
import com.antino.job24.candidates.model.CJobList
import com.antino.job24.common.RegisterModel.Login
import com.antino.job24.common.RegisterModel.Register
import com.antino.job24.recruiters.model.CandidateList
import com.antino.job24.recruiters.model.CreateJob
import com.antino.job24.recruiters.model.DJobList
import com.antino.job24.recruiters.model.RJobList
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

class ApiCommon {
    fun  register(name: String,email: String,password: String,confirmPassword: String,skills: String,userRole:Int): Flow<Register> = flow {
        val body = JsonObject()
        body.addProperty("name", name)
        body.addProperty("email", email)
        body.addProperty("password", password)
        body.addProperty("confirmPassword", confirmPassword)
        body.addProperty("userRole", userRole)
        body.addProperty("skills", skills)

        val response =
            RetrofitClient().retrofitApiSerwithoutInterceptor.register(body = body)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun  login(email: String,password: String): Flow<Login> = flow {
        val body = JsonObject()
        body.addProperty("email", email)
        body.addProperty("password", password)

        Log.d("TAG", "login: $body")

        val response =
            RetrofitClient().retrofitApiSerwithoutInterceptor.login(body = body)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun  getJob(context:Context): Flow<CJobList> = flow {
        val response =
            RetrofitClient().retrofitApiSerInterceptor(context = context).getJob()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun  applyJob(context:Context,id:String): Flow<CApply> = flow {
        val body = JsonObject()
        body.addProperty("jobId", id)

        Log.d("TAG", "applyJob: $body")
        val response =
            RetrofitClient().retrofitApiSerInterceptor(context = context).applyJob(body = body)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun  appliedJobList(context:Context): Flow<CJobList> = flow {
        val response =
            RetrofitClient().retrofitApiSerInterceptor(context = context).appliedJobList()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun  getCreatedJob(context:Context): Flow<RJobList> = flow {
        val response =
            RetrofitClient().retrofitApiSerInterceptor(context = context).getcreatedJob()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun  deleteJob(context:Context,jobid:String): Flow<DJobList> = flow {
        val body = JsonObject()
        body.addProperty("jobId", jobid)
        Log.d("TAG", "deleteJob: $body")
        val response =
            RetrofitClient().retrofitApiSerInterceptor(context = context).deleteJob(body=body)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun  createJob(context: Context,title: String,des: String,loc: String): Flow<CreateJob> = flow {
        val body = JsonObject()
        body.addProperty("title", title)
        body.addProperty("description", des)
        body.addProperty("location", loc)

        val response =
            RetrofitClient().retrofitApiSerInterceptor(context = context).createJob(body = body)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun  getCandidateJob(context:Context,id: String): Flow<CandidateList> = flow {
        val response =
            RetrofitClient().retrofitApiSerInterceptor(context = context).getcreatedJob(id = id)
        emit(response)
    }.flowOn(Dispatchers.IO)
}