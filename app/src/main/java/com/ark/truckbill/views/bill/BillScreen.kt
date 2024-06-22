package com.ark.truckbill.views.bill

import android.util.Log
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
import com.ark.truckbill.YearMonthDayFormatter
import com.ark.truckbill.data.BillStatus
import com.ark.truckbill.repository.BillDataBase
import com.ark.truckbill.repository.entity.BillEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun BillScreen(navController: NavController, billId: Int?, type: String) {
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
    val billStartDateState = remember {
        mutableStateOf(LocalDate.now().format(YearMonthDayFormatter))
    }
    val billEndDateState = remember {
        mutableStateOf(LocalDate.now().format(YearMonthDayFormatter))
    }

    val context = LocalContext.current
    val dao = BillDataBase.getDB(context).getBillDao()

    LaunchedEffect(billId, type) {
        billStatus = BillStatus.valueOf(type)
        if (billStatus == BillStatus.MODIFY && billId != null) {
            billStatus = BillStatus.MODIFY
            CoroutineScope(Dispatchers.IO).launch {
                val bill = dao.getBillWithId(billId)
                if (bill != null) {
                    billNameState.value = bill.name
                    billWeightState.value = bill.weight
                    billStartState.value = bill.start
                    billEndState.value = bill.end
                    billPriceState.value = bill.price
                    billStartDateState.value = bill.startDate
                    billEndDateState.value = bill.endDate
                }
            }
        }
    }

    val buildBillEntity = {
        BillEntity(
            id = billId ?: 0,
            name = billNameState.value,
            weight = billWeightState.value,
            price = billPriceState.value,
            start = billStartState.value,
            end = billEndState.value,
            startDate = billStartDateState.value,
            endDate = billEndDateState.value
        )
    }
    val onSave = {
        val billEntity = buildBillEntity()
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertBill(billEntity)
        }
    }
    val onUpdate = {
        val billEntity = billId?.let { buildBillEntity() }
        CoroutineScope(Dispatchers.IO).launch {
            if (billEntity != null) {
                dao.updateBill(billEntity)
            }
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
                onUpdate()
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
        BillContent(
            billNameState,
            billWeightState,
            billPriceState,
            billStartState,
            billEndState,
            billStartDateState,
            billEndDateState
        )
    }
}