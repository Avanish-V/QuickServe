package com.byteapps.serrvicewala.Screens.BottomNavigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.byteapps.serrvicewala.Features.Orders.OrdersViewModel
import com.byteapps.serrvicewala.Features.PaymentGetway.Presentation.StartPaymentViewModel
import com.byteapps.serrvicewala.Screens.OrdersNavigations.OrderListScreen
import com.byteapps.serrvicewala.Screens.OrdersNavigations.OrderedDetailsScreen
import com.byteapps.serrvicewala.Screens.OrdersNavigations.TakeReviewScreen
import com.byteapps.serrvicewala.SharedViewModel.ShareOrderDetailsViewModel
import com.byteapps.serrvicewala.Utils.NavigationRoutes.OrderNavRoutes
import com.byteapps.serrvicewala.Validations.ReviewFormValidationViewModel

@Composable
fun OrderScreen(currentDestination:(String)->Unit) {


    val navHostController = rememberNavController()

    val currentDestination =
        navHostController.currentBackStackEntryAsState().value?.destination?.route
    if (currentDestination != null) {
        currentDestination(currentDestination)
    }

    val ordersViewModel: OrdersViewModel = hiltViewModel()
    val shareOrderDetailsViewModel: ShareOrderDetailsViewModel = hiltViewModel()


    NavHost(navController = navHostController, startDestination = OrderNavRoutes.ORDER_LIST) {

        composable(route = OrderNavRoutes.ORDER_LIST) {

            OrderListScreen(
                navHostController = navHostController,
                ordersViewModel = ordersViewModel,
                shareOrderDetailsViewModel = shareOrderDetailsViewModel

            )

        }
        composable(
            route = OrderNavRoutes.ORDERED_DETAIL,
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

            val paymentViewModel : StartPaymentViewModel = hiltViewModel()
            OrderedDetailsScreen(
                navHostController = navHostController,
                orderDetailsViewModel = shareOrderDetailsViewModel,
                ordersViewModel = ordersViewModel,
                paymentViewModel = paymentViewModel
            )

        }
        composable(route = OrderNavRoutes.TAKE_REVIEW) {

            val reviewFormValidationViewModel: ReviewFormValidationViewModel = hiltViewModel()

            TakeReviewScreen(
                navHostController = navHostController,
                reviewFormValidationViewModel = reviewFormValidationViewModel,
                shareOrderDetailsViewModel = shareOrderDetailsViewModel,
                ordersViewModel = ordersViewModel

            )

        }


    }


}
