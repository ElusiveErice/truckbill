package com.ark.truckbill.views.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ark.truckbill.R
import com.ark.truckbill.components.YearMonthSelectModalLayout
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomepageScreen(navController: NavController) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val dateState = remember {
        mutableStateOf(LocalDate.now())
    }
    Scaffold(
        topBar = {
            TopAppBar() {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                }
            }
        }
    ) {
        YearMonthSelectModalLayout(
            sheetState = modalBottomSheetState,
            dateState = dateState,
            hide = { scope.launch { modalBottomSheetState.hide() } }
        ) {
            HomepageContent(
                navController = navController,
                currentDate = dateState.value,
                show = { scope.launch { modalBottomSheetState.show() } })
        }
    }
}