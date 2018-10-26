package com.dof.myapplication.datasource

import android.arch.paging.DataSource
import com.dof.myapplication.service.DiscoverService
import com.dof.myapplication.service.model.Discover

class DataSourceFactory(api: DiscoverService) : DataSource.Factory<Int, Discover>() {
    val source = SequentialDataSource(api)

    override fun create(): DataSource<Int, Discover> {
        return source
    }
}