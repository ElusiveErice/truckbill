package com.ark.truckbill.views.bill

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ark.truckbill.R
import com.ark.truckbill.components.TextInput


@Composable
fun BillContent(
    billNameState: MutableState<String>,
    billWeightState: MutableState<String>,
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
        if (input.isEmpty()) {
            billPriceState.value = 0
        } else {
            billPriceState.value = input.toInt()
        }
    }
    val onBillHeightChange = { input: String ->
        billWeightState.value = input
    }

    val inputModifier = Modifier
        .fillMaxWidth()
        .padding(20.dp, 10.dp, 20.dp, 10.dp)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            label = stringResource(id = R.string.bill_name),
            value = billNameState.value,
            onChange = onBillNameChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(20.dp, 10.dp, 20.dp, 10.dp)
        ) {
            TextInput(
                label = stringResource(id = R.string.place_of_departure),
                value = billStartState.value,
                onChange = onBillStartChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp, 0.dp, 10.dp, 0.dp)
            )
            TextInput(
                label = stringResource(id = R.string.destination),
                value = billEndState.value,
                onChange = onBillEndChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
            )
        }
        TextInput(
            label = stringResource(id = R.string.price),
            value = billPriceState.value.toString(),
            keyboardType = KeyboardType.Number,
            modifier = inputModifier,
            onChange = onBillPriceChange
        )
        TextInput(
            label = stringResource(id = R.string.weight),
            value = billWeightState.value,
            keyboardType = KeyboardType.Number,
            modifier = inputModifier,
            onChange = onBillHeightChange
        )
    }
}
