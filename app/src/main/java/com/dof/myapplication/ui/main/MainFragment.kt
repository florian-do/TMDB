package com.dof.myapplication.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dof.myapplication.R
import com.dof.myapplication.adapter.DiscoverAdapter
import com.dof.myapplication.databinding.MainFragmentBinding
import com.dof.myapplication.service.model.Discover

class MainFragment : Fragment() {

    private val TAG = "MainFragment"

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
//        val binding: DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.main_fragment,
                container,
                false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.vm = viewModel
        viewModel.amount.set("test")

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

        val adapter = DiscoverAdapter(context!!, diffCallBack)
        binding.rvFeed.layoutManager = GridLayoutManager(context, 3)
        binding.rvFeed.setHasFixedSize(true)
        binding.rvFeed.adapter = adapter

//        viewModel.data.observe(this, Observer {
//            Log.d(TAG, ": observe "+it?.size)
//        })
    }

    fun test(x: Any) {
        when (x) {
            is Int -> println("INT")
            is String -> println("STRING")
            else -> println("TAMERE")
        }
    }

    val diffCallBack = object : DiffUtil.ItemCallback<Discover?>() {

        override fun areItemsTheSame(oldItem: Discover?, newItem: Discover?): Boolean {
            Log.d(TAG, "areItemsTheSame: ")
            if (oldItem == null || newItem == null) return false
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Discover?, newItem: Discover?): Boolean {
            Log.d(TAG, "areContentsTheSame: ")
            if (oldItem == null || newItem == null) return false
            return oldItem.id == newItem.id
        }
    }
}