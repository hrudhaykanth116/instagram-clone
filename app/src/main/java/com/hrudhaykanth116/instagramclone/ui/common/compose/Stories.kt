package com.hrudhaykanth116.instagramclone.ui.common.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun StoriesView(list: List<String>) {
    LazyRow(
        // first item will add 8.dp padding to it’s top, the last item will add 8.dp to
        // its bottom, and all items will have 16.dp padding on the left and the right.
        // contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        // Space between the items.
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { text ->
            ProfileImage(title = text)
        }
    }
}
