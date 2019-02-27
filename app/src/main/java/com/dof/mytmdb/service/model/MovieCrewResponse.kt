package com.dof.mytmdb.service.model

data class MovieCrewResponse(
    val cast: List<Crew>,
    val crew: List<Crew>,
    val id: Int
)

data class Crew(
    val cast_id: Int,
    val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val name: String,
    val character: String,
    val order: Int,
    val profile_path: String
)


