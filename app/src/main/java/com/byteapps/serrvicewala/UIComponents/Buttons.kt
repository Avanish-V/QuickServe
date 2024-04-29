package com.byteapps.serrvicewala.UIComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byteapps.QuickServe.R

@Composable
fun SmallButton(
    text: String,
    textColor: Color,
    color: Color,
    onClick: () -> Unit
) {

    ElevatedButton(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .height(36.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(text = text, color = textColor)
    }

}

@Composable
fun FullWidthButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    color: Color,
    enabled:Boolean,
    onClick: () -> Unit
) {

    Box(modifier = Modifier.fillMaxWidth()){
        ElevatedButton(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = color
            ),
            modifier = modifier,
            shape = RoundedCornerShape(6.dp),
            enabled = enabled
        ) {
            Text(text = text, color = textColor)
        }
    }

}

@Composable
fun FullWidthWithPriceButton(
    buttonText: String,
    priceText: String,
    discount: Int?,
    textColor: Color,
    color: Color,
    onClick: () -> Unit
) {


    Column (
        Modifier
            .fillMaxWidth()
            .padding(top = 13.dp)){

        Row (
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)){
            Row (Modifier.weight(1f)){
                Text(text = "To Pay ", style = MaterialTheme.typography.displayMedium,color = MaterialTheme.colorScheme.onPrimary)
                Text(text = "(Incl. all taxes and charges)", fontSize = 12.sp,color = MaterialTheme.colorScheme.onSecondary)

            }
            Text(text = priceText, style = MaterialTheme.typography.displayMedium,color = MaterialTheme.colorScheme.onPrimary)
        }



            AnimatedVisibility(visible = discount != 0) {
                Spacer(modifier = Modifier.height(2.dp))

                Row (
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    Image(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(id = R.drawable.ticket),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                    Text(text = "You saved $discount", style = MaterialTheme.typography.displaySmall,color = MaterialTheme.colorScheme.secondary)

                }
            }





        ElevatedButton(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = color
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(text = buttonText, color = textColor)
        }


    }


}



@Composable
fun SmallCardButton(icon: Int, iconTint: Color, onClick: () -> Unit,status:Boolean) {

    Card(
        onClick = {onClick() },
        modifier = Modifier.size(32.dp),
        shape = RoundedCornerShape(5.dp),
        enabled = status,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Icon(painter = painterResource(id = icon), contentDescription = null, tint = iconTint)
        }
    }
}
