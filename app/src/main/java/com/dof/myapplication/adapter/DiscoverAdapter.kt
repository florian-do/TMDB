package com.dof.myapplication.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dof.myapplication.Const
import com.dof.myapplication.R
import com.dof.myapplication.databinding.AdapterDiscoverBinding
import com.dof.myapplication.module.GlideApp
import com.dof.myapplication.service.model.Discover

class DiscoverAdapter(val context: Context) : PagedListAdapter<Discover, DiscoverAdapter.ViewHolder>(diffCallBack) {

    private val TAG = "DiscoverAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : AdapterDiscoverBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_discover,
                parent,
                false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            GlideApp.with(context)
                    .load(Const.URL_PHOTO+it.poster_path)
                    .fitCenter()
                    .into(holder.binding.cover)
        }
    }

    class ViewHolder(val binding: AdapterDiscoverBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<Discover?>() {

            override fun areItemsTheSame(oldItem: Discover?, newItem: Discover?): Boolean {
                Log.d("DiffUtil.ItemCallback", "areItemsTheSame: ")
                if (oldItem == null || newItem == null) return false
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Discover?, newItem: Discover?): Boolean {
                Log.d("DiffUtil.ItemCallback", "areContentsTheSame: ")
                if (oldItem == null || newItem == null) return false
                return oldItem.id == newItem.id
            }
        }
    }
}