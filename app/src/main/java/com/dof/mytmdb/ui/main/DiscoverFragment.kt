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
import com.dof.mytmdb.R
import com.dof.mytmdb.adapter.DiscoverAdapter
import com.dof.mytmdb.databinding.DiscoverFragmentBinding
import com.dof.mytmdb.viewmodel.DiscoverViewModel

class DiscoverFragment : Fragment() {

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
        binding.rvFeed.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
//        binding.rvFeed.setHasFixedSize(true)
        binding.rvFeed.adapter = adapter

        viewModel.data.observe(this, Observer {
            Log.d(TAG, ": ${it?.size}")
            adapter.submitList(it)
        })
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