package com.byteapps.serrvicewala.Screens.ProceedOrderNavigation

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Address.AddressViewModel
import com.byteapps.serrvicewala.Address.UserAddressDataModel
import com.byteapps.serrvicewala.Features.Offers.OfferDataModel
import com.byteapps.serrvicewala.Features.Offers.OffersViewModel
import com.byteapps.serrvicewala.LocationPermission.PermissionDialog
import com.byteapps.serrvicewala.LocationPermission.locationPermissionText
import com.byteapps.serrvicewala.Features.PaymentGetway.Presentation.CashFreePayment
import com.byteapps.serrvicewala.Features.PriceCalculation.PriceCalculationViewModel
import com.byteapps.serrvicewala.Features.PriceCalculation.PriceDetails
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Coupon
import com.byteapps.serrvicewala.SharedViewModel.ShareProceedOrderDetailsViewModel

import com.byteapps.serrvicewala.SharedViewModel.ShareServiceDetailViewModel
import com.byteapps.serrvicewala.UIComponents.CommonInputField
import com.byteapps.serrvicewala.UIComponents.DateDataModel
import com.byteapps.serrvicewala.UIComponents.FullWidthButton
import com.byteapps.serrvicewala.UIComponents.FullWidthWithPriceButton
import com.byteapps.serrvicewala.UIComponents.SmallCardButton
import com.byteapps.serrvicewala.UIComponents.SpannableOfferText
import com.byteapps.serrvicewala.UIComponents.generateCurrentMonthDates
import com.byteapps.serrvicewala.UIComponents.timeList
import com.byteapps.serrvicewala.Utils.NavigationRoutes.HomeNavRoutes
import com.byteapps.wiseschool.GeoFencing.GeoLocation.GeoLocationViewModel
import com.byteapps.wiseschool.GeoFencing.Permission.PermissionViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProceedOrderScreen(
    shareServiceDetailViewModel: ShareServiceDetailViewModel,
    shareOrderDetailsViewModel: ShareProceedOrderDetailsViewModel,
    permissionViewModel: PermissionViewModel,
    geoLocationViewModel: GeoLocationViewModel,
    addressViewModel: AddressViewModel,
    offersViewModel: OffersViewModel,
    priceCalculationViewModel: PriceCalculationViewModel,
    navHostController: NavHostController,

    ) {

    val serviceDetailsData = shareServiceDetailViewModel.serviceDetails.value
    val calculatedPrice = priceCalculationViewModel.priceWithCoupon.collectAsState().value.calculatedPriceWithCoupon
    val address = shareOrderDetailsViewModel.address.collectAsState().value
    val time = shareOrderDetailsViewModel.time.collectAsState().value
    val date = shareOrderDetailsViewModel.date.collectAsState().value
    val quantity = shareOrderDetailsViewModel.quantity.collectAsState().value
    val offerResult = offersViewModel.offerResultById.value

    LaunchedEffect(serviceDetailsData.serviceId){
        offersViewModel.getOffersResultById(serviceDetailsData.serviceTAG)
    }

    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            density = LocalDensity.current,
            skipHiddenState = false
        )
    )

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var contentOption by rememberSaveable {
        mutableIntStateOf(0)
    }



    LaunchedEffect(quantity) {
        priceCalculationViewModel.setPriceTagWithCoupon(
            PriceDetails(
                price = serviceDetailsData.price,
                tax = serviceDetailsData.tax,
                total = 0,
                quantity = quantity,
                coupon = Coupon(
                    offerTitle = "",
                    couponCode = "",
                    discount = 0

                )
            )
        )
    }

    val userAddressDataItems = addressViewModel.addressData.collectAsState().value

    val dialogQueue = permissionViewModel.visiblePermissionDialogQueue

    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionViewModel.onPermissionResult(
                permission = Manifest.permission.ACCESS_FINE_LOCATION,
                isGrant = isGranted,
                context = context
            )
            if (isGranted) {

                geoLocationViewModel.startLocationUpdate(context)

            }

        }
    )



    dialogQueue.forEach { permission ->

        PermissionDialog(
            permissionTextProvider = when (permission) {
                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    locationPermissionText()
                }

                else -> return@forEach
            },
            isPermanentDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                permission
            ),
            onDismiss = {
                permissionViewModel.dismissDialog()
            },
            onOkClick = {
                permissionViewModel.dismissDialog()
            },
            onGoToAppsSettingsClick = {

                permissionViewModel.openAppSettings(context)

            }

        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Order") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }

    ) { paddingValues ->

        BottomSheetScaffold(
            modifier = Modifier.padding(paddingValues),
            sheetContent = {

                when (contentOption) {

                    0 -> {

                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(13.dp)
                        ) {

                            items(offerResult.offerList) {

                                OfferSingleCard(it) {
                                    scope.launch {
                                        bottomSheetState.bottomSheetState.hide().apply {
                                            priceCalculationViewModel.setPriceTagWithCoupon(
                                                PriceDetails(
                                                    price = serviceDetailsData.price,
                                                    tax = serviceDetailsData.tax,
                                                    total = 0,
                                                    quantity = quantity,
                                                    coupon = Coupon(
                                                        offerTitle = it.offerTitle,
                                                        couponCode = it.offerCode,
                                                        discount = it.offerDiscount
                                                    )
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }

                    1 -> {

                        AddressBottomSheetContent(
                            navHostController = navHostController,
                            addressList = userAddressDataItems.addressList,
                            onGetLocationClick = {

                                locationPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            },
                            shareOrderDetailsViewModel = shareOrderDetailsViewModel,
                            locationViewModel = geoLocationViewModel
                        )

                    }

                }

            },
            scaffoldState = bottomSheetState,
            sheetContainerColor = Color.White,
            containerColor = Color.White,
            sheetPeekHeight = 0.dp,
            sheetShadowElevation = 10.dp,
            sheetTonalElevation = 20.dp,
            sheetDragHandle = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(onClick = {
                        scope.launch {
                            bottomSheetState.bottomSheetState.hide()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                    Text(
                        text = if (contentOption == 0) "Coupon" else "Address",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            },

            ) {

            Column(modifier = Modifier.fillMaxSize()) {

                LazyColumn(modifier = Modifier.weight(1f)) {

                    item {
                        ProductDetail(
                            productTitle = serviceDetailsData.serviceTitle,
                            productImage = serviceDetailsData.images[0],
                            price = serviceDetailsData.price.toString(),
                            quantity = {
                                shareOrderDetailsViewModel.setQuantity(it)
                            },
                            counterText = quantity
                        )
                    }


                    item {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
                    }


                    item {
                        ApplyOffer(
                            icon = R.drawable.badge_percent__1_,
                            promoCode = calculatedPrice?.coupon?.couponCode
                                ?: "Null",
                            onClick = {
                                scope.launch {
                                    contentOption = 0
                                    bottomSheetState.bottomSheetState.expand()
                                }
                            }
                        )
                    }


                    item {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
                    }


                    item {
                        PriceDetails(
                            icon = R.drawable.receipt,
                            priceTag = calculatedPrice
                        )
                    }

                    item {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
                    }

                    item {
                        DateSelection(
                            shareOrderDetailsViewModel = shareOrderDetailsViewModel,
                            dateValue = date
                        )
                    }

                    item {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
                    }

                    item {
                        TimeSelection(
                            shareOrderDetailsViewModel = shareOrderDetailsViewModel,
                            selectedValue = time
                        )
                    }

                    item {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
                    }

                    item {

                            AddressPicker(
                                address = address
                            ) {
                                scope.launch {
                                    contentOption = 1
                                    bottomSheetState.bottomSheetState.expand()
                                }
                            }

                    }

                }

                FullWidthWithPriceButton(
                    buttonText = "Pay",
                    priceText = "₹${calculatedPrice?.total}",
                    discount = calculatedPrice?.coupon?.discount,
                    textColor = Color.White,
                    color = MaterialTheme.colorScheme.primary
                ) {

                    val intent = Intent(context, CashFreePayment::class.java)

                    intent.putExtra("TITLE", serviceDetailsData.serviceTitle)
                    intent.putExtra("SERVICE_ID", serviceDetailsData.serviceId)
                    intent.putExtra("DATE", date)
                    intent.putExtra("TIME", time)
                    intent.putExtra("NAME", address?.name)
                    intent.putExtra("MOBILE", address?.mobile)
                    intent.putExtra("PIN_CODE", address?.pinCode)
                    intent.putExtra("STATE", address?.state)
                    intent.putExtra("CITY", address?.city)
                    intent.putExtra("BUILDING", address?.building)
                    intent.putExtra("AREA", address?.area)
                    intent.putExtra("TYPE", address?.type)
                    intent.putExtra("PRICE", calculatedPrice?.price.toString())
                    intent.putExtra("TAX", calculatedPrice?.tax.toString())
                    intent.putExtra("TOTAL", calculatedPrice?.total.toString())
                    intent.putExtra("DISCOUNT", calculatedPrice?.coupon?.discount.toString())
                    intent.putExtra("QUANTITY", calculatedPrice?.quantity.toString())
                    intent.putExtra("COUPON_CODE", calculatedPrice?.coupon?.couponCode)
                    intent.putExtra("COUPON_TITLE", calculatedPrice?.coupon?.offerTitle)



                    context.startActivity(intent)


                }
            }
        }
    }
}


@Composable
fun ApplyOffer(icon: Int, promoCode: String, onClick: () -> Unit) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = "Promo Applied",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = promoCode,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.SemiBold
            )
        }
        Box() {
            TextButton(onClick = { onClick() }, shape = RoundedCornerShape(5.dp)) {
                Text(text = "Apply", color = MaterialTheme.colorScheme.primary)
            }
        }
    }

}

@Composable
fun PriceDetails(icon: Int, priceTag: PriceDetails?) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)
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
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "₹${priceTag?.price}",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Tax & Fee",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "₹${priceTag?.tax}",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            if (priceTag != null) {
                if ((priceTag.coupon?.discount ?: 0) != 0) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Promo",
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "-₹${priceTag?.coupon?.discount}",
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Total ${priceTag?.quantity}",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "₹${priceTag?.total}",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }
}

@Composable
fun DateSelection(shareOrderDetailsViewModel: ShareProceedOrderDetailsViewModel, dateValue: String) {

    val currentMonthDates = generateCurrentMonthDates()

    Column {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "Date",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                LazyRow(
                    contentPadding = PaddingValues(13.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {

                    items(currentMonthDates, key = { it.day_of_month }) {

                        DateSingleCard(
                            dateDataModel = it,
                            selectedValue = dateValue,
                            onSelect = {
                                shareOrderDetailsViewModel.getDate(it)
                            }
                        )

                    }


                }
            }

        }

    }


}

@Composable
fun TimeSelection(shareOrderDetailsViewModel: ShareProceedOrderDetailsViewModel, selectedValue: String) {

    Column {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.clock_five),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "Time",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                LazyRow(
                    contentPadding = PaddingValues(13.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {

                    items(timeList, key = { it.time }) {

                        TimeSingleCard(
                            date = it.time.toString(),
                            week = it.format,
                            selectedValue = selectedValue,
                            onSelect = {
                                shareOrderDetailsViewModel.getTime(it)

                            }
                        )

                    }


                }
            }

        }

    }


}

@Composable
fun AddressPicker(address: UserAddressDataModel?, onClick: () -> Unit) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.track),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        address.let {

            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Address",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = it?.name ?: "",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = it?.mobile ?: "",
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = "${it?.building} ${it?.area} ${it?.pinCode}\n${it?.city} ${it?.state}",
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.displaySmall
            )
        }

        }
        Box() {
            TextButton(
                onClick = { onClick() },
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Address",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun DateSingleCard(dateDataModel: DateDataModel, selectedValue: String, onSelect: (String) -> Unit) {

    val dateValue by rememberSaveable {
        mutableStateOf("${dateDataModel.day_of_month} ${dateDataModel.month} ${dateDataModel.year}")
    }

    Card(
        modifier = Modifier
            .height(80.dp)
            .width(70.dp),
        onClick = {
            onSelect(dateValue)
        },
        colors = CardDefaults.cardColors(
            containerColor = if(dateValue == selectedValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
            contentColor = if(dateValue == selectedValue) Color.White else MaterialTheme.colorScheme.onSecondary
        ),
        border = BorderStroke(
            1.dp,
            color = if (dateValue == selectedValue) MaterialTheme.colorScheme.primary else Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)

    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = dateDataModel.day_of_month.toString(),
                    lineHeight = 10.sp,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(text = dateDataModel.day_of_week)
            }
        }

    }


}

