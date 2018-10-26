package com.dof.myapplication

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dof.myapplication.ui.main.MainFragment
import com.dof.myapplication.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
//
//        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
//        viewModel.data.observe(this, Observer {
//            Log.d(TAG, ": observe "+it?.size)
//        })
    }
}