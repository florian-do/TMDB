package com.dof.mytmdb

import android.app.Application
import com.dof.mytmdb.repository.RepositoryFactory
import com.dof.mytmdb.service.TMDBClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        var repoFactory: RepositoryFactory = RepositoryFactory()
        var retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(TMDBClient.HOSTNAME)
                .client(TMDBClient().get())
                .build()
    }
}