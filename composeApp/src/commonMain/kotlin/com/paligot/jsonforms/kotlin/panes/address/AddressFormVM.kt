package com.paligot.jsonforms.kotlin.panes.address

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddressFormVM(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddressFormViewModel = viewModel { AddressFormViewModel() }
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    AddressFormPane(
        uiModel = uiState.value,
        modifier = modifier,
        onStreetChange = viewModel::queryChange,
        onBackClick = onBackClick
    )
}
