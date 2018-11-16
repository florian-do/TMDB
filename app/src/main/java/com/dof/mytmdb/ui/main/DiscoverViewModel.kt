package com.dof.mytmdb.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.dof.mytmdb.App
import com.dof.mytmdb.datasource.DiscoverDataSourceFactory
import com.dof.mytmdb.service.DiscoverService
import com.dof.mytmdb.service.model.Discover

class DiscoverViewModel : ViewModel() {
    val data : LiveData<PagedList<Discover>>

    init {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build()

        val api : DiscoverService = App.retrofit.create(DiscoverService::class.java)
        val dataSourceFactory = DiscoverDataSourceFactory(api)

        data = LivePagedListBuilder(dataSourceFactory, config).build()
    }
}
