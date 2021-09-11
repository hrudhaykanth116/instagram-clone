package com.hrudhaykanth116.instagramclone.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTvReviewsResponse(
    val id: Int? = null,
    val page: Int? = null,
    val results: List<Result?>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        val author: String? = null,
        val author_details: AuthorDetails? = null,
        val content: String? = null,
        val created_at: String? = null,
        val id: String? = null,
        val updated_at: String? = null,
        val url: String? = null
    ) {
        @JsonClass(generateAdapter = true)
        data class AuthorDetails(
            val avatar_path: String? = null,
            val name: String? = null,
            val rating: Int? = null,
            val username: String? = null
        )
    }
}