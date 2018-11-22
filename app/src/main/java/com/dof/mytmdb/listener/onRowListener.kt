package com.dof.mytmdb.listener

import android.widget.ImageView

interface onRowListener<T> {
    fun onRowClick(data : T, imgView : ImageView)
}