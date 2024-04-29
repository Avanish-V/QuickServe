package com.byteapps.karigar.presentation.Screens.AccountNavHost

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteapps.serrvicewala.Address.AddressViewModel
import com.byteapps.serrvicewala.Address.UserAddressDataModel
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.UIComponents.MyAlertDialog
import com.byteapps.serrvicewala.UIComponents.StatusScreen
import com.byteapps.serrvicewala.Utils.ResultState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAddress(
    navHostController: NavHostController,
    addressViewModel: AddressViewModel
) {

    LaunchedEffect(Unit){
        addressViewModel.getAddress()
    }
    val addressData = addressViewModel.addressData.collectAsState().value

    var isDialog by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var addressId by rememberSaveable {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Address") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription ="" )
                    }

                }
            )

        }
    ) { paddingValues ->


        when {
            addressData.isLoading -> {

                LoadingScreen()
            }

            addressData.addressList.isNotEmpty()->{

                LazyColumn(Modifier.padding(paddingValues), contentPadding = PaddingValues(13.dp), verticalArrangement = Arrangement.spacedBy(13.dp)){

                    items(addressData.addressList){
                        AddressSingleRow(
                            it,
                            onDelete = {
                                addressId = it.addressId
                                isDialog = true
                            }
                        )
                    }

                }

            }
            addressData.addressList.isEmpty()->{

                StatusScreen(text = "No address!")

            }
            addressData.error.isNotEmpty()->{
                StatusScreen(text = "Something went wrong!")
            }
        }

        if (isDialog){
            MyAlertDialog(
                onOuterClick = { isDialog = false },
                onDismissClick = { isDialog = false },
                onConfirmClick = {

                 scope.launch {
                     addressViewModel.deleteAddress(addressId).collect{
                         when(it){
                             is ResultState.Loading->{
                                 isLoading = true
                             }
                             is ResultState.Success->{
                                 isLoading = false
                                 isDialog = false
                                 addressViewModel.getAddress()
                             }
                             is ResultState.Error->{
                                 isLoading = false
                                 isDialog = false
                             }
                         }
                     }
                 }

                },
                isLoading = isLoading
            )
        }


    }

}


@Composable
fun AddressSingleRow(addressDataModel: UserAddressDataModel,onDelete:()->Unit) {


   Row (
       Modifier
           .fillMaxWidth()
           .border(
               1.dp,
               color = MaterialTheme.colorScheme.onTertiary,
               shape = RoundedCornerShape(8.dp)
           )){

       Column(
           Modifier
               .weight(1f)
               .padding(13.dp),
           verticalArrangement = Arrangement.spacedBy(8.dp)
       ) {

           Text(text = addressDataModel.name, style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.onPrimary)

           Text(text = addressDataModel.mobile, style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onSecondary)

           Text(text = "${addressDataModel.building}, ${addressDataModel.area}, ${addressDataModel.pinCode}\n" +
                   "${addressDataModel.city}, ${addressDataModel.state}", style = MaterialTheme.typography.displaySmall, color = MaterialTheme.colorScheme.onSecondary)

           Text(text = addressDataModel.type, style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.onSecondary)

       }

       IconButton(onClick = { onDelete() }) {
           Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
       }
   }


}