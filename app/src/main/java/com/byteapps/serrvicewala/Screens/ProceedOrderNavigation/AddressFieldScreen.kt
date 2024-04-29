package com.byteapps.serrvicewala.Screens.ProceedOrderNavigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Address.AddressViewModel
import com.byteapps.serrvicewala.Address.UserAddressDataModel
import com.byteapps.serrvicewala.UIComponents.CommonInputField
import com.byteapps.serrvicewala.UIComponents.FullWidthButton
import com.byteapps.serrvicewala.Utils.NavigationRoutes.HomeNavRoutes
import com.byteapps.serrvicewala.Utils.ResultState
import com.byteapps.serrvicewala.Validations.AddressFormValidationViewModel
import kotlinx.coroutines.launch
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressFieldScreen(
    navHostController: NavHostController,
    addressViewModel: AddressViewModel,
    addressFormValidationViewModel: AddressFormValidationViewModel
) {

    val scope = rememberCoroutineScope()

    val name = addressFormValidationViewModel.name.collectAsState().value
    val mobile = addressFormValidationViewModel.mobile.collectAsState().value
    val pinCode = addressFormValidationViewModel.pinCode.collectAsState().value
    val state = addressFormValidationViewModel.state.collectAsState().value
    val city = addressFormValidationViewModel.city.collectAsState().value
    val building = addressFormValidationViewModel.building.collectAsState().value
    val area = addressFormValidationViewModel.area.collectAsState().value
    val type = addressFormValidationViewModel.type.collectAsState().value


    Scaffold (
        topBar = {
           TopAppBar(
               title = { Text(text = "Address") },
               navigationIcon = {
                   IconButton(onClick = { navHostController.popBackStack() }) {
                       Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                   }
               },
               colors = TopAppBarDefaults.topAppBarColors(
                   containerColor = Color.White
               )
           )
        }
    ){ paddingValues ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)

        ) {

            Column(modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 15.dp)
            ) {

                Spacer(modifier = Modifier.padding(6.dp))

                CommonInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Full Name",
                    lable = "Full Name",
                    textValue = {addressFormValidationViewModel._name.value = it},
                    keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(modifier = Modifier.padding(6.dp))

                CommonInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Mobile",
                    lable = "Mobile Number",
                    textValue = {addressFormValidationViewModel._mobile.value = it},
                    keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                Spacer(modifier = Modifier.padding(6.dp))

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

                    CommonInputField(
                        modifier = Modifier.weight(1f),
                        placeholder = "Pin Code",
                        lable = "Pin Code",
                        textValue = {addressFormValidationViewModel._pinCode.value = it},
                        keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.padding(6.dp))

                    TextButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )

                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.location_crosshairs),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                            Text(text = "Get Location", color = Color.White)
                        }

                    }
                }

                Spacer(modifier = Modifier.padding(6.dp))

                Row {
                    CommonInputField(
                        modifier = Modifier.weight(1f),
                        placeholder = "State",
                        lable = "State",
                        textValue = {addressFormValidationViewModel._state.value = it},
                        keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    Spacer(modifier = Modifier.padding(6.dp))

                    CommonInputField(
                        modifier = Modifier.weight(1f),
                        placeholder = "City",
                        lable = "City",
                        textValue = {addressFormValidationViewModel._city.value = it},
                        keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }

                Spacer(modifier = Modifier.padding(6.dp))

                CommonInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Building or House no",
                    lable = "Building or House no",
                    textValue = {addressFormValidationViewModel._building.value = it},
                    keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(modifier = Modifier.padding(6.dp))

                CommonInputField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Area",
                    lable = "Area",
                    textValue = {addressFormValidationViewModel._area.value = it},
                    keyBoardOption = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(modifier = Modifier.padding(6.dp))

                var selectedValue by remember {
                    mutableStateOf("")
                }
                LazyRow(content = {
                    items(buttonList) {
                        CommonTextButton(
                            icon = it.icon,
                            text = it.text,
                            selectedValue = selectedValue,
                            onClick = {
                                selectedValue = it
                                addressFormValidationViewModel._type.value = selectedValue
                            })

                    }
                })

            }

           FullWidthButton(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(16.dp)
                   .height(48.dp),
               text = "Add address",
               textColor = Color.White,
               color = MaterialTheme.colorScheme.primary,
               enabled = addressFormValidationViewModel.isValidated(
                   name = name,
                   mobile = mobile,
                   pinCode = pinCode,
                   state = state,
                   city = city,
                   building = building,
                   area = area,
                   type = type
               ),
               onClick = {
                   scope.launch {
                       addressViewModel.setUserAddress(
                           UserAddressDataModel(
                               name = name,
                               mobile = mobile,
                               pinCode = pinCode ,
                               state = state,
                               city = city,
                               building = building ,
                               area = area,
                               type = type,
                               addressId = UUID.randomUUID().toString()
                           )
                       ).collect{
                           when(it){
                               is ResultState.Loading->{

                               }
                               is ResultState.Success->{
                                   addressViewModel.getAddress()
                                   navHostController.navigate(HomeNavRoutes.PROCEED_ORDER_DETAIL)
                               }
                               is ResultState.Error->{

                               }
                           }
                       }
                   }

               }
           )


        }
    }


    }



@Composable
fun CommonTextButton(modifier: Modifier = Modifier, icon: Int, text: String,selectedValue : String, onClick: (String) -> Unit) {

    TextButton(
        onClick = { onClick(text) },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.padding(end = 20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        )
        , border = BorderStroke(width = 1.dp, color = if (text == selectedValue) MaterialTheme.colorScheme.primary else Color.Transparent)

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(text = "$text", color = MaterialTheme.colorScheme.onPrimary)
        }

    }

}




data class BuildingOption(
    val text: String,
    val icon: Int
)

val buttonList = mutableListOf<BuildingOption>(
    BuildingOption("Home", R.drawable.home_bolde_1),
    BuildingOption("Office", R.drawable.city),
)
