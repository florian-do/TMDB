package com.dof.myapplication.datasource

import android.arch.lifecycle.Observer
import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.dof.myapplication.repository.DiscoverRepo
import com.dof.myapplication.service.model.Discover
import com.dof.myapplication.ui.main.MainFragment

class SequentialDataSource(val c : MainFragment, val repo : DiscoverRepo) : PageKeyedDataSource<Int, Discover>() {

    private val TAG = "SequentialDataSource"

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Discover>) {
        Log.d(TAG, "loadInitial: ")
        repo.getDiscover().observe(c, Observer {
            Log.d(TAG, "OK: "+it?.page)
            Log.d(TAG, "OK: "+it?.results?.size)
            callback.onResult(it?.results.orEmpty(), null, 2)
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Discover>) {
        Log.d(TAG, "loadAfter: "+params.key)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Discover>) {
        Log.d(TAG, "loadBefore: "+params.key)
    }
}