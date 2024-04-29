package com.byteapps.serrvicewala.Screens.ProceedOrderNavigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteapps.serrvicewala.Features.ServiceCategories.Presentation.ViewModel.ServiceProductsViewModel
import com.byteapps.serrvicewala.SharedViewModel.ShareCategoryViewModel
import com.byteapps.serrvicewala.SharedViewModel.ShareServiceDetailViewModel
import com.byteapps.serrvicewala.UIComponents.HorizontalProductCard
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.Utils.NavigationRoutes.HomeNavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navHostController: NavHostController,
    serviceProductsViewModel: ServiceProductsViewModel,
    shareCategoryViewModel: ShareCategoryViewModel,
    shareServiceDetailViewModel: ShareServiceDetailViewModel

) {

    LaunchedEffect(Unit){
      serviceProductsViewModel.getServiceProducts(shareCategoryViewModel.categoryId.value)
    }
    val serviceProductData = serviceProductsViewModel.serviceProductDataItem.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "AC Services", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = {navHostController.popBackStack()}) {
                       Icon(
                           imageVector = Icons.Default.ArrowBack,
                           contentDescription = null,
                       )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                ),

            )
        }
    ) {paddingValues ->


        if (serviceProductData.isLoading){

            LoadingScreen()

        }
        else if (serviceProductData.serviceProductsList.isNotEmpty()){

            LazyColumn(
                Modifier.padding(paddingValues),
                contentPadding = PaddingValues(13.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ){

                items(serviceProductData.serviceProductsList){
                    HorizontalProductCard(
                        serviceProductDataModel = it,
                        onClick = {
                            shareServiceDetailViewModel.setServiceDetails(it)
                            navHostController.navigate(HomeNavRoutes.PRODUCT_DETAIL)
                        }
                    )
                }

            }

        }

    }
}

