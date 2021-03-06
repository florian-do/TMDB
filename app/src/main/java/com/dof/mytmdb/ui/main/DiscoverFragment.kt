package com.dof.mytmdb.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dof.mytmdb.MainActivity
import com.dof.mytmdb.R
import com.dof.mytmdb.adapter.DiscoverAdapter
import com.dof.mytmdb.databinding.DiscoverFragmentBinding
import com.dof.mytmdb.listener.onRowListener
import com.dof.mytmdb.service.model.Discover
import com.dof.mytmdb.viewmodel.DiscoverViewModel

import kotlinx.coroutines.*

class DiscoverFragment : Fragment(), onRowListener<Discover> {

    private val TAG = "MainFragment"

    companion object {
        fun newInstance() = DiscoverFragment()
    }

    private lateinit var viewModel: DiscoverViewModel
    private lateinit var binding : DiscoverFragmentBinding
    private lateinit var adapter : DiscoverAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.discover_fragment,
                container,
                false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DiscoverViewModel::class.java)

        adapter = DiscoverAdapter(context!!)
        adapter.mListener = this
        binding.rvFeed.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
//        binding.rvFeed.setHasFixedSize(true)
        binding.rvFeed.adapter = adapter

        viewModel.data.observe(this, Observer {
            Log.d(TAG, ": ${it?.size}")
            adapter.submitList(it)
        })
    }

    override fun onRowClick(data: Discover, imageView: ImageView) {
        MovieActivity.newActivity(activity as MainActivity, data.id, imageView)
//        test()
    }

    fun test() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) { // cancellable computation loop
                // print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    fun poubelle() {

        test(1)
        test(viewModel)
        test("test")

        var lastName : String? = null
        val l = lastName?.length
        println(l)

        lastName.let { println(it) }


        val originalList = listOf(1, 2, 3, 4, 5, null, 7, 8, 9, 10)

        for (i in originalList) {
            println(i)
        }
    }

    fun test(x: Any) {
        when (x) {
            is Int -> println("INT")
            is String -> println("STRING")
            else -> println("TAMERE")
        }
    }
}