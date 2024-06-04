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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ark.truckbill.R
import com.ark.truckbill.data.BillStatus
import com.ark.truckbill.repository.BillDataBase
import com.ark.truckbill.repository.entity.BillEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BillScreen(navController: NavController) {
    var billStatus by remember {
        mutableStateOf(BillStatus.ADD)
    }
    val billNameState = remember {
        mutableStateOf("")
    }
    val billStartState = remember {
        mutableStateOf("")
    }
    val billEndState = remember {
        mutableStateOf("")
    }
    val billPriceState = remember {
        mutableStateOf(0)
    }
    val billWeightState = remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val dao = BillDataBase.getDB(context).getBillDao()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val onSave = {
        val billEntity = BillEntity(
            name = billNameState.value,
            weight = billWeightState.value,
            price = billPriceState.value,
            start = billStartState.value,
            end = billEndState.value,
            startDate = LocalDate.now().format(formatter),
            endDate = LocalDate.now().format(formatter)
        )
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertBill(billEntity)
        }
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
                onSave()
                navController.popBackStack()
            }
            BillStatus.MODIFY -> {
                onSave()
                navController.popBackStack()
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
        BillContent(billNameState, billPriceState, billWeightState, billStartState, billEndState)
    }
}