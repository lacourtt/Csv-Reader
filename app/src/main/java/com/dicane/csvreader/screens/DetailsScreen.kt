package com.dicane.csvreader.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dicane.csvreader.compose.DefaultTextTitle
import com.dicane.csvreader.navigation.ContactScreens
import com.dicane.csvreader.viewmodel.MainViewModel

@Composable
fun DetailsScreen(navController: NavHostController, viewModel: MainViewModel, index: Int) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        viewModel.getContact(index).run {
            DefaultTextTitle(text = "Name and Company")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$firstName $lastName")
            Text(text = companyName)
            Spacer(modifier = Modifier.height(16.dp))
            DefaultTextTitle(text = "Contact")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = email)
            Text(text = phone)
            Text(text = phone1)
            Spacer(modifier = Modifier.height(16.dp))
            DefaultTextTitle(text = "Address")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = address)
            Text(text = city)
            Text(text = county)
            Text(text = state)
            Text(text = zip)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ){
            IconButton(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50.dp),
                    )
                    .size(54.dp),
                onClick = {
                    navController.navigate(ContactScreens.ContactEdition.route(index))
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White,
                )
            }
        }
    }
}