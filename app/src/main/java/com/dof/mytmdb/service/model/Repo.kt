package com.dof.mytmdb.service.model

import com.google.gson.annotations.SerializedName

data class Repo(
        @SerializedName("id")
        val id: String,
        @SerializedName("full_name")
        val fullName: String)