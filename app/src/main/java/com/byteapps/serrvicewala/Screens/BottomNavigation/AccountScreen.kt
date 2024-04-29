package com.byteapps.serrvicewala.Screens.BottomNavigation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
 import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.byteapps.QuickServe.R
import com.byteapps.karigar.presentation.Screens.AccountNavHost.HelpCenter
import com.byteapps.karigar.presentation.Screens.AccountNavHost.ManageAddress
import com.byteapps.karigar.presentation.Screens.AccountNavHost.PrivacyPolicyScreen
import com.byteapps.karigar.presentation.Screens.AccountNavHost.ProfileScreen
import com.byteapps.karigar.presentation.Screens.AccountNavHost.SettingScreen
import com.byteapps.karigar.presentation.Screens.AccountNavHost.TermsConditionScreen
import com.byteapps.serrvicewala.Address.AddressViewModel
import com.byteapps.serrvicewala.Features.UserProfile.UserProfileViewModel
import com.byteapps.serrvicewala.MainActivity
import com.byteapps.serrvicewala.SharedViewModel.ShareUserProfileViewModel
import com.byteapps.serrvicewala.UIComponents.sendEmailSingleRecipient
import com.byteapps.serrvicewala.Utils.NavigationRoutes.AccountNavRoutes
import com.byteapps.serrvicewala.Utils.NavigationRoutes.AuthenticationRoutes
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen() {

    val navHostController = rememberNavController()

    val userProfileViewModel:UserProfileViewModel = hiltViewModel()

    NavHost(navController = navHostController, startDestination = AccountNavRoutes.MENU_SCREEN){

        composable(route = AccountNavRoutes.MENU_SCREEN){
            MenuScreen(navHostController)
        }
        composable(route = AccountNavRoutes.PROFILE){

            val shareUserProfileViewModel : ShareUserProfileViewModel = hiltViewModel()

            ProfileScreen(
                navHostController = navHostController,
                shareUserProfileViewModel = shareUserProfileViewModel,
                userProfileViewModel = userProfileViewModel

            )
        }

        composable(route = AccountNavRoutes.ADDRESS){
           val addressViewModel : AddressViewModel = hiltViewModel()
           ManageAddress(
               navHostController = navHostController,
               addressViewModel = addressViewModel
           )
        }
        composable(route = AccountNavRoutes.HELP){
            HelpCenter(navHostController = navHostController)
        }

        composable(route = AccountNavRoutes.TERM_CONDITIONS){
            TermsConditionScreen(navHostController = navHostController)
        }
        composable(route = AccountNavRoutes.PRIVACY_POLICY){
            PrivacyPolicyScreen(navHostController = navHostController)
        }
        composable(route = AccountNavRoutes.SETTINGS){
            SettingScreen(navHostController = navHostController)
        }

    }




}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Account") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                )
            )

        }
    ) { paddingValues ->

        Column (Modifier.padding(paddingValues)){


            Menu1(navHostController)

            Menu2(navHostController)

            LogOut(onLogoutClick = {
                FirebaseAuth.getInstance().signOut().apply {

                }
            })

        }
    }
}



@Composable
fun Menu1(navHostController: NavHostController) {

    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                color = MaterialTheme.colorScheme.onTertiary,
                width = 2.dp,
                shape = RoundedCornerShape(6.dp)
            ),
    ) {
        Column(

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            MenuSingleItem(icon = R.drawable.user, text = "Profile", onClick = {navHostController.navigate(AccountNavRoutes.PROFILE)})
            CustomDivider()
            MenuSingleItem(icon = R.drawable.track, text = "Address", onClick = {navHostController.navigate(AccountNavRoutes.ADDRESS)})


        }
    }


}


@Composable
fun Menu2(navHostController: NavHostController) {

    val context = LocalContext.current

    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                color = MaterialTheme.colorScheme.onTertiary,
                width = 2.dp,
                shape = RoundedCornerShape(6.dp)
            ),
    ) {
        Column(

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            MenuSingleItem(icon = R.drawable.user_headset, text = "Help", onClick = {navHostController.navigate(AccountNavRoutes.HELP)})
            CustomDivider()
            MenuSingleItem(
                icon = R.drawable.feedback_hand,
                text = "Feedback",
                onClick = {
                    sendEmailSingleRecipient(context = context,"byteappstudeiopvt@gmail.com")
                }
            )
            CustomDivider()
            MenuSingleItem(icon = R.drawable.memo_circle_check, text = "Terms & Conditions", onClick = {navHostController.navigate(AccountNavRoutes.TERM_CONDITIONS)})
            CustomDivider()
            MenuSingleItem(icon = R.drawable.user_lock__1_, text = "Privacy Policy", onClick = {navHostController.navigate(AccountNavRoutes.PRIVACY_POLICY)})
            CustomDivider()
            MenuSingleItem(icon = R.drawable.settings, text = "Setting", onClick = {navHostController.navigate(AccountNavRoutes.SETTINGS)})

        }
    }


}



@Composable
fun LogOut(onLogoutClick:()->Unit) {

    Box(
        Modifier
            .clickable { onLogoutClick() }
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                color = MaterialTheme.colorScheme.onTertiary,
                width = 2.dp,
                shape = RoundedCornerShape(6.dp)
            ),
    ) {
        Column(

            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            MenuSingleItem(icon = R.drawable.sign_out_alt__2_, text = "Sign-out", onClick = {})

        }
    }


}



@Composable
fun CustomDivider() {

    HorizontalDivider(color = MaterialTheme.colorScheme.onTertiary, modifier = Modifier.height(2.dp))
}



@Composable
fun MenuSingleItem(icon:Int,text:String,onClick:()->Unit) {

    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }, verticalAlignment = Alignment.CenterVertically) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(text = text)
        }

        Icon(
            painter = painterResource(id = R.drawable.round_keyboard_arrow_right_24),
            contentDescription = "",
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
    }
}