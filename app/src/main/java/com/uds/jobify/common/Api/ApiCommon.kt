package com.uds.jobify.common.Api

import android.content.Context
import android.util.Log
import com.uds.jobify.candidates.model.CApply
import com.uds.jobify.candidates.model.CJobList
import com.uds.jobify.common.RegisterModel.Login
import com.uds.jobify.common.RegisterModel.Register
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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
}