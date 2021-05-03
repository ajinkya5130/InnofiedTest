package com.ajinkya.assignmentapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajinkya.assignmentapp.api_call.RetrofitClient
import com.ajinkya.assignmentapp.listeners.IUserInfoListener
import com.ajinkya.assignmentapp.models.UsersInfoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {

    private var mutableUserList: MutableLiveData<UsersInfoModel>? = null
    private var mutableErrorResponse: MutableLiveData<Throwable>? = null
    lateinit var listener: IUserInfoListener

    init {
        mutableUserList = MutableLiveData()
        mutableErrorResponse = MutableLiveData()
    }


    fun callAPIForUserList() {
        RetrofitClient.getInstance().myApi.getUsersList(
            listener.getPageNo(),
            listener.getPerPageCount()
        )
            .enqueue(object : Callback<UsersInfoModel> {
                override fun onResponse(
                    call: Call<UsersInfoModel>,
                    response: Response<UsersInfoModel>
                ) {
                    mutableUserList!!.value = response.body()
                }

                override fun onFailure(call: Call<UsersInfoModel?>, t: Throwable) {
                    mutableErrorResponse!!.value = t
                }
            })
    }


    fun getUserListInfo(): LiveData<UsersInfoModel?>? {
        return mutableUserList
    }

    fun getErrorInfo(): LiveData<Throwable?>? {
        return mutableErrorResponse
    }

}