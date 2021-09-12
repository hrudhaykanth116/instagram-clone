package com.hrudhaykanth116.instagramclone.ui.screens.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hrudhaykanth116.instagramclone.R
import com.hrudhaykanth116.instagramclone.data.models.TvShowDetails
import com.hrudhaykanth116.instagramclone.ui.screens.profile.castcrew.TvCastFragment
import com.hrudhaykanth116.instagramclone.ui.screens.profile.images.TvImagesFragment
import com.hrudhaykanth116.instagramclone.ui.screens.profile.reviews.TvReviewsFragment
import com.hrudhaykanth116.instagramclone.ui.screens.profile.similar.TvSimilarShowFragment
import com.hrudhaykanth116.instagramclone.ui.screens.profile.videos.TvVideosFragment

class ProfileContentViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val tvShowDetails: TvShowDetails
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return TABS_LIST.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TvImagesFragment.getInstance(tvShowDetails)
            1 -> TvCastFragment.getInstance(tvShowDetails)
            2 -> TvReviewsFragment.getInstance(tvShowDetails)
            3 -> TvVideosFragment.getInstance(tvShowDetails)
            4 -> TvSimilarShowFragment.getInstance(tvShowDetails)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    companion object {

        val TABS_LIST = listOf(
            TabItem(R.drawable.ic_image_24, "images"),
            TabItem(R.drawable.ic_baseline_person_24, "cast"),
            TabItem(R.drawable.ic_baseline_forum_24, "review"),
            TabItem(R.drawable.ic_baseline_videocam_24, "videos"),
            TabItem(R.drawable.ic_baseline_view_comfy_24, "similar"),
        )

    }

    data class TabItem(
        val iconId: Int,
        val text: String
    )

}