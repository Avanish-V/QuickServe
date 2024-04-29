package com.byteapps.serrvicewala

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.byteapps.karigar.presentation.Screens.bottomBarScreens.BottomBar.items
import com.byteapps.serrvicewala.Authentication.Screens.OTPScreen
import com.byteapps.serrvicewala.Authentication.Screens.PhoneNumberScreen
import com.byteapps.serrvicewala.Features.Offers.OffersViewModel
import com.byteapps.serrvicewala.LocationPermission.PermissionStateViewModel
import com.byteapps.serrvicewala.Screens.BottomNavigation.AccountScreen
import com.byteapps.serrvicewala.Screens.BottomNavigation.HomeScreen
import com.byteapps.serrvicewala.Screens.BottomNavigation.InboxScreen
import com.byteapps.serrvicewala.Screens.BottomNavigation.OrderScreen
import com.byteapps.serrvicewala.Utils.NavigationRoutes.AuthenticationRoutes
import com.byteapps.serrvicewala.Utils.NavigationRoutes.BottomNavRoutes
import com.byteapps.serrvicewala.Utils.NavigationRoutes.MainRootRoute
import com.byteapps.serrvicewala.Utils.NavigationRoutes.OrderNavRoutes
import com.byteapps.serrvicewala.ui.theme.ServizTheme
import com.byteapps.wiseschool.GeoFencing.GeoLocation.GeoLocationViewModel
import com.byteapps.wiseschool.GeoFencing.Permission.PermissionViewModel
import com.byteappstudio.b2ccart.Authentications.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val navHostController = rememberNavController()
            val authViewModel : AuthViewModel = hiltViewModel()

            ServizTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    if (FirebaseAuth.getInstance().currentUser != null){
                        MainRootNavHost()
                    }
                    else{

                        NavHost(

                            navController = navHostController,
                            startDestination = AuthenticationRoutes.PHONE_NUMBER,

                            ) {

                            composable(route = AuthenticationRoutes.PHONE_NUMBER) {
                                PhoneNumberScreen(
                                    navHostController = navHostController,
                                    authViewModel = authViewModel
                                )
                            }

                            composable(route = AuthenticationRoutes.OPT) {
                                OTPScreen(
                                    navHostController = navHostController,
                                    authViewModel = authViewModel
                                )
                            }

                            composable(route = MainRootRoute.MAIN_ROUTE){
                                MainRootNavHost()
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomAppBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val destination = navBackStackEntry?.destination

        NavigationBar(

            containerColor = Color.White,
            modifier = Modifier
                .shadow(elevation = 5.dp)
                .height(75.dp),
            contentColor = Color.Transparent


        ) {

            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {

                        Icon(
                            painter = painterResource(id =  if (destination?.route == item.route) item.iconBold else item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )

                    },
                    label = { Text(item.item, fontWeight = FontWeight.Normal) },
                    selected = destination?.hierarchy?.any {
                        it.route == item.route
                    } == true,
                    onClick = {

                        if (destination?.route != item.route) {
                            navController.navigate(item.route) {

                                popUpTo(navController.graph.findStartDestination().id) {

                                    saveState = false
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        }


                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.onTertiary,
                        selectedTextColor = MaterialTheme.colorScheme.primary
                    )


                )
            }
        }
    }


@Composable
fun MainRootNavHost() {

    val navHostController = rememberNavController()

    var destination by rememberSaveable {
        mutableStateOf("")
    }


    Scaffold (

        bottomBar = {

            if (destination == "PRODUCT_DASHBOARD" || destination == OrderNavRoutes.ORDER_LIST){
                BottomAppBar(
                    navController =navHostController
                )
            }

        }

    ){paddingValues ->


        val permissionViewModel : PermissionViewModel = hiltViewModel()
        val locationViewModel : GeoLocationViewModel = hiltViewModel()
        val context = LocalContext.current


        val locationPermissionResultLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                permissionViewModel.onPermissionResult(
                    permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    isGrant = isGranted,
                    context = context
                )
                if (isGranted) {


                    locationViewModel.checkAvailability(context)

                }

            }
        )


        LaunchedEffect(1){

            locationPermissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }


        NavHost(

            navController = navHostController,
            startDestination = BottomNavRoutes.BOTTOM_BAR_HOME_SCREEN,
            modifier = Modifier.padding(
                paddingValues
            )

        ) {

            composable(route = BottomNavRoutes.BOTTOM_BAR_HOME_SCREEN) {

                HomeScreen(
                    currentDestination = {
                        destination = it
                    },
                    geoLocationViewModel = locationViewModel
                )
            }
            composable(route = BottomNavRoutes.BOTTOM_BAR_INBOX_SCREEN) {

                val offerViewModel:OffersViewModel = hiltViewModel()

                InboxScreen(offersViewModel = offerViewModel)
            }

            composable(route = BottomNavRoutes.BOTTOM_BAR_ORDER_SCREEN){
                OrderScreen(
                    currentDestination = {
                        destination = it
                    }
                )
            }

            composable(route = BottomNavRoutes.BOTTOM_BAR_ACCOUNT_SCREEN) {
                AccountScreen()
            }

        }

    }

}


