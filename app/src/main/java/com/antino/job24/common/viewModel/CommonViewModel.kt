package com.antino.job24.common.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antino.job24.common.Api.ApiCommon
import com.antino.job24.common.RegisterModel.Login
import com.antino.job24.common.RegisterModel.Register
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CommonViewModel: ViewModel() {
    var responseRegster: MutableLiveData<Register?> = MutableLiveData()
    var responseLogin: MutableLiveData<Login?> = MutableLiveData()


    fun register(name: String,email: String,password: String,confirmPassword: String,skills: String,userRole:Int) {
        viewModelScope.launch {
            ApiCommon().register(name=name,email=email,password=password,confirmPassword=confirmPassword,skills=skills,userRole=userRole)
                .catch { e ->

                    var errorResponse: Register?=null
                    when(e){
                        is HttpException -> {
                            val gson = Gson()
                            val type = object : TypeToken<Register>() {}.type
                            errorResponse = gson.fromJson(
                                e.response()?.errorBody()!!.charStream(), type
                            )
                        }
                    }

                    responseRegster.value=errorResponse
                }.collect {response ->
                    responseRegster.value = response
                }
        }
    }
    fun login(email: String,password: String) {
        viewModelScope.launch {
            ApiCommon().login(email=email,password=password)
                .catch { e ->

                    var errorResponse: Login?=null
                    when(e){
                        is HttpException -> {
                            val gson = Gson()
                            val type = object : TypeToken<Login>() {}.type
                            errorResponse = gson.fromJson(
                                e.response()?.errorBody()!!.charStream(), type
                            )
                        }
                    }


                    responseLogin.value=errorResponse
                }.collect {response ->
                    responseLogin.value = response
                }
        }
    }
}