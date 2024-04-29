package com.byteapps.serrvicewala.Screens.ProceedOrderNavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Features.Reviews.ServiceReviewsViewModel
import com.byteapps.serrvicewala.Features.Reviews.data.GetServiceReviewsDataModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Description
import com.byteapps.serrvicewala.SharedViewModel.ShareServiceDetailViewModel
import com.byteapps.serrvicewala.UIComponents.FullWidthButton
import com.byteapps.serrvicewala.UIComponents.HorizontalPagerScreen
import com.byteapps.serrvicewala.UIComponents.RatingReviewsBar
import com.byteapps.serrvicewala.UIComponents.ShowRating
import com.byteapps.serrvicewala.Utils.NavigationRoutes.HomeNavRoutes
import com.byteapps.serrvicewala.ui.theme.secondary_color

@Composable

fun ProductDetailsScreen(
    navHostController: NavHostController,
    shareServiceDetailViewModel: ShareServiceDetailViewModel,
    serviceReviewsViewModel: ServiceReviewsViewModel
) {

    val data = shareServiceDetailViewModel.serviceDetails.value
    val reviewsData = serviceReviewsViewModel.reviewsResults.value

    LaunchedEffect(data.serviceId){
        serviceReviewsViewModel.getServiceReviews(data.serviceId)
    }

    Column (Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween){

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ){

            item {
                HorizontalPagerScreen( items = data.images, onBackClick = {navHostController.popBackStack()})
            }

            item {

                ServiceInformationSection(
                    title =data.serviceTitle ,
                    price = data.price.toString(),
                    offerTitle = ""
                )
            }

            item {
                FeaturesSection()
            }

            item {
                ServiceSteps(stepsList = data.description)
            }

            item {
                Text(
                    text = "Rating & Reviews",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            items(reviewsData.serviceReviewsList){
                ShowReviewsSection(it)
            }

        }

        FullWidthButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            text = "Proceed to book",
            textColor = Color.White,
            color = MaterialTheme.colorScheme.primary,
            enabled = true
        ) {

            navHostController.navigate(HomeNavRoutes.PROCEED_ORDER_DETAIL)

        }

    }

    
}

@Composable
fun ServiceSteps(stepsList:List<Description>) {

    var stepsRange by remember { mutableStateOf(0f..100f) }


    stepsList.forEach {steps->

        Row (
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ){

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primary
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,

                    )
                }
                VerticalDivider(
                    modifier = Modifier.wrapContentHeight()
                )
            }

            Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
                Text(text = steps.title, fontWeight = FontWeight.SemiBold)
                Text(text = steps.comment, style = MaterialTheme.typography.displaySmall)
            }
        }

    }

}

@Composable
fun ServiceInformationSection(
    title:String,
    price:String,
    offerTitle:String
) {

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.padding(2.dp))

        RatingReviewsBar(rating = "3.9", reviewsCount = "100")

        Spacer(modifier = Modifier.padding(5.dp))

        SpannablePriceAndDiscount(price = "₹$price", discount = offerTitle)


    }


}

@Composable
fun SpannablePriceAndDiscount(price: String, discount: String) {
    val annotatedString = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Black
            )
        ) {
            append("$price")
        }

        append(" ($discount)")
    }

    Text(
        text = annotatedString,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun FeaturesSection() {
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {


        Box(modifier = Modifier
            .height(100.dp)
            .width(90.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(secondary_color),
            contentAlignment = Alignment.Center
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.moped) ,
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "On time\nDelivery",
                    style = MaterialTheme.typography.displaySmall
                )
            }

        }
        Box(modifier = Modifier
            .height(100.dp)
            .width(90.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(secondary_color),
            contentAlignment = Alignment.Center
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.features_alt) ,
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "30 days\nWarranty",
                    style = MaterialTheme.typography.displaySmall
                )
            }

        }
        Box(modifier = Modifier
            .height(100.dp)
            .width(90.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(secondary_color),
            contentAlignment = Alignment.Center
        ){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceEvenly,

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.refund_alt) ,
                    contentDescription = null,
                    modifier = Modifier.size(26.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Instant\nRefund",
                    style = MaterialTheme.typography.displaySmall
                )
            }

        }
    }
}

@Composable
fun ShowReviewsSection(reviewsData: GetServiceReviewsDataModel) {

    val profession_review = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Black
            )
        ) {
            append("")
        }

        append(reviewsData.comment)

    }

    val name_date = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold,
                // fontSize = WebSettings.TextSize.medium
            )
        ) {
            append(reviewsData.reviewBy)
        }

        append(" ●  ${reviewsData.date}")
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 15.dp, end = 15.dp),
    ) {


        Box(

            modifier = Modifier
                .size(48.dp)
                .clip(shape = CircleShape)
                .background(color = MaterialTheme.colorScheme.onTertiary),
            contentAlignment = Alignment.Center

        ){
            Text(
                text = reviewsData.reviewBy.get(0).toString(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 20.dp),
        ) {

            Text(
                text = name_date,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.padding(1.dp))

            ShowRating(index = reviewsData.rating)
            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = profession_review,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary,
                overflow = TextOverflow.Ellipsis,

            )

        }
    }

}

