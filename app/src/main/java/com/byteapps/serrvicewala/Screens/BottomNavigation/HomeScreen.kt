package com.byteapps.serrvicewala.Screens.BottomNavigation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.byteapps.serrvicewala.Address.AddressViewModel
import com.byteapps.serrvicewala.Features.Crousel.CarouselViewModel
import com.byteapps.serrvicewala.Features.Offers.OffersViewModel
import com.byteapps.serrvicewala.Features.Orders.OrdersViewModel
import com.byteapps.serrvicewala.Features.PaymentGetway.Presentation.StartPaymentViewModel
import com.byteapps.serrvicewala.Features.PriceCalculation.PriceCalculationViewModel
import com.byteapps.serrvicewala.Features.Reviews.ServiceReviewsViewModel
import com.byteapps.serrvicewala.Screens.ProceedOrderNavigation.AddressFieldScreen
import com.byteapps.serrvicewala.Screens.ProceedOrderNavigation.DashboardScreen
import com.byteapps.serrvicewala.Screens.ProceedOrderNavigation.ProceedOrderScreen
import com.byteapps.serrvicewala.Screens.ProceedOrderNavigation.ProductDetailsScreen
import com.byteapps.serrvicewala.Screens.ProceedOrderNavigation.ProductListScreen
import com.byteapps.serrvicewala.Features.ServiceCategories.Presentation.ViewModel.ServiceCategoryViewModel
import com.byteapps.serrvicewala.Features.ServiceCategories.Presentation.ViewModel.ServiceProductsViewModel
import com.byteapps.serrvicewala.LocationPermission.PermissionStateViewModel
import com.byteapps.serrvicewala.SharedViewModel.ShareCategoryViewModel
import com.byteapps.serrvicewala.SharedViewModel.ShareProceedOrderDetailsViewModel
import com.byteapps.serrvicewala.SharedViewModel.ShareServiceDetailViewModel
import com.byteapps.serrvicewala.Utils.NavigationRoutes.HomeNavRoutes
import com.byteapps.serrvicewala.Validations.AddressFormValidationViewModel
import com.byteapps.wiseschool.GeoFencing.GeoLocation.GeoLocationViewModel
import com.byteapps.wiseschool.GeoFencing.Permission.PermissionViewModel


@Composable
fun HomeScreen(currentDestination:(String)->Unit,geoLocationViewModel: GeoLocationViewModel) {

    val navHostController = rememberNavController()

    val currentDestination = navHostController.currentBackStackEntryAsState().value?.destination?.route

    if (currentDestination != null) {
        currentDestination(currentDestination)
    }

    val context = LocalContext.current

    val shareCategoryViewModel : ShareCategoryViewModel = hiltViewModel()
    val shareServiceDetailViewModel:ShareServiceDetailViewModel = hiltViewModel()
    val permissionViewModel:PermissionViewModel = hiltViewModel()
   // val geoLocationViewModel:GeoLocationViewModel = hiltViewModel()
    val shareOrderDetailsViewModel:ShareProceedOrderDetailsViewModel= hiltViewModel()
    val addressViewModel:AddressViewModel = hiltViewModel()
    val priceCalculationViewModel: PriceCalculationViewModel = hiltViewModel()
    val serviceReviewsViewModel : ServiceReviewsViewModel = hiltViewModel()
    val offersViewModel:OffersViewModel = hiltViewModel()
    val permissionStateViewModel : PermissionStateViewModel = hiltViewModel()
    val carouselViewModel:CarouselViewModel = hiltViewModel()





    NavHost(
        navController = navHostController,
        startDestination = HomeNavRoutes.PRODUCT_DASHBOARD,

    ) {

        composable(route = HomeNavRoutes.PRODUCT_DASHBOARD) {

            val serviceCategoryViewModel: ServiceCategoryViewModel = hiltViewModel()
          //  val locationViewModel:GeoLocationViewModel = hiltViewModel()



            DashboardScreen(
                navHostController = navHostController,
                serviceCategoryViewModel = serviceCategoryViewModel,
                shareCategoryViewModel = shareCategoryViewModel,
                permissionViewModel = permissionViewModel,
                geoLocationViewModel = geoLocationViewModel,
               carouselViewModel = carouselViewModel
            )

        }
        composable(
            route = HomeNavRoutes.PRODUCT_LIST,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(400)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(400)
                )
            },
        ) {

            val serviceProductsViewModel: ServiceProductsViewModel = hiltViewModel()

           ProductListScreen(
               navHostController = navHostController,
               serviceProductsViewModel = serviceProductsViewModel,
               shareCategoryViewModel = shareCategoryViewModel,
               shareServiceDetailViewModel = shareServiceDetailViewModel
           )

        }
        composable(
            route = HomeNavRoutes.PRODUCT_DETAIL,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(400)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(400)
                )
            },
        ) {

           ProductDetailsScreen(
               navHostController = navHostController,
               shareServiceDetailViewModel = shareServiceDetailViewModel,
               serviceReviewsViewModel = serviceReviewsViewModel
           )

        }

        composable(
            route = HomeNavRoutes.PROCEED_ORDER_DETAIL,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(400)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(400)
                )
            },
        ) {

            ProceedOrderScreen(
                shareServiceDetailViewModel = shareServiceDetailViewModel,
                shareOrderDetailsViewModel = shareOrderDetailsViewModel,
                permissionViewModel = permissionViewModel,
                geoLocationViewModel = geoLocationViewModel,
                addressViewModel = addressViewModel,
                offersViewModel = offersViewModel,
                priceCalculationViewModel = priceCalculationViewModel,
                navHostController = navHostController,

            )

        }

        composable(
            route = HomeNavRoutes.ADDRESS_FIELD,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, tween(700)
                )
            },
        ) {

            val addressFormValidationViewModel : AddressFormValidationViewModel = hiltViewModel()

            AddressFieldScreen(
                navHostController = navHostController,
                addressViewModel = addressViewModel,
                addressFormValidationViewModel = addressFormValidationViewModel
            )

        }

    }
}