@Composable
fun TimeSingleCard(date: String, week: String, selectedValue: String, onSelect: (String) -> Unit) {

    val dateValue by rememberSaveable {
        mutableStateOf("$date $week")
    }
    Card(
        modifier = Modifier
            .height(54.dp),
        onClick = {
            onSelect(dateValue)
        },
        colors = CardDefaults.cardColors(
            containerColor = if(dateValue == selectedValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onTertiary,
            contentColor = if(dateValue == selectedValue) Color.White else MaterialTheme.colorScheme.onSecondary
        ),
        border = BorderStroke(
            1.dp,
            color = if (dateValue == selectedValue) MaterialTheme.colorScheme.primary else Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

           Text(text = date, style = MaterialTheme.typography.displayMedium)
           Text(text = week,style = MaterialTheme.typography.displayMedium)

        }

    }


}

@Composable
fun ProductDetail(productTitle: String, productImage: String,price: String, quantity: (Int) -> Unit,counterText: Int) {


    Row(modifier = Modifier.padding(horizontal = 16.dp)) {

            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(120.dp),
                model = productImage,
                contentDescription = null
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {

                Text(
                    text = productTitle,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "₹$price")
                Spacer(modifier = Modifier.padding(10.dp))
                ItemIncrement(countValue = { quantity(it) },counterText = counterText)

            }
        }




}

@Composable
fun ItemIncrement(countValue: (Int) -> Unit,counterText:Int) {

    var counter by rememberSaveable {
        mutableIntStateOf(1)
    }
    var increamentStatus by rememberSaveable {
        mutableStateOf(true)
    }
    var decreamentStatus by rememberSaveable {
        mutableStateOf(true)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        SmallCardButton(
            icon = R.drawable.plus_small,
            iconTint = MaterialTheme.colorScheme.primary,
            status = increamentStatus,
            onClick = {
                if (counter == 5) {
                    increamentStatus = false
                } else {
                    decreamentStatus = true
                    counter++
                    countValue(counter)
                }


            })
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "$counterText", color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.padding(4.dp))
        SmallCardButton(
            icon = R.drawable.minus_small,
            iconTint = MaterialTheme.colorScheme.primary,
            status = decreamentStatus,
            onClick = {

                if (counter == 1) {
                    decreamentStatus = false
                } else {
                    increamentStatus = true
                    counter--
                    countValue(counter)
                }

            })
    }
}


