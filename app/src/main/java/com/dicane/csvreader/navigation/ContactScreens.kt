package com.dicane.csvreader.navigation

sealed class ContactScreens(val routeName: String) {
    object ContactList : ContactScreens("contact-list")

    object ContactDetail : ContactScreens("details/{detailsIndex}") {
        fun route(detailsIndex: Int) = "details/$detailsIndex"
    }

    object ContactEdition : ContactScreens("edition/{editIndex}") {
        fun route(editIndex: Int) = "edition/$editIndex"
    }
}
