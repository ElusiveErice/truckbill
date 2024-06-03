package com.ark.truckbill.views.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.datetime.LocalDate


@Composable
fun HomepageContent(navController: NavController, currentDate: LocalDate, show: () -> Unit) {
    val onClickAddBill = {
        navController.navigate("bill")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = show)
            ) {
                Text(text = "${currentDate.year}年${currentDate.monthNumber}月")
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "expand"
                )
            }
            BillList(testBillList)
        }
        FloatingActionButton(
            onClick = onClickAddBill,
            modifier = Modifier
                .offset((-50).dp, (-100).dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, "add a bill")
        }
    }
}