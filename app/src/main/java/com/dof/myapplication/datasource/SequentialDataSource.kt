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
        callAPI(params.key, params.requestedLoadSize) { repos, next ->
            callback.onResult(repos, next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Discover>) {
        Log.d(TAG, "loadBefore: "+params.key)
    }

    private fun callAPI(page: Int, perPage: Int, callback: (repos: List<Discover>, next: Int?) -> Unit) {
        try {
            val response = api.getDiscover(TMDBClient.API_KEY, page).execute()

            response.body()?.let {
                val next: Int = it.page + 1
                callback(it.results, next)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}