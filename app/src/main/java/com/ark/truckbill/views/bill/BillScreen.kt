package com.ark.truckbill.views.bill

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ark.truckbill.R
import com.ark.truckbill.data.BillStatus

@Composable
fun BillScreen(navController: NavController) {
    var billStatus by remember {
        mutableStateOf(BillStatus.CHECK)
    }
    val buttonTitle =
        if (billStatus == BillStatus.CHECK) stringResource(id = R.string.edit)
        else stringResource(id = R.string.save)
    val onClick: () -> Unit = {
        when (billStatus) {
            BillStatus.CHECK -> {
                billStatus = BillStatus.MODIFY
            }
            BillStatus.ADD -> {
                billStatus = BillStatus.CHECK
            }
            BillStatus.MODIFY -> {
                billStatus = BillStatus.CHECK
            }
        }
    }
    val onBackClick: () -> Unit = {
        navController.popBackStack()
    }
    Scaffold(
        topBar = {
            TopAppBar {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, "back")
                    }
                    Text(
                        text = billStatus.title(),
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                    TextButton(
                        onClick = onClick,
                    ) {
                        Text(text = buttonTitle, color = Color.White)
                    }
                }
            }
        }
    ) {
        BillContent()
    }
}