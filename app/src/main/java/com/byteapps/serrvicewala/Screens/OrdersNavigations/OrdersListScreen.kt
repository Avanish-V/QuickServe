package com.byteapps.serrvicewala.Screens.OrdersNavigations

import android.media.Rating
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Features.Orders.OrdersViewModel
import com.byteapps.serrvicewala.Features.Orders.data.OrdersDataModel
import com.byteapps.serrvicewala.Features.Orders.data.Status
import com.byteapps.serrvicewala.Features.Orders.data.Tracking
import com.byteapps.serrvicewala.SharedViewModel.ShareOrderDetailsViewModel
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.UIComponents.RatingReviewsBar
import com.byteapps.serrvicewala.UIComponents.StatusScreen
import com.byteapps.serrvicewala.Utils.NavigationRoutes.OrderNavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(
    navHostController: NavHostController,
    ordersViewModel: OrdersViewModel,
    shareOrderDetailsViewModel: ShareOrderDetailsViewModel
) {

    LaunchedEffect(Unit){
        ordersViewModel.getOrdersList()
    }
    val ordersData = ordersViewModel.orderResult.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Orders") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                )
            )

        }
   ) {paddingValues ->

        if (ordersData.isLoading){
            LoadingScreen()
        }
        else if (ordersData.ordersList.isEmpty()){
            StatusScreen(text = "No Orders")
        }
        else if (ordersData.error.isNotEmpty()){

            StatusScreen(text = "Something went wrong!")
        }else{

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(13.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ){

                items(ordersData.ordersList){

                    OrdersSingleCard(
                        ordersData = it,
                        onclick = {
                            shareOrderDetailsViewModel.setOrderDetailsData(it)
                            navHostController.navigate(OrderNavRoutes.ORDERED_DETAIL)
                        }
                    )

                }

            }
        }


    }

}


@Composable
fun OrdersSingleCard(ordersData: OrdersDataModel, onclick:()->Unit) {

    Column (
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onTertiary,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.onTertiary,
                shape = RoundedCornerShape(10.dp)
            )

            .clickable {
                onclick()
            }
    ){

        if (ordersData.status == Status.ACTIVE && ordersData.tracking == Tracking.ASSIGNED){
            SingleStatus(color = MaterialTheme.colorScheme.secondary, title = "Assigned")
        }
        else if (ordersData.status == Status.ACTIVE && ordersData.tracking == Tracking.COMPLETED){
        SingleStatus(color = MaterialTheme.colorScheme.primary, title = "Completed")
         }
        else if (ordersData.status == Status.ACTIVE){
            SingleStatus(color = MaterialTheme.colorScheme.secondary, title = "Order Placed")
        }
        else if (ordersData.status == Status.CANCELED){
            SingleStatus(color = MaterialTheme.colorScheme.error, title = "Canceled! Refund is processing. ")
        }
        else if (ordersData.status == Status.REFUNDED){
            SingleStatus(color = MaterialTheme.colorScheme.tertiary, title = "Refunded")

        }



        Row (Modifier.padding(13.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)){


            Image(
                modifier = Modifier.size(42.dp),
                painter = painterResource(id = R.drawable.ac),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )

            Column (verticalArrangement = Arrangement.spacedBy(10.dp)){

                Row(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp),
                        text = ordersData.serviceInfo.serviceTitle,
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "₹${ordersData.priceTag.total}",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                }
                Column {

                    Text(
                        text = "${ordersData.dateTime.time}, ${ordersData.dateTime.date}",
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.displaySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Order Id ${ordersData.orderId}",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.displaySmall
                    )

                }

            }

        }



                AssignedProfessional(
                    profName = ordersData.professionalDetail?.professionalName ?: "",
                    profImage = ordersData.professionalDetail?.professionalImage ?: "",
                    profId = ordersData.professionalID,
                    rating = ordersData.professionalDetail?.rating.toString(),
                    count = ordersData.professionalDetail?.count.toString()
                )







    }


}


@Composable
fun AssignedProfessional(profName:String,profImage:String,profId:String,rating: String,count:String) {


    if (profId != ""){

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(42.dp),
                model = profImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column (modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)){
                Text(text = profName, style = MaterialTheme.typography.displayMedium)
                if (count >= "50"){
                    RatingReviewsBar(rating = rating, reviewsCount = count)
                }else{
                    Text(text = "ID:$profId", style = MaterialTheme.typography.displaySmall)
                }

            }
            IconButton(onClick = {  }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.phone_call),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
    }






}

@Composable
fun SingleStatus(color: Color,title:String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            color = Color.White
        )
    }

}