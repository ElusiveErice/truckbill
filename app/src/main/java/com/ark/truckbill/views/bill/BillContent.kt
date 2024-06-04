package com.ark.truckbill.views.bill

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ark.truckbill.R


@Composable
fun BillContent(
    billNameState: MutableState<String>,
    billWeightState: MutableState<String>,
    billPriceState: MutableState<String>,
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
        billPriceState.value = input
    }
    val onBillHeightChange = { input: String ->
        billWeightState.value = input
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormItemView(
            label = stringResource(id = R.string.bill_name),
            value = billNameState.value,
            onChange = onBillNameChange
        )
        FormItemView(
            label = stringResource(id = R.string.place_of_departure),
            value = billStartState.value,
            onChange = onBillStartChange
        )
        FormItemView(
            label = stringResource(id = R.string.destination),
            value = billEndState.value,
            onChange = onBillEndChange
        )
        FormItemView(
            label = stringResource(id = R.string.price),
            value = billPriceState.value,
            keyboardType = KeyboardType.Number,
            onChange = onBillPriceChange
        )
        FormItemView(
            label = stringResource(id = R.string.weight),
            value = billWeightState.value,
            keyboardType = KeyboardType.Number,
            onChange = onBillHeightChange
        )
    }
}

@Composable
private fun FormItemView(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onChange: (value: String) -> Unit
) {
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
            modifier = Modifier.width(200.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}