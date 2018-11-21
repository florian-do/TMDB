package com.dof.mytmdb.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.dof.mytmdb.repository.MovieRepo
import com.dof.mytmdb.service.model.MovieDetailResponse

class MovieViewModel : ViewModel() {

    private val repo : MovieRepo

    init {
        repo = MovieRepo()
    }

    fun getMovieDetail(id : Int) : LiveData<MovieDetailResponse> {
        return repo.getMovieDetail(id)
    }

}