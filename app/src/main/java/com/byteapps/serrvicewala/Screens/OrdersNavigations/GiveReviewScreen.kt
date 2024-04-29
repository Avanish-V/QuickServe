package com.byteapps.serrvicewala.Screens.OrdersNavigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Features.Orders.OrdersViewModel
import com.byteapps.serrvicewala.Features.Orders.data.ReviewDataModel
import com.byteapps.serrvicewala.SharedViewModel.ShareOrderDetailsViewModel
import com.byteapps.serrvicewala.UIComponents.FullWidthButton
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.UIComponents.getCurrentTimeDate
import com.byteapps.serrvicewala.Utils.NavigationRoutes.OrderNavRoutes
import com.byteapps.serrvicewala.Utils.ResultState
import com.byteapps.serrvicewala.Validations.ReviewFormValidationViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakeReviewScreen(
    navHostController: NavHostController,
    reviewFormValidationViewModel: ReviewFormValidationViewModel,
    shareOrderDetailsViewModel: ShareOrderDetailsViewModel,
    ordersViewModel: OrdersViewModel
) {

    val reviewComment = reviewFormValidationViewModel.reviewText1.collectAsState().value
    val reviewCount = reviewFormValidationViewModel.reviewCount.collectAsState().value
    val orderDetails = shareOrderDetailsViewModel.orderDetails.collectAsState().value

    val scope = rememberCoroutineScope()

    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Review") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "" )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                )
            )

        }
    ) { paddingValues ->

        Column(Modifier.padding(paddingValues)) {

            Column(
                Modifier
                    .weight(1f)
                    .padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {


                AsyncImage(
                    model ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2FLOZDRrgvGqiKb0GeURpn9WAnldE_BlUNjadsTuRTQ&s" ,
                    contentDescription = "",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),

                    contentScale = ContentScale.FillBounds

                )

                if (orderDetails != null) {
                    Text(text = orderDetails.address.name, style = MaterialTheme.typography.displayMedium)
                }

                Text(text = "Let's rate your professional.", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onSecondary)

               RatingView(onSelected = {
                   reviewFormValidationViewModel._reviewCount.value = it
               })

                OutlinedTextField(
                    value = reviewComment,
                    onValueChange = {reviewFormValidationViewModel._reviewText.value  = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    placeholder = {
                        Text(
                            text = "Tell people about your professional",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.displaySmall
                        )
                    },

                )
            }

            FullWidthButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp),
                text = "Submit",
                textColor = Color.White,
                color = MaterialTheme.colorScheme.primary,
                enabled = reviewFormValidationViewModel.isValidated(reviewComment,reviewCount),
                onClick = {
                    scope.launch {

                        ordersViewModel.setReview(
                            ReviewDataModel(
                                professionalId = "235689",
                                serviceId = orderDetails?.serviceInfo?.serviceId ?: "",
                                orderId = orderDetails?.orderId.toString(),
                                comment = reviewComment,
                                rating = reviewCount,
                                date = getCurrentTimeDate(),
                                reviewBy = orderDetails?.address?.name ?: ""
                            )
                        ).collect{
                            when(it){
                                is ResultState.Loading->{
                                    isLoading = true
                                }
                                is ResultState.Success->{
                                    navHostController.navigate(OrderNavRoutes.ORDERED_DETAIL)
                                }
                                is ResultState.Error->{

                                }
                            }
                        }
                    }
                }
            ) 
        }
        if (isLoading) LoadingScreen()
    }
}
@Composable
fun RatingView(modifier: Modifier = Modifier,onSelected :(Int) ->Unit) {


    var ratingState by remember {
        mutableStateOf(0)
    }


    Row (modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){

        for (index in 1 .. 5){

            Icon(
                painter = if (index <= ratingState) painterResource(id = R.drawable.star__1_) else painterResource(id = R.drawable.star),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .padding(horizontal = 3.dp)
                    .clickable {
                        onSelected(index)
                        ratingState = index

                    },
                tint = MaterialTheme.colorScheme.secondary

            )

        }
    }



}