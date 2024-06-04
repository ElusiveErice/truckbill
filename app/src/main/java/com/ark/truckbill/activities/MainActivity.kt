package com.ark.truckbill.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ark.truckbill.exceptions.NullNavigationArgumentException
import com.ark.truckbill.ui.theme.TruckBillTheme
import com.ark.truckbill.views.bill.BillScreen
import com.ark.truckbill.views.homepage.HomepageScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TruckBillTheme {
                TruckBillNavigation()
            }
        }
    }

    @Composable
    private fun TruckBillNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "homepage") {
            composable("homepage") { HomepageScreen(navController) }
            composable(
                route = "bill?billId={billId}/{type}",
                arguments = listOf(navArgument("billId") {
                    defaultValue = 0
                    type = NavType.IntType
                })
            ) { entry ->
                val billId = entry.arguments?.getInt("billId")
                val type = entry.arguments?.getString("type")
                if (type != null) {
                    BillScreen(navController, billId, type)
                } else {
                    throw NullNavigationArgumentException()
                }
            }
        }
    }
}
