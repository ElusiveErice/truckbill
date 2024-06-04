package com.ark.truckbill.views.bill

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ark.truckbill.R


@Composable
fun BillContent(
    billNameState: MutableState<String>,
    billWeightState: MutableState<Int>,
    billPriceState: MutableState<Int>,
    billStartState: MutableState<String>,
    billEndState: MutableState<String>
) {
    val onBillNameChange = { input: String ->
        billNameState.value = input
    }
    val onBillStartChange = { input: String ->
        billStartState.value = input
    }
    val onBillEndChange = { input: String ->
        billEndState.value = input
    }
    val onBillPriceChange = { input: String ->
        billPriceState.value = if(input == "") 0 else input.toInt()
    }
    val onBillHeightChange = { input: String ->
        billWeightState.value = if(input == "") 0 else input.toInt()
}

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormItemView(
            stringResource(id = R.string.bill_name),
            billNameState.value,
            onBillNameChange
        )
        FormItemView(
            stringResource(id = R.string.place_of_departure),
            billStartState.value,
            onBillStartChange
        )
        FormItemView(
            stringResource(id = R.string.destination),
            billEndState.value,
            onBillEndChange
        )
        FormItemView(
            stringResource(id = R.string.price),
            billPriceState.value.toString(),
            onBillPriceChange
        )
        FormItemView(
            stringResource(id = R.string.weight),
            billWeightState.value.toString(),
            onBillHeightChange
        )
    }
}

@Composable
private fun FormItemView(label: String, value: String, onChange: (value: String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label, modifier = Modifier
                .width(100.dp)
                .padding(0.dp, 0.dp, 20.dp, 0.dp)
        )
        TextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier.width(200.dp)
        )
    }
}