@Composable
fun AddressBottomSheetContent(
    navHostController: NavHostController,
    addressList: List<UserAddressDataModel>,
    onGetLocationClick: () -> Unit,
    shareOrderDetailsViewModel: ShareProceedOrderDetailsViewModel,
    locationViewModel: GeoLocationViewModel
) {

    val currentLocation = locationViewModel.locationAddress.value

    var satate by rememberSaveable {
        mutableStateOf("")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var mobile by rememberSaveable {
        mutableStateOf("")
    }


    LazyColumn {

        item {

            TextButton(
                onClick = {
                    onGetLocationClick()
                },
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 6.dp
                    )
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location_crosshairs),
                        contentDescription = " ",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Text(
                        text = "Use my current location",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

            }

        }

        item {

            if (currentLocation.isLoading) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }


            } else if (currentLocation.locationAddress?.address?.isNotEmpty() == true) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(13.dp)
                ) {

                    CommonInputField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = "Name",
                        lable = "Name",
                        textValue = { name = it },
                        keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    CommonInputField(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = "Mobile",
                        lable = "Mobile",
                        textValue = { mobile = it },
                        keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                color = MaterialTheme.colorScheme.onTertiary,
                                shape = RoundedCornerShape(5.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,

                        ) {


                        Icon(
                            painter = painterResource(id = R.drawable.track),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(horizontal = 13.dp)
                                .size(24.dp)
                        )

                        Text(
                            modifier = Modifier.padding(vertical = 13.dp),
                            text = currentLocation.locationAddress.address
                        )
                    }


                    FullWidthButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        text = "Confirm",
                        textColor = Color.White,
                        color = MaterialTheme.colorScheme.primary,
                        enabled = !(name.isEmpty() || mobile.isEmpty()),
                        onClick = {
                            shareOrderDetailsViewModel.getAddress(
                                UserAddressDataModel(
                                    name = name,
                                    mobile = mobile,
                                    pinCode = currentLocation.locationAddress.pinCode.toString(),
                                    state = "",
                                    city = "",
                                    building = currentLocation.locationAddress.address,
                                    area = "",
                                    type = "",
                                    addressId = ""

                                )
                            )
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(10.dp))

                }

            }

        }

        items(addressList, key = { it.addressId }) { data ->
            AddressBottomSheetCard(
                addressData = data,
                onClickState = satate,
                buttonState = {
                    satate = it

                    shareOrderDetailsViewModel.getAddress(data)
                },

                )
        }

        item {

            TextButton(
                onClick = {
                    navHostController.navigate(HomeNavRoutes.ADDRESS_FIELD)
                },
                modifier = Modifier.padding(20.dp)

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.map_marker_plus),
                        contentDescription = " ",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Text(text = "Add new address", color = MaterialTheme.colorScheme.primary)
                }

            }
        }


    }


}


