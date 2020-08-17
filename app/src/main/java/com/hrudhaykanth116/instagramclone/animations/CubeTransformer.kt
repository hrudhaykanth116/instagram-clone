package com.hrudhaykanth116.instagramclone.animations

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class CubeTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {

        view.pivotX = if (position < 0F) view.width.toFloat() else 0F
        view.pivotY = view.height.toFloat() * 0.5f
        view.rotationY = 30f * position

    }

}