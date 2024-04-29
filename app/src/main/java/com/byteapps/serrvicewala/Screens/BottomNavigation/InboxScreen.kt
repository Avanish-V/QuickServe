package com.byteapps.serrvicewala.Screens.BottomNavigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Features.Offers.OfferDataModel

import com.byteapps.serrvicewala.Features.Offers.OffersViewModel
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.UIComponents.StatusScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(offersViewModel: OffersViewModel) {

    val offerResult = offersViewModel.offerResult.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Inbox") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                )
            )

        }
    ) { paddingValues ->

        when {
            offerResult.isLoading -> {

                LoadingScreen()

            }
            offerResult.offerList.isEmpty() -> {

                StatusScreen(text = "No offers available")
            }
            offerResult.error.isNotEmpty() -> {

                StatusScreen(text = "Something went wrong!")
            }
            else -> {

                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(13.dp),
                    verticalArrangement = Arrangement.spacedBy(13.dp)

                ){
                    items(offerResult.offerList, key = {offerId->offerId.offerId.toString()}){ item->
                        NotificationCard(offer = item){

                        }
                    }
                }


            }
        }


    }
}


@Composable
fun NotificationCard(offer:OfferDataModel, onClick:()->Unit) {

    val click by remember {
        mutableStateOf(onClick())
    }

    Card(
        onClick = {  },
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            2.dp,
            MaterialTheme.colorScheme.onTertiary
        ),
        shape = RoundedCornerShape(8.dp)


    ) {

        Row (modifier = Modifier.fillMaxWidth()){

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = MaterialTheme.colorScheme.onTertiary),
                contentAlignment = Alignment.Center
            ){

                AsyncImage(
                    model  = offer.offerImage,
                    contentDescription = null,
                    modifier = Modifier.size(62.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)

                )
            }

            Column (
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ){

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = offer.offerTitle,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = offer.offerExp,
                        color = Color.Gray,
                        style = MaterialTheme.typography.displaySmall
                    )
                }

                Row (
                    modifier = Modifier
                        .padding(
                            horizontal = 10.dp
                        )
                ){

                    SpannableText(
                        title = offer.offerTitle,
                        code = offer.offerCode
                    )
                }

            }
        }

    }

}

@Composable
fun SpannableText(

    title:String,
    code:String

) {
    val annotatedString = buildAnnotatedString {

        append(title)

        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Black)
        ) {
            append(" $code ")
        }

        append("promo code")
    }

    Text(
        text = annotatedString,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Stable
data class OffersDataClass(

    val offerId : String ?= "",
    val discount : Int ,
    val offerTitle : String ?= "",
    val offerDescription : String ?= "",
    val time : String ?=""



)
val offersList = listOf<OffersDataClass>(

    OffersDataClass("FREE10",10,"10% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE20",20,"00% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE30",30,"30% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE40",40,"40% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE50",50,"50% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE60",60,"60% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE70",70,"70% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE80",80,"80% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE90",90,"90% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE100",100,"100% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE102",102,"102% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE103",103,"103% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE104",104,"104% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE105",105,"105% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE106",106,"106% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE107",107,"107% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE108",108,"108% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE109",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE110",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE111",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE112",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE113",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE114",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE115",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE116",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE117",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE118",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE119",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE120",109,"109% cashback","save money on house cleaning using","10 May, 23"),
    OffersDataClass("FREE121",109,"109% cashback","save money on house cleaning using","10 May, 23"),


    )
