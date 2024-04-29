package com.byteapps.serrvicewala.Screens.ProceedOrderNavigation

import android.Manifest
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Features.Crousel.CarouselDataModel
import com.byteapps.serrvicewala.Features.Crousel.CarouselViewModel
import com.byteapps.serrvicewala.LocationPermission.PermissionDialog
import com.byteapps.serrvicewala.LocationPermission.StateDialog
import com.byteapps.serrvicewala.LocationPermission.locationPermissionText
import com.byteapps.serrvicewala.Features.ServiceCategories.Presentation.ViewModel.ServiceCategoryViewModel
import com.byteapps.serrvicewala.LocationPermission.PermissionStateViewModel
import com.byteapps.serrvicewala.SharedViewModel.ShareCategoryViewModel
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.UIComponents.Promotions
import com.byteapps.serrvicewala.UIComponents.RecommendedServicesCard
import com.byteapps.serrvicewala.UIComponents.ServiceCategoryCard
import com.byteapps.serrvicewala.Utils.NavigationRoutes.HomeNavRoutes
import com.byteapps.wiseschool.GeoFencing.GeoLocation.GeoLocationViewModel
import com.byteapps.wiseschool.GeoFencing.Permission.PermissionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    serviceCategoryViewModel: ServiceCategoryViewModel,
    shareCategoryViewModel: ShareCategoryViewModel,
    permissionViewModel: PermissionViewModel,
    geoLocationViewModel: GeoLocationViewModel,
    carouselViewModel: CarouselViewModel
) {

    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )


    val snackBarHostState = SnackbarHostState()

    val scope  = rememberCoroutineScope()

    val serviceCategoryData = serviceCategoryViewModel.serviceCategoryDataItem.value

    val availabilityResult = geoLocationViewModel.isAvailable.value


    val carousel = carouselViewModel.carouselResults.value



        scope.launch {
            if (availabilityResult.isAvailable?.isAvailable == true){

                snackBarHostState.showSnackbar("Service Available in your area.")
            }
            else{
                snackBarHostState.showSnackbar("Sorry! Service Not Available In Your Area.")
            }

        }



    StateDialog(
        dialogQueue = permissionViewModel.visiblePermissionDialogQueue,
        permissionViewModel = permissionViewModel,
        context = context
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.marker),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )


                        if (availabilityResult.isLoading){
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .size(22.dp),
                                strokeWidth = 2.dp,
                                strokeCap = StrokeCap.Round,
                                color = Color.White
                            )
                        }
                        else if (availabilityResult.isAvailable?.currentLocation?.isEmpty() == true){
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(text = "Check availability")
                            }
                        }
                        else{
                            availabilityResult.isAvailable?.let {
                                Text(
                                    text = it.currentLocation,
                                    style = MaterialTheme.typography.displaySmall,
                                    color = Color.White,
                                    maxLines = 1
                                )
                            }
                        }

                    }
                },
                scrollBehavior = scrollBehavior

            )
        },
        snackbarHost = {


          SnackbarHost(
              hostState = snackBarHostState
          ) {
            Snackbar(snackbarData = it)
          }



        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)

    ) { paddingValues ->

        LazyColumn(Modifier.padding(paddingValues)) {

            item {

                when {
                    carousel.isLoading -> {

                    }
                    carousel.carouselList.isNotEmpty()->{

                        LazyRow(
                            content = {
                                items(carousel.carouselList) {
                                    Promotions(
                                        carouselData = it,
                                        onButtonClick = {
                                            navHostController.navigate(HomeNavRoutes.PRODUCT_LIST).apply {
                                                shareCategoryViewModel.setCategoryId(it.carouselTAG)
                                            }
                                        }
                                    )
                                }
                            },
                            contentPadding = PaddingValues(13.dp),
                            horizontalArrangement = Arrangement.spacedBy(13.dp),

                        )

                    }
                    carousel.error.isNotEmpty()->{


                    }
                }



            }

            item {

                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = 13.dp,
                            vertical = 10.dp
                        )
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Most popular services",
                        style = MaterialTheme.typography.displayMedium
                    )

//                    Text(
//                        text = "See all",
//                        style = MaterialTheme.typography.displaySmall,
//                        color = MaterialTheme.colorScheme.primary
//                    )
                }

            }

            item {

                LazyRow(
                    contentPadding = PaddingValues(13.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp)
                ) {
                    items(serviceCategoryData.serviceCategoryList) {

                        ServiceCategoryCard(
                            icon = it.icon,
                            title = it.serviceTitle)
                        {
                            navHostController.navigate(HomeNavRoutes.PRODUCT_LIST).apply {
                                shareCategoryViewModel.setCategoryId(it.id)
                            }
                        }
                    }
                }
            }

            item {

                Row(modifier = Modifier.padding(horizontal = 13.dp, vertical = 10.dp)) {
                    Text(
                        text = "Recommended Services ",
                        style = MaterialTheme.typography.displayMedium
                    )
                }

            }

            item {
                Recommended(navHostController = navHostController)
            }


        }

        if(availabilityResult.isLoading){
            LoadingScreen()
        }
    }

}



@Composable
fun Recommended(navHostController: NavHostController) {

    LazyRow(
        contentPadding = PaddingValues(13.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        items(5) {
            RecommendedServicesCard(modifier = Modifier, navHostController)
        }
    }
}