@Composable
fun AddressBottomSheetCard(
    addressData: UserAddressDataModel,
    onClickState: String,
    buttonState: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 16.dp),
        colors = if (addressData.addressId == onClickState) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary) else CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp)
    ) {


        Row(
            modifier = Modifier.height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = addressData.addressId == onClickState,
                onClick = {

                    buttonState(addressData.addressId)


                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = if (addressData.addressId == onClickState) Color.White else MaterialTheme.colorScheme.onPrimary

                )

            )
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "${addressData.type} (${addressData.name})",
                    color = if (addressData.addressId == onClickState) Color.White else MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = "${addressData.building}, ${addressData.area}, ${addressData.pinCode}, ${addressData.city}, ${addressData.state}",
                    color = if (addressData.addressId == onClickState) Color.White else MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }


    }
}


@Composable
fun OfferSingleCard(offerDataModel: OfferDataModel, onApplyClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ),
        onClick = {

        }

    ) {


        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,

            ) {

            AsyncImage(
                modifier = Modifier.padding(end = 10.dp).size(32.dp),
                model = offerDataModel.offerImage,
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )

            Box(modifier = Modifier.weight(1f)) {

                SpannableOfferText(offerTitle = offerDataModel.offerTitle, couponCode = offerDataModel.offerCode)

            }

            TextButton(
                modifier = Modifier.padding(start = 13.dp),
                onClick = { onApplyClick() },
                shape = RoundedCornerShape(5.dp),

                ) {
                Text(text = "Apply", color = MaterialTheme.colorScheme.primary)
            }


        }
    }


}

