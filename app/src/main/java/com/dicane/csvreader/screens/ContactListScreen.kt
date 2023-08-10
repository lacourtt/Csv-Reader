package com.dicane.csvreader.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.dicane.csvreader.compose.DefaultHeadLine
import com.dicane.csvreader.compose.defaultPadding
import com.dicane.csvreader.compose.ListItemTextTitle
import com.dicane.csvreader.model.Contact
import com.dicane.csvreader.navigation.ContactScreens
import com.dicane.csvreader.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun ContactListScreen(navController: NavHostController, viewModel: MainViewModel, activity: ComponentActivity) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                viewModel.setLoadingState()
                activity.lifecycleScope.launch {
                    viewModel.fetchData(activity)
                }
            },
        ) {
            Text("Read CSV")
        }
        val screenState = viewModel.contactsState.collectAsState().value
        when(screenState) {
            is ScreenState.Success -> {
                ContactsList(
                    navController = navController,
                    contacts = screenState.contacts
                )
            }
            is ScreenState.Error -> {
                ErrorScreen(activity, viewModel)
            }
            is ScreenState.Loading -> {
                LoadingScreen()
            }
            is ScreenState.Empty -> {
                EmptyScreen()
            }

        }
    }
}

@Composable
fun ContactsList(navController: NavHostController, contacts: List<Contact>) {
    LazyColumn {
        itemsIndexed(contacts) { index, contact ->
            ContactItem(
                navController = navController,
                contact = contact,
                index = index,
                lastIndex = contacts.lastIndex
            )
        }
    }
}

@Composable
fun ContactItem(navController: NavHostController, contact: Contact, index: Int, lastIndex: Int) {
    Column(
        modifier = Modifier.clickable {
            navController.navigate(ContactScreens.ContactDetail.route(index))
        },
        horizontalAlignment = Alignment.Start
    ) {
        contact.run {
            ListItemTextTitle(text = "$firstName $lastName")
            DefaultHeadLine(text = phone)
            DefaultHeadLine(text = "$city, $state")
        }
        if (index != lastIndex)
            Divider(
                modifier = Modifier.defaultPadding()
            )
    }
}

@Composable
fun ErrorScreen(activity: ComponentActivity, viewModel: MainViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "Error")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                    viewModel.setLoadingState()
                    viewModel.fetchData(activity)
                }
            ) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "The list is empty. \n\n Please, click on the button \nto read the CSV file.")
    }
}

