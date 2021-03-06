package com.dof.mytmdb.adapter

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.view.ViewCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dof.mytmdb.Const
import com.dof.mytmdb.R
import com.dof.mytmdb.databinding.AdapterDiscoverBinding
import com.dof.mytmdb.listener.onRowListener
import com.dof.mytmdb.module.GlideApp
import com.dof.mytmdb.service.model.Discover

class DiscoverAdapter(val context: Context) : PagedListAdapter<Discover, DiscoverAdapter.ViewHolder>(diffCallBack) {

    private val TAG = "DiscoverAdapter"
    var mListener : onRowListener<Discover> ?= null

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
            val data : Discover = it

            GlideApp.with(context)
                    .load(Const.URL_PHOTO+it.poster_path)
                    .fitCenter()
                    .into(holder.binding.cover)

            ViewCompat.setTransitionName(holder.binding.cover, it.id.toString());

            holder.binding.root.setOnClickListener {
                mListener.let {
                    it?.onRowClick(data, holder.binding.cover)
                }
            }
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