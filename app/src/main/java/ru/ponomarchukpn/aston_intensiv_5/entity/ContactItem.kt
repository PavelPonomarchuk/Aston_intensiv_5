package ru.ponomarchukpn.aston_intensiv_5.entity

data class ContactItem(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    var id: Int = 0
)
