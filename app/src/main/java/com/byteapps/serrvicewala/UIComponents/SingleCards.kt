package com.byteapps.serrvicewala.UIComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Features.Crousel.CarouselDataModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.ServiceProductDataModel
import com.byteapps.serrvicewala.ui.theme.LightBlue


@Composable()
fun Promotions(modifier: Modifier = Modifier,carouselData: CarouselDataModel,onButtonClick:()->Unit) {

    Card (
        modifier = Modifier.height(150.dp).width(240.dp),
        shape = RoundedCornerShape(10.dp)

        ){
        Box(
            modifier = Modifier.fillMaxSize()

        ){

            AsyncImage(
                model = carouselData.carouselImage,
                contentDescription =null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()

            )

            Box(
                modifier = Modifier

                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            0.5f to LightBlue,
                            1f to Color.Transparent
                        )
                    )

            ){

                Column (modifier.padding(horizontal = 13.dp, vertical = 20.dp)){

                    Text(
                        text = carouselData.carouselTitle,
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(
                        text = carouselData.carouselSubTitle,
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    ElevatedButton(
                        onClick = {
                           onButtonClick()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(32.dp)
                            .alpha(0.9f),
                        shape = RoundedCornerShape(
                            5.dp
                        )
                    ) {
                        Text(
                            text = "Book now",
                            color = Color.White,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ServiceCategoryCard(modifier: Modifier = Modifier,icon: String,title: String,onClick :()->Unit) {


    Column (horizontalAlignment = Alignment.CenterHorizontally,){
        Card(
            onClick = {onClick()},
            modifier = Modifier.size(74.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onTertiary),

        ) {

            Box(
                modifier = Modifier.fillMaxSize()
                , contentAlignment = Alignment.Center
            ){
                AsyncImage(
                    model = icon,
                    contentDescription = "",
                    modifier.size(54.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )

            }
        }
        Spacer(modifier = Modifier.padding( 4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center

        )

    }

}

@Composable
fun RecommendedServicesCard(modifier: Modifier= Modifier,navHostController: NavHostController) {

    
    Card(
        onClick = {

        },
        modifier = Modifier
            .width(200.dp)
            .height(220.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onTertiary)

    ) {

        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {

            Image(
                painter = painterResource(id = R.drawable._0260400_4389639),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.FillWidth
            )
            Column(
                Modifier
                    .padding(10.dp)
                    .weight(1f)) {

                Text(
                    text = "Ac deep clean Service",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                RatingReviewsBar(rating = "4.7", reviewsCount = "100")

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "₹399",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayMedium
                )
            }



        }

    }
}

@Composable
fun RatingReviewsBar(rating: String,reviewsCount: String) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.star__1_),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = rating,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "($reviewsCount Reviews)",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp
        )
    }

}

@Composable
fun HorizontalProductCard(serviceProductDataModel: ServiceProductDataModel, onClick: () -> Unit) {

    Card(
        onClick = { onClick() },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(0.1.dp, Color.LightGray)

    ) {

        Row(modifier = Modifier.fillMaxSize()) {


            AsyncImage(
                model = serviceProductDataModel.images.get(0),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                contentScale = ContentScale.Crop

            )
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 10.dp, top = 10.dp)) {

                Text(
                    text = serviceProductDataModel.serviceTitle,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.padding(2.dp))

                RatingReviewsBar(
                    rating = "4.7",
                    reviewsCount = "100"
                )

                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = "₹${serviceProductDataModel.price}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayMedium,

                )


                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(13.dp),
                    horizontalArrangement = Arrangement.End
                ){

                    SmallButton(
                        text = "Book",
                        textColor = Color.White,
                        color = MaterialTheme.colorScheme.primary,
                        onClick = {
                            onClick()
                        }
                    )



                }
            }


        }

    }
}
