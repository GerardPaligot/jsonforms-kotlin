package com.paligot.jsonforms.kotlin.panes.address

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddressFormVM(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddressFormViewModel = viewModel { AddressFormViewModel() },
) {
    val uiState = viewModel.uiState.collectAsState()
    AddressFormPane(
        uiModel = uiState.value,
        modifier = modifier,
        onStreetChange = viewModel::queryChange,
        onBackClick = onBackClick,
    )
}
