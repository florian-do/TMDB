package com.dof.mytmdb.service

import com.dof.mytmdb.service.model.MovieCrewResponse
import com.dof.mytmdb.service.model.MovieDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET(TMDBClient.MOVIE_ROUTE)
    fun getMovieDetail(
            @Path("movie_id") id : Int,
            @Query("api_key") apiKey : String) : Call<MovieDetailResponse>

    @GET(TMDBClient.MOVIE_ROUTE_CREW)
    fun getMovieCrews(
            @Path("movie_id") id : Int,
            @Query("api_key") apiKey : String) : Call<MovieCrewResponse>
}