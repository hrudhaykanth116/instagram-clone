package com.hrudhaykanth116.instagramclone.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTvCreditsResponse(
    val cast: List<Cast?>? = null,
    val crew: List<Crew?>? = null,
    val id: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class Cast(
        val adult: Boolean? = null,
        val character: String? = null,
        val credit_id: String? = null,
        val gender: Int? = null,
        val id: Int? = null,
        val known_for_department: String? = null,
        val name: String? = null,
        val order: Int? = null,
        val original_name: String? = null,
        val popularity: Double? = null,
        val profile_path: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Crew(
        val adult: Boolean? = null,
        val credit_id: String? = null,
        val department: String? = null,
        val gender: Int? = null,
        val id: Int? = null,
        val job: String? = null,
        val known_for_department: String? = null,
        val name: String? = null,
        val original_name: String? = null,
        val popularity: Double? = null,
        val profile_path: String? = null
    )
}