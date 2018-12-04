package com.dof.mytmdb.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.dof.mytmdb.App
import com.dof.mytmdb.repository.MovieRepo
import com.dof.mytmdb.service.model.MovieCrewResponse
import com.dof.mytmdb.service.model.MovieDetailResponse

class MovieViewModel : ViewModel() {

    private val repo : MovieRepo

    init {
        repo = MovieRepo()
    }

    fun getMovieDetail(id : Int) : LiveData<MovieDetailResponse> {
        return repo.getMovieDetail(id)
    }

    fun getMovieCrews(id: Int) : LiveData<MovieCrewResponse> {
        return repo.getMovieCrews(id)
    }

}