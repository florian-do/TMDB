package com.dof.mytmdb.service

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class TMDBClient {
    companion object {
        const val HOSTNAME : String = "https://api.themoviedb.org/3/"

        const val DISCOVER_ROUTE : String = "discover/movie"
        const val MOVIE_ROUTE : String = "movie/{movie_id}"
        const val MOVIE_ROUTE_CREW : String = "movie/{movie_id}/credits"
        const val API_KEY : String = "b4dc27fb266e43bbef9756317657c40f"
    }

    fun get() : OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .build()
    }

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            Log.d("Interceptor", request.url().toString())
            return chain.proceed(request)
        }
    }
}