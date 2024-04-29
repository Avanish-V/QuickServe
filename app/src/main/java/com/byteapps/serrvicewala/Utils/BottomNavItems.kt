package com.byteapps.karigar.presentation.Screens.bottomBarScreens.BottomBar

import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Utils.NavigationRoutes.BottomNavRoutes


data class navItems(

    val item: String,
    val icon: Int,
    val iconBold: Int,
    val route: String
)


val items = listOf(
    navItems(
        "Home",
        R.drawable.home,
        R.drawable.home_solid,
        BottomNavRoutes.BOTTOM_BAR_HOME_SCREEN
    ),
    navItems(
        "Inbox",
        R.drawable.envelope,
        R.drawable.envelope_solid,
        BottomNavRoutes.BOTTOM_BAR_INBOX_SCREEN
    ),
    navItems(
        "Orders",
        R.drawable.memo_pad,
        R.drawable.memo_solid,
       BottomNavRoutes.BOTTOM_BAR_ORDER_SCREEN
    ),
    navItems(
        "Account",
        R.drawable.user,
        R.drawable.user_solid,
       BottomNavRoutes.BOTTOM_BAR_ACCOUNT_SCREEN
    )
)