package com.paligot.jsonforms.kotlin

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.paligot.jsonforms.kotlin.panes.AccountCreationFormPane
import com.paligot.jsonforms.kotlin.panes.AppleForm
import com.paligot.jsonforms.kotlin.panes.ContactFormPane
import com.paligot.jsonforms.kotlin.panes.FormListPane
import com.paligot.jsonforms.kotlin.panes.LoginFormPane
import com.paligot.jsonforms.kotlin.panes.address.AddressFormVM
import kotlinx.serialization.Serializable

@Serializable
object FormList

@Serializable
object AccountCreation

@Serializable
object Login

@Serializable
object Contact

@Serializable
object Address

@Serializable
object AppleForm

fun NavGraphBuilder.formDescriptionNavGraph(navController: NavController) {
    composable<FormList> {
        FormListPane(
            onAccountCreationClick = { navController.navigate(AccountCreation) },
            onLogInClick = { navController.navigate(Login) },
            onContactClick = { navController.navigate(Contact) },
            onAddressClick = { navController.navigate(Address) },
            onAppleClick = { navController.navigate(AppleForm) },
        )
    }
    composable<AccountCreation> {
        AccountCreationFormPane {
            navController.popBackStack()
        }
    }
    composable<Login> {
        LoginFormPane {
            navController.popBackStack()
        }
    }
    composable<Contact> {
        ContactFormPane {
            navController.popBackStack()
        }
    }
    composable<Address> {
        AddressFormVM(onBackClick = {
            navController.popBackStack()
        })
    }
    composable<AppleForm> {
        AppleForm {
            navController.popBackStack()
        }
    }
}
