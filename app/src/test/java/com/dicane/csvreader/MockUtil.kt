package com.dicane.csvreader

import com.dicane.csvreader.model.Contact

object MockUtil {
    val contact = Contact(null, "James", "Butt", "Benton, John B Jr", "6649 N Blue Gum St", "New Orleans", "Orleans", "LA", "70116", "504-621-8927", "504-845-1427", "jbutt@gmail.com")
    val contactList = listOf(
        Contact(null, "James", "Butt", "Benton, John B Jr", "6649 N Blue Gum St", "New Orleans", "Orleans", "LA", "70116", "504-621-8927", "504-845-1427", "jbutt@gmail.com"),
        Contact(null, "Josephine", "Darakjy", "Chanay, Jeffrey A Esq", "4 B Blue Ridge Blvd", "Brighton", "Livingston", "MI", "48116", "810-292-9388", "810-374-9840", "josephine_darakjy@darakjy.org"),
        Contact(null, "Art", "Venere", "Chemel, James L Cpa", "8 W Cerritos Ave #54", "Bridgeport", "Gloucester", "NJ", "8014", "856-636-8749", "856-264-4130", "art@venere.org")
    )
}