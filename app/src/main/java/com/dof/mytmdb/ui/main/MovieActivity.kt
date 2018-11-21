package com.dof.mytmdb.ui.main

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dof.mytmdb.Const
import com.dof.mytmdb.R
import com.dof.mytmdb.databinding.ActivityMovieBinding
import com.dof.mytmdb.module.GlideApp
import com.dof.mytmdb.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {

    private var id : Int = 0
    private lateinit var viewModel : MovieViewModel
    private lateinit var binding : ActivityMovieBinding

    companion object {
        private val TAG = "MovieActivity"
        private val ARG_ID = "arg_id"

        fun newActivity(a: Activity, id: Int) {
            val intent = Intent(a, MovieActivity::class.java)
            intent.putExtra(ARG_ID, id)
            a.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        id = intent.getIntExtra(ARG_ID, 0)
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.getMovieDetail(id).observe(this, Observer {
            it.let {
                binding.data = it
                GlideApp.with(this)
                        .load(Const.URL_PHOTO_ORIGINAL+it?.backdrop_path)
                        .into(backDrop)

                GlideApp.with(this)
                        .load(Const.URL_PHOTO+it?.poster_path)
                        .into(cover)

                with (cover) {
                    setFadingEdgeLength(40)
                }

                Log.d(TAG, " NAME : ${Const.URL_PHOTO+it?.backdrop_path}")
            }
        })
    }
}
