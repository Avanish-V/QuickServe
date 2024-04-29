package com.byteapps.serrvicewala.Features.PaymentGetway.Presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import com.byteapps.QuickServe.R
import com.byteapps.serrvicewala.Address.UserAddressDataModel
import com.byteapps.serrvicewala.Features.Orders.OrdersViewModel
import com.byteapps.serrvicewala.Features.Orders.data.DateTime
import com.byteapps.serrvicewala.Features.Orders.data.OrdersDataModel
import com.byteapps.serrvicewala.Features.Orders.data.ProfessionalDetails
import com.byteapps.serrvicewala.Features.Orders.data.ServiceInfo
import com.byteapps.serrvicewala.Features.Orders.data.Status
import com.byteapps.serrvicewala.Features.Orders.data.Tracking
import com.byteapps.serrvicewala.Features.PaymentGetway.Data.CreateOrderDataModel
import com.byteapps.serrvicewala.Features.PaymentGetway.Data.CustomerDetails
import com.byteapps.serrvicewala.Features.PriceCalculation.PriceDetails
import com.byteapps.serrvicewala.Features.ServiceCategories.Data.Coupon
import com.byteapps.serrvicewala.SharedViewModel.ParcePaymentDataViewModel
import com.byteapps.serrvicewala.UIComponents.LoadingScreen
import com.byteapps.serrvicewala.Utils.ResultState
import com.byteapps.serrvicewala.ui.theme.ServizTheme
import com.cashfree.pg.api.CFPaymentGatewayService
import com.cashfree.pg.base.exception.CFException
import com.cashfree.pg.core.api.CFSession
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback
import com.cashfree.pg.core.api.utils.CFErrorResponse
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutPayment
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutTheme
import com.cashfree.pg.ui.api.upi.intent.CFIntentTheme
import com.cashfree.pg.ui.api.upi.intent.CFUPIIntentCheckout
import com.cashfree.pg.ui.api.upi.intent.CFUPIIntentCheckoutPayment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Arrays
import kotlin.random.Random

@AndroidEntryPoint
class CashFreePayment : ComponentActivity(),CFCheckoutResponseCallback {

    var cfEnvironment = CFSession.Environment.SANDBOX
    val startPaymentViewModel : StartPaymentViewModel by viewModels()
    val ordersViewModel: OrdersViewModel by viewModels()
    private lateinit var parcePaymentDataViewModel: ParcePaymentDataViewModel

    var status : Boolean = false

    var title: String? = null
    var service_id: String? = null
    var date: String? = null
    var time: String? = null
    var name: String? = null
    var mobile: String? = null
    var pin_code: String? = null
    var state: String? = null
    var city: String? = null
    var building: String? = null
    var area: String? = null
    var type: String? = null
    var price: Int? = null
    var tax: Int? = null
    var total: String? = null
    var discount: String? = null
    var quantity: String? = null
    var coupon_code: String? = null
    var coupon_title: String? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parcePaymentDataViewModel = ViewModelProvider(this).get(ParcePaymentDataViewModel::class.java)

        title = intent.getStringExtra("TITLE")
        service_id = intent.getStringExtra("SERVICE_ID")
        date = intent.getStringExtra("DATE")
        time = intent.getStringExtra("TIME")
        name = intent.getStringExtra("NAME")
        mobile = intent.getStringExtra("MOBILE")
        pin_code = intent.getStringExtra("PIN_CODE")
        state = intent.getStringExtra("STATE")
        city = intent.getStringExtra("CITY")
        building = intent.getStringExtra("BUILDING")
        area = intent.getStringExtra("AREA")
        type = intent.getStringExtra("TYPE")
        price = intent.getStringExtra("PRICE")?.toInt()
        tax = intent.getStringExtra("TAX")?.toInt()
        total = intent.getStringExtra("TOTAL")
        discount = intent.getStringExtra("DISCOUNT")
        quantity = intent.getStringExtra("QUANTITY")
        coupon_code = intent.getStringExtra("COUPON_CODE")
        coupon_title = intent.getStringExtra("COUPON_TITLE")


        try {

            CFPaymentGatewayService.getInstance().setCheckoutCallback(this)
        } catch (e: CFException) {
            e.printStackTrace()
        }


