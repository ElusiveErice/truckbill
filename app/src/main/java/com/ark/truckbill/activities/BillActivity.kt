package com.ark.truckbill.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ark.truckbill.ui.theme.TruckbillTheme
import com.ark.truckbill.views.bill.BillContent

class BillActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TruckbillTheme() {
                BillContent()
            }
        }
    }
}
