package com.dof.mytmdb.datasource

import android.arch.paging.DataSource
import com.dof.mytmdb.service.DiscoverService
import com.dof.mytmdb.service.model.Discover

class DiscoverDataSourceFactory(api: DiscoverService) : DataSource.Factory<Int, Discover>() {

    val source = DiscoverDataSource(api)

    override fun create(): DataSource<Int, Discover> {
        return source
    }
}