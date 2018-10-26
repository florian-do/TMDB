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

class SequentialDataSource(val api : DiscoverService) : PageKeyedDataSource<Int, Discover>() {

    private val TAG = "SequentialDataSource"

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Discover>) {

        api.getDiscover(TMDBClient.API_KEY).enqueue(object : Callback<DiscoverReponse> {
            override fun onFailure(call: Call<DiscoverReponse>, t: Throwable) {
                Log.d(TAG, ": FAIL")
            }

            override fun onResponse(call: Call<DiscoverReponse>, response: Response<DiscoverReponse>) {
                Log.d(TAG, ": response code -> "+response.code())
                val it = response.body();
                Log.d(TAG, "list size: "+it?.results?.size)

                response.body()?.let {
                    callback.onResult(it.results, null, 2)
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Discover>) {
        Log.d(TAG, "loadAfter: "+params.key)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Discover>) {
        Log.d(TAG, "loadBefore: "+params.key)
    }
}