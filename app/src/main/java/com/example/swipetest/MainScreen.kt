package com.example.swipetest

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun MainScreen(
    pagerState: PagerState,
    coverItems: List<CoverItem>
) {
    HorizontalPager(
        state = pagerState,
        count = Int.MAX_VALUE,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) { index ->
        val page = index.floorMod(coverItems.size)

        if (coverItems.isNotEmpty()) {
            val item = coverItems[page]

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                Log.d("__DEBUG__", "ACTION_DOWN")
                            }
                            MotionEvent.ACTION_CANCEL -> {
                                Log.d("__DEBUG__", "ACTION_CANCEL")
                            }
                            MotionEvent.ACTION_UP -> {
                                Log.d("__DEBUG__", "ACTION_UP")
                            }
                            MotionEvent.ACTION_MOVE -> {
                                Log.d("__DEBUG__", "ACTION_MOVE")
                            }
                            else -> {
                                return@pointerInteropFilter false
                            }
                        }
                        true
                    }
                    .graphicsLayer {
                        if (pagerState.pageCount > 2) {
                            val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue

                            lerp(
                                ScaleFactor(0.9f, 0.9f),
                                ScaleFactor(1f, 1f),
                                1f - pageOffset.coerceIn(0f, 1f)
                            ).also { scaleFactor ->
                                scaleX = scaleFactor.scaleX
                                scaleY = scaleFactor.scaleY
                            }
                        }
                    }
            ) {
                val (image, gradient, coverInfo) = createRefs()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(R.color.purple_200))
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                        }
                )

                // Gradient
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.black).copy(alpha = 0.01f),
                                    colorResource(id = R.color.black)
                                )
                            )
                        )
                        .constrainAs(gradient) {
                            bottom.linkTo(image.bottom)
                        }
                )

                Text(
                    text = item.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.white),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(coverInfo) {
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }
    }
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}