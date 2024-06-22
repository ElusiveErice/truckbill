package com.ark.truckbill.views.bill

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ark.truckbill.R
import com.ark.truckbill.YearMonthDayFormatter
import com.ark.truckbill.components.DatePickerDialog
import com.ark.truckbill.components.DatePickerMode
import com.ark.truckbill.components.TextInput
import java.time.LocalDate


@Composable
fun BillContent(
    billNameState: MutableState<String>,
    billWeightState: MutableState<Int>,
    billPriceState: MutableState<Int>,
    billStartState: MutableState<String>,
    billEndState: MutableState<String>,
    billStartDateState: MutableState<String>,
    billEndDateState: MutableState<String>
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
        if (input.isBlank()) {
            billPriceState.value = 0
        } else {
            billPriceState.value = input.toInt()
        }
    }
    val onBillWeightChange = { input: String ->
        if (input.isBlank()) {
            billWeightState.value = 0
        } else {
            billWeightState.value = input.toInt()
        }
    }
    val onBillStartDateChange = { input: String ->
        billStartDateState.value = input
    }
    val onBillEndDateChange = { input: String ->
        billEndDateState.value = input
    }

    val columnItemModifier = Modifier
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
            modifier = columnItemModifier
        ) {
            TextInput(
                label = stringResource(id = R.string.place_of_departure),
                value = billStartState.value,
                onChange = onBillStartChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            )
            TextInput(
                label = stringResource(id = R.string.destination),
                value = billEndState.value,
                onChange = onBillEndChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp)
            )
        }
        TextInput(
            label = stringResource(id = R.string.price),
            value = billPriceState.value.toString(),
            keyboardType = KeyboardType.Number,
            modifier = columnItemModifier,
            onChange = onBillPriceChange
        )
        TextInput(
            label = stringResource(id = R.string.weight),
            value = billWeightState.value.toString(),
            keyboardType = KeyboardType.Number,
            modifier = columnItemModifier,
            onChange = onBillWeightChange
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = columnItemModifier
        ) {
            DateInput(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                label = stringResource(id = R.string.start_date),
                value = billStartDateState.value,
                onChange = onBillStartDateChange
            )
            DateInput(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                label = stringResource(id = R.string.end_date),
                value = billEndDateState.value,
                onChange = onBillEndDateChange
            )
        }
    }
}


@Composable
fun DateInput(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onChange: (input: String) -> Unit
) {
    val dialogState = remember {
        mutableStateOf(false)
    }
    val onDismiss = { selected: Boolean, year: Int, month: Int, day: Int ->
        if (selected) {
            onChange(
                LocalDate.now().withYear(year).withMonth(month).withDayOfMonth(day)
                    .format(YearMonthDayFormatter)
            )
        }
        dialogState.value = false
    }
    val dateState = remember {
        mutableStateOf(LocalDate.now())
    }
    LaunchedEffect(value) {
        dateState.value = LocalDate.parse(value)
    }
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier.clickable {
            dialogState.value = true
        },
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity))
                .padding(10.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.subtitle2
            )
            Text(text = value)
            DatePickerDialog(
                onDismiss = onDismiss,
                mode = DatePickerMode.YearMonthDay,
                year = dateState.value.year,
                month = dateState.value.monthValue,
                day = dateState.value.dayOfMonth,
                dialogState = dialogState
            )
        }
    }
}
