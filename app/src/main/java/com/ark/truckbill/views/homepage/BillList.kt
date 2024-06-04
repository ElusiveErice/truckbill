package com.ark.truckbill.views.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ark.truckbill.R
import com.ark.truckbill.data.BillStatus
import com.ark.truckbill.repository.entity.BillEntity
import com.ark.truckbill.ui.theme.BillItemBackground
import java.time.LocalDate

@Composable
fun BillList(navController: NavController, billList: List<BillEntity>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        billList.forEach { BillItem(navController, it) }
    }
}

@Composable
private fun BillItem(navController: NavController, bill: BillEntity) {
    val onClick = {
        navController.navigate("bill?billId=${bill.id}/${BillStatus.MODIFY.name}")
    }
    val stateDate = LocalDate.parse(bill.startDate)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        backgroundColor = BillItemBackground,
        shape = MaterialTheme.shapes.small
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = bill.start, fontSize = 24.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_right_arrow),
                        contentDescription = "right arrow",
                        modifier = Modifier.padding(16.dp, 0.dp)
                    )
                    Text(text = bill.end, fontSize = 24.sp)
                }
                Text(text = stringResource(id = R.string.some_yuan, bill.price))
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row() {
                    Text(text = bill.name)
                    Text(text = stringResource(id = R.string.some_dun, bill.weight))
                }
                Text(
                    text = stringResource(
                        id = R.string.year_month_day,
                        stateDate.year,
                        stateDate.monthValue,
                        stateDate.dayOfMonth
                    )
                )
            }
        }
    }

}
