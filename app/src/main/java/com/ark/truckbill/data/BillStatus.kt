package com.ark.truckbill.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ark.truckbill.R

enum class BillStatus {
    CHECK {
        @Composable
        override fun title(): String {
            return stringResource(id = R.string.check_bill)
        }
    },
    ADD {
        @Composable
        override fun title(): String {
            return stringResource(id = R.string.add_bill)
        }
    },
    MODIFY {
        @Composable
        override fun title(): String {
            return stringResource(id = R.string.modify_bill)
        }
    };

    @Composable
    abstract fun title(): String
}
