package com.dof.myapplication.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.databinding.ObservableField
import com.dof.myapplication.App
import com.dof.myapplication.datasource.DataSourceFactory
import com.dof.myapplication.service.DiscoverService
import com.dof.myapplication.service.model.Discover

class MainViewModel : ViewModel() {

    var amount = ObservableField<String>()
    val data : LiveData<PagedList<Discover>>

    init {
        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build()

        val api : DiscoverService = App.retrofit.create(DiscoverService::class.java)
        val dataSourceFactory = DataSourceFactory(api)

        data = LivePagedListBuilder(dataSourceFactory, config).build()
    }
}
