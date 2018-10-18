package com.dof.myapplication

import android.app.Application
import com.dof.myapplication.service.TMDBClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    var retrofit: Retrofit? = null

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(TMDBClient.HOSTNAME)
                .build()


        /**
         *
         .baseUrl(EcashClient.getApiUrl())
        .client(new EcashClient().get(EcashClient.TIMEOUT))
        .addConverterFactory(GsonConverterFactory.create())
        .build();
         */
    }
}