package ru.ponomarchukpn.aston_intensiv_5.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.ponomarchukpn.aston_intensiv_5.R
import ru.ponomarchukpn.aston_intensiv_5.databinding.FragmentContactEditBinding
import ru.ponomarchukpn.aston_intensiv_5.entity.ContactItem
import ru.ponomarchukpn.aston_intensiv_5.repository.ContactsRepository

class ContactEditFragment : Fragment() {

    private var contactId = UNDEFINED_ID

    private var _binding: FragmentContactEditBinding? = null
    private val binding: FragmentContactEditBinding
        get() = _binding ?: throw RuntimeException("FragmentContactEditBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setValues()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArguments() {
        val args = requireArguments()

        if (!args.containsKey(KEY_CONTACT_ID)) {
            throw RuntimeException("Argument contact id is absent")
        }
        contactId = args.getInt(KEY_CONTACT_ID)
    }

    private fun setValues() {
        ContactsRepository.getContact(contactId)?.let {
            with(binding) {
                editName.setText(it.firstName)
                editSurname.setText(it.lastName)
                editPhone.setText(it.phoneNumber)
                editButtonSave.setOnClickListener {
                    onButtonSavePressed()
                }
            }
        }
    }

    private fun onButtonSavePressed() {
        val firstName = binding.editName.text.toString()
        val lastName = binding.editSurname.text.toString()
        val phoneNumber = binding.editPhone.text.toString()

        if (firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) {
            showRequireNotBlankToast()
        } else {
            updateContact(firstName, lastName, phoneNumber)
            launchContactsListFragment()
        }
    }

    private fun showRequireNotBlankToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.edit_is_blank_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateContact(firstName: String, lastName: String, phoneNumber: String) {
        ContactsRepository.updateContact(ContactItem(firstName, lastName, phoneNumber, contactId))
    }

    private fun launchContactsListFragment() {
        requireActivity().supportFragmentManager.popBackStack(
            ContactDetailsFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {

        private const val KEY_CONTACT_ID = "contactId"
        private const val UNDEFINED_ID = 0

        fun newInstance(contactId: Int) = ContactEditFragment().apply {
            arguments = bundleOf(KEY_CONTACT_ID to contactId)
        }
    }
}
