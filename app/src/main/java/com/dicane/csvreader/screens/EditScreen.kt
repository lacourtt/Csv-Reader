package com.dicane.csvreader.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dicane.csvreader.compose.DefaultTextTitle
import com.dicane.csvreader.navigation.ContactScreens
import com.dicane.csvreader.viewmodel.MainViewModel

@Composable
fun EditScreen(navController: NavHostController, viewModel: MainViewModel, index: Int) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .verticalScroll(
                enabled = true,
                state = scrollState,
            )
            .padding(horizontal = 24.dp),
    ) {
        val contact = viewModel.getContact(index)

        val firstName = remember { mutableStateOf(TextFieldValue(contact.firstName)) }
        val lastName = remember { mutableStateOf(TextFieldValue(contact.lastName)) }
        val companyName = remember { mutableStateOf(TextFieldValue(contact.companyName)) }
        val email = remember { mutableStateOf(TextFieldValue(contact.email)) }
        val phone = remember { mutableStateOf(TextFieldValue(contact.phone)) }
        val phone1 = remember { mutableStateOf(TextFieldValue(contact.phone1)) }
        val address = remember { mutableStateOf(TextFieldValue(contact.address)) }
        val city = remember { mutableStateOf(TextFieldValue(contact.city)) }
        val county = remember { mutableStateOf(TextFieldValue(contact.county)) }
        val state = remember { mutableStateOf(TextFieldValue(contact.state)) }
        val zip = remember { mutableStateOf(TextFieldValue(contact.zip)) }

        contact.let {
            DefaultTextTitle(text = "Identification")
            Spacer(modifier = Modifier.height(8.dp))
            textFieldValue(firstName, "First Name")
            textFieldValue(lastName, "Last Name")
            textFieldValue(companyName, "Last Name")
            Spacer(modifier = Modifier.height(16.dp))
            DefaultTextTitle(text = "Contact")
            Spacer(modifier = Modifier.height(8.dp))
            textFieldValue(email, "Email")
            textFieldValue(phone, "Phone Number")
            textFieldValue(phone1, "Secondary Phone Number")
            Spacer(modifier = Modifier.height(16.dp))
            DefaultTextTitle(text = "Location")
            Spacer(modifier = Modifier.height(8.dp))
            textFieldValue(address, "Email")
            textFieldValue(city, "Email")
            textFieldValue(county, "Email")
            textFieldValue(state, "Email")
            textFieldValue(zip, "Email")
            Spacer(modifier = Modifier.height(16.dp))
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                viewModel.updateContact(index, contact.id, firstName.value, lastName.value, companyName.value, email.value, phone.value, phone1.value, address.value, city.value, county.value, state.value, zip.value)
                navController.navigate(ContactScreens.ContactList.routeName) {
                    popUpTo(ContactScreens.ContactList.routeName) {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun textFieldValue(
    value: MutableState<TextFieldValue>,
    title: String
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        value = value.value,
        onValueChange = {
            value.value = it
        },
        label = { Text(text = title) },
    )

}