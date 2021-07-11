package com.hrudhaykanth116.instagramclone.data.models.discover

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class DiscoverResult(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String?>? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
){

    companion object{
        // TODO: 12/07/21 Ignoring the diff as list stays in the same position.
        public val diffUtilCallback = object: DiffUtil.ItemCallback<DiscoverResult>(){
            override fun areItemsTheSame(oldItem: DiscoverResult, newItem: DiscoverResult): Boolean {
                // return oldItem.id == newItem.id
                return false
            }

            override fun areContentsTheSame(oldItem: DiscoverResult, newItem: DiscoverResult): Boolean {
                // return oldItem == newItem
                return false
            }

        }
    }

}