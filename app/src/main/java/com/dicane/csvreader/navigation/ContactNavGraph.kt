package com.dicane.csvreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dicane.csvreader.MainActivity
import com.dicane.csvreader.screens.ContactListScreen
import com.dicane.csvreader.screens.DetailsScreen
import com.dicane.csvreader.screens.EditScreen
import com.dicane.csvreader.viewmodel.MainViewModel

@Composable
fun ContactNavGraph(navController: NavHostController, viewModel: MainViewModel, activity: MainActivity) {
    NavHost(
        navController = navController,
        startDestination = ContactScreens.ContactList.routeName
    ) {
        composable(
            route = ContactScreens.ContactList.routeName
        ){
            ContactListScreen(
                navController = navController,
                viewModel = viewModel,
                activity = activity
            )
        }
        composable(
            route = ContactScreens.ContactDetail.routeName,
            arguments = listOf(navArgument("detailsIndex") {
                type = NavType.IntType
            })
        ){
            DetailsScreen(
                navController = navController,
                viewModel = viewModel,
                index = it.arguments!!.getInt("detailsIndex")
            )
        }
        composable(
            route = ContactScreens.ContactEdition.routeName,
            arguments = listOf(navArgument("editIndex") {
                type = NavType.IntType
            })
        ){
            EditScreen(
                navController = navController,
                viewModel = viewModel,
                index = it.arguments!!.getInt("editIndex")
            )
        }
    }
}