package com.hrudhaykanth116.instagramclone.ui.screens.account

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hrudhaykanth116.instagramclone.ui.common.compose.ProfileImage
import com.hrudhaykanth116.instagramclone.ui.common.compose.StoriesView
import com.hrudhaykanth116.instagramclone.ui.common.compose.ValueUnitView
import com.hrudhaykanth116.instagramclone.utils.toasts.ToastHelper

@Preview(showBackground = true)
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val storiesList = listOf(
        "Test1",
        "Test2",
        "Test3",
        "Test4",
        "Test5",
        "Test6",
        "Test7",
        "Test8",
        "Test9",
        "Test10",
        "Test2",
        "Test2",
        "Test2",
        "Test2",
        "Test2",
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Cyan,
                        Color.Transparent
                    )
                )
            )
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row() {
                    Text(text = "hrudhaykanth116", style = MaterialTheme.typography.body2)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Switch accounts"
                    )
                }
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO: 11/09/21 max width
                ProfileImage("hrudhaykanth116")
                ValueUnitView(
                    "Liked",
                    "8"
                )
                ValueUnitView(
                    "Favourite",
                    "14"
                )
                ValueUnitView(
                    "Watchlist",
                    "28"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            StoriesView(storiesList)
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Viewpager
        }

        Button(
            onClick = { onLogoutButtonClicked(context) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Sign out")
        }
    }
}

fun onLogoutButtonClicked(context: Context) {
    ToastHelper.showErrorToast(context, msg = "yet to be implemented.")
    // val auth = Firebase.auth
    // auth.signOut()
    // activity?.finishAffinity()
    // val signInIntent = Intent(context, SignInActivity::class.java)
    // signInIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
    // startActivity(signInIntent)
}