        setContent {


            val scope = rememberCoroutineScope()


            var isProgressLoading by remember {
                mutableStateOf(false)
            }


            ServizTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    if (isProgressLoading) LoadingScreen()

                    if (status) OrderStatus()

                    LaunchedEffect(Unit){

                        scope.launch {
                            startPaymentViewModel.createOrder(
                                CreateOrderDataModel(
                                    orderAmount = 100.0,
                                    orderCurrency = "INR",
                                    orderId = generateOrderId().toString(),
                                    customerDetails = CustomerDetails(
                                        customerId = "235689",
                                        customerName = "Avanihs",
                                        customerEmail ="avanish@gamil.com",
                                        customerPhone = "8787040661"
                                    )

                                )
                            ).collect{
                                when(it){
                                    is ResultState.Loading->{
                                        isProgressLoading = true
                                    }
                                    is ResultState.Success->{

                                        doWebCheckoutPayment(
                                            sessionId = it.data?.paymentSessionId ?: "",
                                            orderID = it.data?.orderId ?: "",
                                        )

//                                        doDropCheckoutPayment(
//                                            sessionId = it.data?.paymentSessionId ?: "",
//                                            orderID = it.data?.orderId ?: "",
//                                        )

                                        isProgressLoading = false
                                    }
                                    is ResultState.Error->{
                                        isProgressLoading = false
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


    }


    override fun onPaymentVerify(orderID: String?) {



        CoroutineScope(Dispatchers.IO).launch {


            ordersViewModel.setOrder(
                OrdersDataModel(
                    orderId = orderID.toString(),
                    userUUID = FirebaseAuth.getInstance().currentUser?.uid ?: "null",
                    serviceInfo =
                        ServiceInfo(
                            serviceTitle = title!!,
                            serviceId = service_id.toString()
                        )
                    ,
                    priceTag = PriceDetails(
                        price = price?.toInt() ?: 0,
                        tax = tax?.toInt() ?: 0,
                        quantity = quantity?.toInt() ?: 0,
                        total = total?.toInt() ?: 0,
                        coupon = Coupon(
                            offerTitle = coupon_title.toString(),
                            couponCode = coupon_code.toString(),
                            discount = discount?.toInt() ?: 0
                        )

                    ),
                    address = UserAddressDataModel(
                        name = name.toString(),
                        mobile = mobile.toString(),
                        pinCode = pin_code.toString(),
                        state = state.toString(),
                        city = city.toString(),
                        building = building.toString(),
                        area = area.toString(),
                        type = type.toString(),
                        addressId = ""

                    ),

                    dateTime = DateTime(
                        date = date.toString(),
                        time = time.toString()
                    ),
                    tracking = Tracking.PLACED,

                    status = Status.ACTIVE,
                    professionalID = "235689",
                    professionalDetail = ProfessionalDetails(

                    )

                )
            ).collect{
                when(it){
                    is ResultState.Loading->{

                    }
                    is ResultState.Success->{
                       status = true
                    }
                    is ResultState.Error->{

                        Toast.makeText(applicationContext,it.message,Toast.LENGTH_LONG).show()

                    }
                }
            }
        }

    }

    override fun onPaymentFailure(cfErrorResponse: CFErrorResponse?, orderID: String?) {


    }



    fun doDropCheckoutPayment(sessionId:String,orderID: String) {

        try {
            val cfSession = CFSession.CFSessionBuilder()
                .setEnvironment(cfEnvironment)
                .setPaymentSessionID(sessionId)
                .setOrderId(orderID)
                .build()
            val cfTheme = CFIntentTheme.CFIntentThemeBuilder()
                .setPrimaryTextColor("#FFFFFF")
                .setBackgroundColor("#4169E1")
                .build()

            val cfupiIntentCheckout = CFUPIIntentCheckout.CFUPIIntentBuilder()
                .setOrder(Arrays.asList(CFUPIIntentCheckout.CFUPIApps.BHIM, CFUPIIntentCheckout.CFUPIApps.PHONEPE,CFUPIIntentCheckout.CFUPIApps.GOOGLE_PAY))
                .build()

            val cfupiIntentCheckoutPayment = CFUPIIntentCheckoutPayment.CFUPIIntentPaymentBuilder()
                .setSession(cfSession)
                .setCfUPIIntentCheckout(cfupiIntentCheckout)
                .setCfIntentTheme(cfTheme)
                .build()
            CFPaymentGatewayService.getInstance().doPayment(this, cfupiIntentCheckoutPayment)
        } catch (exception: CFException) {
            exception.printStackTrace()
        }
    }

    fun doWebCheckoutPayment(sessionId:String,orderID: String) {

        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this)
        } catch (e: CFException) {
            e.printStackTrace()
        }

        try {
            val cfSession = CFSession.CFSessionBuilder()
                .setEnvironment(cfEnvironment)
                .setPaymentSessionID(sessionId)
                .setOrderId(orderID)
                .build()
            val cfTheme = CFWebCheckoutTheme.CFWebCheckoutThemeBuilder()
                .setNavigationBarBackgroundColor("#000000")
                .setNavigationBarTextColor("#FFFFFF")
                .build()
            val cfWebCheckoutPayment = CFWebCheckoutPayment.CFWebCheckoutPaymentBuilder()
                .setSession(cfSession)
                .setCFWebCheckoutUITheme(cfTheme)
                .build()
            CFPaymentGatewayService.getInstance().doPayment(this, cfWebCheckoutPayment)
        } catch (exception: CFException) {
            exception.printStackTrace()
        }
    }

    private fun generateOrderId(): Int {
        // Generate a random number between 10000 and 99999 (inclusive)
        return Random.nextInt(10000, 100000)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OrderStatus() {
    
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.check),
            contentDescription = null
        )

        Text(

            text = "Successfully ordered!",
            style = MaterialTheme.typography.displayMedium
        )

        Text(
            text = "Order Id 1235689",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.displaySmall
        )


    }
    

}

