package com.dof.mytmdb.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.dof.mytmdb.App
import com.dof.mytmdb.service.DiscoverService
import com.dof.mytmdb.service.model.DiscoverReponse

class DiscoverRepo() : Repository {

    private val TAG = "DiscoverRepo"

    var api : DiscoverService

    init {
        api = App.retrofit.create(DiscoverService::class.java)
    }

    fun getDiscover() : LiveData<DiscoverReponse> {
        val data : MutableLiveData<DiscoverReponse>? = MutableLiveData()
        Log.d(TAG, ": getDiscover")

//        api.getDiscover(TMDBClient.API_KEY).enqueue(object : Callback<DiscoverReponse> {
//            override fun onFailure(call: Call<DiscoverReponse>, t: Throwable) {
//                Log.d(TAG, ": FAIL")
//                data?.value = null
//            }
//
//            override fun onResponse(call: Call<DiscoverReponse>, response: Response<DiscoverReponse>) {
//                Log.d(TAG, ": OK -> "+response.code())
//                data?.value = response.body()
//            }
//
//        })

        return data!!
    }
}