package com.hrudhaykanth116.instagramclone.ui.common.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hrudhaykanth116.instagramclone.R

@Preview
@Composable
fun ProfileImagePreview(){
    ProfileImage(title = "hrudhaykanth116")
}

@Composable
fun ProfileImage(title: String) {

    val borderBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFA4409),
            Color(0xFFF78D41),
            Color(0xFFF6E79A)
        )
    )

    Column(
        // modifier = Modifier.width(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_android_white_24dp),
            contentDescription = "Profile log",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .border(2.dp, borderBrush, CircleShape)
                .padding(4.dp) // Padding between border and content
                .clip(CircleShape) // Circular image
                .background(Color.Black) // Image background
                .padding(2.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // TODO: 11/09/21 max width
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}