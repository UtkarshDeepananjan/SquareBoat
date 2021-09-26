package com.antino.job24.recruiters.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antino.job24.candidates.model.CJobList
import com.antino.job24.common.Api.ApiCommon
import com.antino.job24.common.RegisterModel.Login
import com.antino.job24.recruiters.model.CandidateList
import com.antino.job24.recruiters.model.CreateJob
import com.antino.job24.recruiters.model.DJobList
import com.antino.job24.recruiters.model.RJobList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException

class RDashboardViewModel: ViewModel() {
    var responseRJobList: MutableLiveData<RJobList?> = MutableLiveData()
    var responsedeleteResponseBody: MutableLiveData<DJobList?> = MutableLiveData()
    var responseCreateJob: MutableLiveData<CreateJob?> = MutableLiveData()
    var responseCandidateList: MutableLiveData<CandidateList?> = MutableLiveData()

    fun getcreatedJob(context: Context) {
        viewModelScope.launch {
            ApiCommon().getCreatedJob(context = context)
                .catch { e ->

                    var errorResponse: RJobList?=null
                    when(e){
                        is HttpException -> {
                            val gson = Gson()
                            val type = object : TypeToken<RJobList>() {}.type
                            errorResponse = gson.fromJson(
                                e.response()?.errorBody()!!.charStream(), type
                            )
                        }
                    }

                    responseRJobList.value=errorResponse
                }.collect {response ->
                    responseRJobList.value = response
                }
        }
    }
    fun deleteJob(context: Context,jobid:String) {
        viewModelScope.launch {
            ApiCommon().deleteJob(context = context,jobid = jobid)
                .catch { e ->

                    var errorResponse: DJobList?=null
                    when(e){
                        is HttpException -> {
                            val gson = Gson()
                            val type = object : TypeToken<DJobList>() {}.type
                            errorResponse = gson.fromJson(
                                e.response()?.errorBody()!!.charStream(), type
                            )
                        }
                    }

                    responsedeleteResponseBody.value=errorResponse
                }.collect {response ->
                    responsedeleteResponseBody.value = response
                }
        }
    }

    fun createJob(context: Context,title: String,des: String,loc: String) {
        viewModelScope.launch {
            ApiCommon().createJob(context = context,
                title = title,
                des = des,
                loc = loc)
                .catch { e ->

                    var errorResponse: CreateJob?=null
                    when(e){
                        is HttpException -> {
                            val gson = Gson()
                            val type = object : TypeToken<CreateJob>() {}.type
                            errorResponse = gson.fromJson(
                                e.response()?.errorBody()!!.charStream(), type
                            )
                        }
                    }


                    responseCreateJob.value=errorResponse
                }.collect {response ->
                    responseCreateJob.value = response
                }
        }
    }

    fun getCandidateJob(context: Context,id:String) {
        viewModelScope.launch {
            ApiCommon().getCandidateJob(context = context,id = id)
                .catch { e ->

                    var errorResponse: CandidateList?=null
                    when(e){
                        is HttpException -> {
                            val gson = Gson()
                            val type = object : TypeToken<CandidateList>() {}.type
                            errorResponse = gson.fromJson(
                                e.response()?.errorBody()!!.charStream(), type
                            )
                        }
                    }

                    responseCandidateList.value=errorResponse
                }.collect {response ->
                    responseCandidateList.value = response
                }
        }
    }
}