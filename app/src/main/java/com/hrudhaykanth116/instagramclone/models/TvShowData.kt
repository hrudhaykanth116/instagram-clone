package com.hrudhaykanth116.instagramclone.models

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowData(
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    @SerializedName("first_air_date")
    val firstAirDate: String? = "",
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = listOf(),
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("origin_country")
    val originCountry: List<String>? = listOf(),
    @SerializedName("original_language")
    val originalLanguage: String? = "",
    @SerializedName("original_name")
    val originalName: String? = "",
    @SerializedName("overview")
    val overview: String? = "",
    @SerializedName("popularity")
    val popularity: Double? = 0.0,
    @SerializedName("poster_path")
    val posterPath: String? = "",
    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerializedName("vote_count")
    val voteCount: Int? = 0
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TvShowData

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }

    companion object{
        public val diffUtillCallback = object: DiffUtil.ItemCallback<TvShowData>(){
            override fun areItemsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TvShowData, newItem: TvShowData): Boolean {
                return oldItem.equals(newItem)
            }

        }
    }

}