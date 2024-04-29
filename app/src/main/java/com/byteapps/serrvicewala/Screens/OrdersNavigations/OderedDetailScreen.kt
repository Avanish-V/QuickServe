package com.byteapps.serrvicewala.Screens.OrdersNavigations

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Features.Orders.OrdersViewModel
import com.byteapps.serrvicewala.Features.Orders.data.OrdersDataModel
import com.byteapps.serrvicewala.Features.Orders.data.Status
import com.byteapps.serrvicewala.Features.Orders.data.Tracking
import com.byteapps.serrvicewala.Features.PaymentGetway.Presentation.StartPaymentViewModel
import com.byteapps.serrvicewala.Features.PriceCalculation.PriceDetails
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Coupon
import com.byteapps.serrvicewala.SharedViewModel.ShareOrderDetailsViewModel
import com.byteapps.serrvicewala.UIComponents.MyAlertDialog
import com.byteapps.serrvicewala.UIComponents.SmallButton
import com.byteapps.serrvicewala.UIComponents.SmallCardButton
import com.byteapps.serrvicewala.Utils.NavigationRoutes.OrderNavRoutes
import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderedDetailsScreen(
    navHostController: NavHostController,
    orderDetailsViewModel: ShareOrderDetailsViewModel,
    ordersViewModel: OrdersViewModel,
    paymentViewModel: StartPaymentViewModel
) {

    val orderData = orderDetailsViewModel.orderDetails.collectAsState().value

    LaunchedEffect(Unit){

        paymentViewModel.fetchOrderStatus(orderData?.orderId.toString())
    }
    val transactionStatus = paymentViewModel.orderStatusResult.collectAsState().value

    Log.d("TRANSACTION_STATUS", transactionStatus.transactionStatus?.orderStatus ?: "")

    val scope = rememberCoroutineScope()
    var isAlert by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Order Details") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                )
            )

        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(13.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            item {

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

                    Row(
                        Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Order ID ${orderData?.orderId}",
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }

                    if (orderData != null) {
                        Button(
                            onClick = { isAlert = true },
                            enabled = orderData.tracking != Tracking.COMPLETED,
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = Color.White
                            )

                        ) {
                            Text(text = if (orderData.status == Status.ACTIVE) "Cancel" else "Canceled")
                        }
                    }


                }


            }

            item { HorizontalDivider() }

            item {


                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                        Icon(

                            painter = painterResource(id = R.drawable.memo_pad),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary,

                            )

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Order details",
                                style = MaterialTheme.typography.displayMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            orderData?.serviceInfo?.let {
                                Text(
                                    text = it.serviceTitle,
                                    style = MaterialTheme.typography.displaySmall,
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }

                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                        Icon(
                            painter = painterResource(id = R.drawable.track),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                text = "Address",
                                style = MaterialTheme.typography.displayMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "${orderData?.address?.name}\n" +
                                        "${orderData?.address?.mobile}\n" +
                                        "${orderData?.address?.building}, ${orderData?.address?.area}\n" +
                                        "${orderData?.address?.pinCode} ${orderData?.address?.city}\n" +
                                        "${orderData?.address?.state}",
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )

                        }


                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                        Icon(
                            painter = painterResource(id = R.drawable.clock_five),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                            Text(
                                text = "Arrival",
                                style = MaterialTheme.typography.displayMedium,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "${orderData?.dateTime?.time}, ${orderData?.dateTime?.date}",
                                style = MaterialTheme.typography.displaySmall,
                                color = MaterialTheme.colorScheme.onSecondary
                            )

                        }


                    }
                }


            }

            item { HorizontalDivider() }

            item {


                orderData?.tracking?.let {
                    TrackingStatus(
                        ordersData = orderData,
                        tracking = it.name,
                        onCompletedClick = {

                            if (orderData.tracking == Tracking.COMPLETED){
                                navHostController.navigate(OrderNavRoutes.TAKE_REVIEW)
                            }

                        }
                    )
                }


            }

            item { HorizontalDivider() }

            item {

                PriceTagOrderDetails(
                    icon = R.drawable.receipt,
                    priceDetails  = orderData?.priceTag?.let {
                        PriceDetails(
                            price = it.price,
                            tax = orderData.priceTag.tax,
                            quantity = orderData.priceTag.quantity,
                            total = orderData.priceTag.total,
                            coupon = Coupon(
                                offerTitle = orderData.priceTag.coupon?.offerTitle.toString(),
                                couponCode = orderData.priceTag.coupon?.couponCode.toString(),
                                discount = orderData.priceTag.coupon?.discount?.toInt() ?: 0
                            )

                        )
                    }
                )
            }


        }

        if (isAlert) {

            var isLoading by remember {
                mutableStateOf(false)
            }

            MyAlertDialog(
                onOuterClick = {
                    isAlert = false
                },
                onDismissClick = {
                    isAlert = false
                },
                onConfirmClick = {

                    scope.launch {
                        ordersViewModel.cancelOrder(
                            orderId = orderData?.orderId.toString(),
                            status = Status.CANCELED
                        ).collect {
                            when (it) {
                                is ResultState.Loading -> {
                                    isLoading = true
                                }

                                is ResultState.Success -> {

                                    orderDetailsViewModel._orderDetails.value!!.status = Status.CANCELED
                                    isAlert = false
                                }

                                is ResultState.Error -> {
                                    isLoading = false
                                }
                            }
                        }
                    }
                },
                isLoading = isLoading
            )

        }


    }

}

