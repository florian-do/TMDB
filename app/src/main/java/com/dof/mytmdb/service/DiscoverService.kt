package com.dof.mytmdb.service

import com.dof.mytmdb.service.model.DiscoverReponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {
    @GET(TMDBClient.DISCOVER_ROUTE)
    fun getDiscover(
            @Query("api_key") apiKey : String,
            @Query("page") page : Int) : Call<DiscoverReponse>
}