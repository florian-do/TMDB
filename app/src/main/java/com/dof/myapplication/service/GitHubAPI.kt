package com.dof.myapplication.service

import com.dof.myapplication.service.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubAPI {
    @GET("/users/{user}/repos")
    fun repos(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") prePage: Int): Call<List<Repo>>
}