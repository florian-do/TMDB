package com.dof.mytmdb.listener

interface onRowListener<T> {
    fun onRowClick(data : T)
}