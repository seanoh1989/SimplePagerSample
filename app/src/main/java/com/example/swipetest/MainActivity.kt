package com.example.swipetest

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@ExperimentalComposeUiApi
@ExperimentalPagerApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                pagerState = rememberPagerState(),
                coverItems = listOf(
                    CoverItem("First Item"),
                    CoverItem("Second Item"),
                    CoverItem("Third Item")
                )
            )
        }
    }
}