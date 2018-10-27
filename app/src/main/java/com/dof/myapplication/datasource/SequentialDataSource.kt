package com.dof.myapplication.datasource

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.dof.myapplication.service.DiscoverService
import com.dof.myapplication.service.TMDBClient
import com.dof.myapplication.service.model.Discover
import com.dof.myapplication.service.model.DiscoverReponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SequentialDataSource(val api : DiscoverService) : PageKeyedDataSource<Int, Discover>() {

    private val TAG = "SequentialDataSource"

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Discover>) {
        callAPI(1, params.requestedLoadSize) { repos, next ->
            Log.d(TAG, "next : $next size : ${repos.size}")
            callback.onResult(repos, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Discover>) {
        Log.d(TAG, "loadAfter: "+params.key)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Discover>) {
        Log.d(TAG, "loadBefore: "+params.key)
    }

    private fun callAPI(page: Int, perPage: Int, callback: (repos: List<Discover>, next: Int?) -> Unit) {
//        api.getDiscover(TMDBClient.API_KEY).enqueue(object : Callback<DiscoverReponse> {
//            override fun onFailure(call: Call<DiscoverReponse>, t: Throwable) {
//                Log.d(TAG, ": FAIL")
//            }
//
//            override fun onResponse(call: Call<DiscoverReponse>, response: Response<DiscoverReponse>) {
//                Log.d(TAG, ": response code -> "+response.code())
//                response.body()?.let {
//                    Log.d(TAG, "list size: "+it.results.size)
//                    callback(it.results, null)
//                }
//            }
//        })

        try {
            val response = api.getDiscover(TMDBClient.API_KEY).execute()

            response.body()?.let {
                val next: Int? = null
                callback(it.results, next)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}