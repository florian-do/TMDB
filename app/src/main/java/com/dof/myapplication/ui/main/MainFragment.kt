package com.dof.myapplication.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.dof.myapplication.datasource.SequentialDataSource
import com.dof.myapplication.repository.DiscoverRepo
import com.dof.myapplication.service.model.Discover
import java.util.concurrent.Executor
import java.util.concurrent.Executors

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

        val config = PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(20)
                .build()

//        val pagedList = LivePagedListBuilder<Int, Discover>(SequentialDataSource(this, DiscoverRepo()), config)
//                .setNotifyExecutor(UiThreadExecutor())
//                .setFetchExecutor(BackgroundThreadExecutor())
//                .build()

        val mock = MockNotesDataSourceFactory(SequentialDataSource(this, DiscoverRepo()))
        val test : LiveData<PagedList<Discover>> = LivePagedListBuilder<Int, Discover>(mock, config).build()
        val adapter = DiscoverAdapter(context!!, diffCallBack)
        binding.rvFeed.layoutManager = GridLayoutManager(context, 3)
        binding.rvFeed.setHasFixedSize(true)
        binding.rvFeed.adapter = adapter

        test.observe(this, Observer { it ->
            Log.d(TAG, ": "+it?.size)
            it?.let {
                Log.d(TAG, ": "+ it.size)
                adapter.submitList(it)
            }
        })
    }

    fun test(x: Any) {
        when (x) {
            is Int -> println("INT")
            is String -> println("STRING")
            else -> println("TAMERE")
        }
    }

    class MockNotesDataSourceFactory(val dataSource: SequentialDataSource) : DataSource.Factory<Int, Discover>() {
        override fun create(): DataSource<Int, Discover> = dataSource
    }

    internal inner class UiThreadExecutor : Executor {
        private val mHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mHandler.post(command)
        }
    }

    internal inner class BackgroundThreadExecutor : Executor {
        private val executorService = Executors.newFixedThreadPool(3)

        override fun execute(command: Runnable) {
            executorService.execute(command)
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