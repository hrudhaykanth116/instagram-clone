package com.hrudhaykanth116.instagramclone.models

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieData(
    @PrimaryKey
    var id: Int?,
    var adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    var title: String?,
    var video: Boolean?,
    @SerializedName("vote_average")
    var voteAverage: Double?,
    @SerializedName("vote_count")
    var voteCount: Int?
){

    override fun equals(other: Any?): Boolean {
        if (other == this){
            return true
        }
        return (other as MovieData).id == this.id
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    companion object{
        public val diffUtillCallback = object: DiffUtil.ItemCallback<MovieData>(){
            override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.equals(newItem)
            }

        }
    }

}