package ru.ponomarchukpn.aston_intensiv_5.repository

import ru.ponomarchukpn.aston_intensiv_5.entity.ContactItem

object ContactsRepository {

    private val contactsList = mutableListOf<ContactItem>()

    init {
        val values = listOf(
            ContactItem("Иван", "Иванов", "84951234455", 1),
            ContactItem("Петр", "Петров", "81111231122", 2),
            ContactItem("Сидор", "Сидоров", "82221233311", 3),
            ContactItem("Михаил", "Михайлов", "83331234422", 4)
        )
        contactsList.addAll(values)
    }

    fun getList() = contactsList.sortedBy { it.firstName }

    fun getContact(contactId: Int) = contactsList.find { it.id == contactId }

    fun updateContact(contact: ContactItem) {
        contactsList.find { it.id == contact.id }?.let {
            contactsList.remove(it)
            contactsList.add(contact)
        }
    }
}
