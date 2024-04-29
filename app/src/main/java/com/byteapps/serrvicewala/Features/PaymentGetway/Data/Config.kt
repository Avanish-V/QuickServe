package com.byteapps.serrvicewala.Features.PaymentGetway.Data

import com.cashfree.pg.core.api.CFSession
import com.cashfree.pg.core.api.upi.CFUPI

data class Config(
    // CFSession Inputs
    val orderID: String = "order235689",
    val paymentSessionID: String = "session_7NvteR73Fh11P3f3bNdcubIAJgBJJgGK9diC6U5jvr_jfWBS8o-Z2iPf20diqBMVfWDwvARGrISZRCPoDSWjw4Eb1GrKtoZZQT_BWyXW25fD",
    val environment: CFSession.Environment = CFSession.Environment.SANDBOX,

    //Card Payment Inputs
    val cardNumber: String = "4585340002077590",
    val cardMM: String = "09",
    val cardYY: String = "26",
    val cardHolderName: String = "John Doe",
    val cardCVV: String = "850",

    // Card EMI Inputs
    val bankName: String = "Axis Bank",
    val emiTenure: Int = 3,

    //UPI Collect mode
    val collectMode: CFUPI.Mode = CFUPI.Mode.COLLECT,
    val upiVpa: String = "testsuccess@gocash",

    // UPI Intent mode
    val intentMode: CFUPI.Mode = CFUPI.Mode.INTENT,
    val upiAppPackage: String = "com.google.android.apps.nbu.paisa.user",

    //Wallet mode
    val channel: String = "phonepe",
    val phone: String = "9999999999",

    //Pay later mode
    val payLaterChannel: String = "lazypay",

    // Net Banking mode
    val bankCode: Int = 3003
) 