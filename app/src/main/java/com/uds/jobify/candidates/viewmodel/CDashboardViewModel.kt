package com.uds.jobify.candidates.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uds.jobify.candidates.model.CApply
import com.uds.jobify.candidates.model.CJobList
import com.uds.jobify.common.Api.ApiCommon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CDashboardViewModel:ViewModel() {
    var responseCJobList: MutableLiveData<CJobList?> = MutableLiveData()
    var responseCApply: MutableLiveData<CApply?> = MutableLiveData()
    var responseCappliedJobList: MutableLiveData<CJobList?> = MutableLiveData()


    fun getJob(context:Context) {
        viewModelScope.launch {
            ApiCommon().getJob(context = context)
                .catch { e ->

                    var errorResponse: CJobList?=null
                    when(e){
                        is HttpException -> {
                            val gson = Gson()
                            val type = object : TypeToken<CJobList>() {}.type
                            errorResponse = gson.fromJson(
                                e.response()?.errorBody()!!.charStream(), type
                            )
                        }
                    }

                    responseCJobList.value=errorResponse
                }.collect {response ->
                    responseCJobList.value = response
                }
        }
    }

    fun applyJob(context:Context,id:String) {
        viewModelScope.launch {
            ApiCommon().applyJob(context = context,id = id)
                .catch { e ->

                    var errorResponse: CApply?=null
                    when(e){
                        is HttpException -> {
                            val gson = Gson()
                            val type = object : TypeToken<CApply>() {}.type
                            errorResponse = gson.fromJson(
                                e.response()?.errorBody()!!.charStream(), type
                            )
                        }
                    }

                    responseCApply.value=errorResponse
                }.collect {response ->
                    responseCApply.value = response
                }
        }
    }

    fun appliedJobList(context:Context) {
        viewModelScope.launch {
            ApiCommon().appliedJobList(context = context)
                .catch { e ->

                    var errorResponse: CJobList?=null
                    when(e){
                        is HttpException -> {
                            val gson = Gson()
                            val type = object : TypeToken<CJobList>() {}.type
                            errorResponse = gson.fromJson(
                                e.response()?.errorBody()!!.charStream(), type
                            )
                        }
                    }

                    responseCappliedJobList.value=errorResponse
                }.collect {response ->
                    responseCappliedJobList.value = response
                }
        }
    }
}