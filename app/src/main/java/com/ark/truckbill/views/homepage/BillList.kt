package com.ark.truckbill.views.homepage

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
import com.ark.truckbill.R
import com.ark.truckbill.data.Bill
import com.ark.truckbill.ui.theme.BillItemBackground

val bill1 = Bill("棉花", 30f, 200f, "芜湖", "长沙")
val bill2 = Bill("棉花", 30f, 200f, "芜湖", "长沙")
val testBillList =
    mutableListOf<Bill>(bill1, bill2)

@Composable
fun BillList(billList: List<Bill>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        billList.forEach { BillItem(it) }
    }
}

@Composable
private fun BillItem(bill: Bill) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                Text(text = "${bill.price}元")
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row() {
                    Text(text = bill.name)
                    Text(text = "${bill.weight}吨")
                }
                Text(
                    text = stringResource(
                        id = R.string.year_month_day,
                        bill.startDate.year,
                        bill.startDate.monthNumber,
                        bill.startDate.dayOfMonth
                    )
                )
            }
        }
    }

}