@Composable
fun TrackingSingleStatus(
    icon: Int,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(42.dp)
            .background(
                color = containerColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(

            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .clickable { onClick() },
            tint = contentColor,

        )
    }
}



@Composable
fun TrackingStatus(ordersData:OrdersDataModel,tracking:String,onCompletedClick:()->Unit) {


    if (tracking == "PLACED"){

        Column {


            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.memo_pad, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {}
                    VerticalDivider(modifier = Modifier
                        .height(50.dp)
                        .width(5.dp))

                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Placed")

                }

            }

            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.track, containerColor = MaterialTheme.colorScheme.onTertiary, contentColor = Color.Gray) {}
                    VerticalDivider(modifier = Modifier.height(50.dp))

                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Assigned")

                }

            }

            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.star, containerColor = MaterialTheme.colorScheme.onTertiary, contentColor = Color.Gray) {onCompletedClick()}


                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Completed")

                }

            }
        }

    }
    else if (tracking == "ASSIGNED"){

        Column {


            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.memo_pad, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {}
                    VerticalDivider(modifier = Modifier.height(50.dp), color = MaterialTheme.colorScheme.secondary)

                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Placed")

                }

            }

            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.track, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {}
                    VerticalDivider(modifier = Modifier.height(62.dp))

                }
                Column (Modifier.padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Column(Modifier.height(42.dp), verticalArrangement = Arrangement.Center) {

                        Text(text = "Assigned")

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

            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.star, containerColor = MaterialTheme.colorScheme.onTertiary, contentColor = Color.Gray) {}


                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Completed")

                }

            }
        }
    }
    else if (tracking == "COMPLETED"){

        Column {


            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.memo_pad, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {}
                    VerticalDivider(modifier = Modifier.height(50.dp), color = MaterialTheme.colorScheme.secondary)

                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Placed")

                }

            }

            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.track, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {}
                    VerticalDivider(modifier = Modifier.height(50.dp), color = MaterialTheme.colorScheme.secondary)

                }
                Column (Modifier.padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Column(Modifier.height(42.dp), verticalArrangement = Arrangement.Center) {

                        Text(text = "Assigned")

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

            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.star, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {onCompletedClick()}


                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Completed")

                }

            }
        }
    }




}

@Composable
fun RefundStatus(refundStatus:String,onCompletedClick:()->Unit) {


    if (refundStatus == "INITIATED"){

        Column {


            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.memo_pad, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {}
                    VerticalDivider(modifier = Modifier
                        .height(50.dp)
                        .width(5.dp))

                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Refund Initiated")

                }

            }

            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.track, containerColor = MaterialTheme.colorScheme.onTertiary, contentColor = Color.Gray) {}
                    VerticalDivider(modifier = Modifier.height(50.dp))

                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Refunded")

                }

            }


        }

    }
    else if (refundStatus == "REFUNDED"){

        Column {


            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.memo_pad, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {}
                    VerticalDivider(modifier = Modifier.height(50.dp), color = MaterialTheme.colorScheme.secondary)

                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Refund Initiated")

                }

            }

            Row {

                Column (horizontalAlignment = Alignment.CenterHorizontally){

                    TrackingSingleStatus(icon = R.drawable.track, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {}
                    VerticalDivider(modifier = Modifier.height(50.dp))

                }
                Column (
                    Modifier
                        .height(42.dp)
                        .padding(start = 12.dp), verticalArrangement = Arrangement.Center){

                    Text(text = "Refunded")

                }

            }

        }
    }





}

@Composable
fun PriceTagOrderDetails(icon: Int, priceDetails: PriceDetails?) {

    Row(
        Modifier
            .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Price Details",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Price",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = "₹${priceDetails?.price}",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Tax & Fee",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = "₹${priceDetails?.tax}",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

            if (priceDetails?.coupon?.discount != 0){
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Promo-${priceDetails?.coupon?.couponCode}",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "-₹${priceDetails?.coupon?.discount}",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }


            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Total ${priceDetails?.quantity}",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "₹${priceDetails?.total}",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }
}



