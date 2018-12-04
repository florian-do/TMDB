package com.dof.mytmdb.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.dof.mytmdb.App
import com.dof.mytmdb.service.MovieService
import com.dof.mytmdb.service.TMDBClient
import com.dof.mytmdb.service.model.MovieCrewResponse
import com.dof.mytmdb.service.model.MovieDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepo {

    private val api : MovieService

    init {
        api = App.retrofit.create(MovieService::class.java)
    }

    fun getMovieDetail(id : Int) : LiveData<MovieDetailResponse> {
        val data : MutableLiveData<MovieDetailResponse> ?= MutableLiveData()

        api.getMovieDetail(id, TMDBClient.API_KEY).enqueue(object : Callback<MovieDetailResponse> {
            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                data?.value = null
            }

            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                data?.value = response.body()
            }
        })

        return data!!
    }

    fun getMovieCrews(id: Int) : LiveData<MovieCrewResponse> {
        val data : MutableLiveData<MovieCrewResponse> ?= MutableLiveData()

        api.getMovieCrews(id, TMDBClient.API_KEY).enqueue(object : Callback<MovieCrewResponse> {
            override fun onFailure(call: Call<MovieCrewResponse>, t: Throwable) {
                data?.value = null
            }

            override fun onResponse(call: Call<MovieCrewResponse>, response: Response<MovieCrewResponse>) {
                data?.value = response.body()
            }

        })

        return data!!
    }
}