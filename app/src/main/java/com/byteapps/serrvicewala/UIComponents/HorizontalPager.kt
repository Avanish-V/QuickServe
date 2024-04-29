package com.byteapps.serrvicewala.UIComponents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerScreen(items : List<String>,onBackClick:()->Unit) {

        val pagerState = rememberPagerState(pageCount = {items.size})
        val coroutineScope = rememberCoroutineScope()

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
            contentAlignment = Alignment.BottomCenter
        ){

            HorizontalPager(

                state = pagerState,
                modifier = Modifier.fillMaxSize()

            ) { currentPage ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    AsyncImage(
                        model = items[currentPage],
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )

                }
            }

            IconButton(
                onClick = { onBackClick()},
                modifier = Modifier
                    .padding(15.dp)
                    .align(alignment = Alignment.TopStart)
                    .alpha(0.8f),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            HorizontalTabs(items = items, pagerState = pagerState, scope = coroutineScope)

        }

 }


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalTabs(
    items: List<String>,
    pagerState: PagerState,
    scope: CoroutineScope
) {

    Row(modifier = Modifier.padding(bottom = 16.dp)) {

        items.forEachIndexed { index, horizontalPagerContent ->

        IndicatorCard(index,pagerState)

        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IndicatorCard(index:Int,pagerState: PagerState) {

    Card (modifier = Modifier
        .size(10.dp)
        .padding(), shape = CircleShape, colors = CardDefaults.cardColors(
        containerColor = if (index == pagerState.currentPage) MaterialTheme.colorScheme.primary else Color.White
    )

    ){


    }
    Spacer(modifier = Modifier.padding(2.dp))

}