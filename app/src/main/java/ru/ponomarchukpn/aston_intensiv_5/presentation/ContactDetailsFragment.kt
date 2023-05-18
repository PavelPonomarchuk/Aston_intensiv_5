package ru.ponomarchukpn.aston_intensiv_5.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.ponomarchukpn.aston_intensiv_5.R
import ru.ponomarchukpn.aston_intensiv_5.databinding.FragmentContactDetailsBinding
import ru.ponomarchukpn.aston_intensiv_5.repository.ContactsRepository

class ContactDetailsFragment : Fragment() {

    private var contactId = UNDEFINED_ID

    private var _binding: FragmentContactDetailsBinding? = null
    private val binding: FragmentContactDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentContactDetailsBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArguments()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
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
            binding.detailsNameContent.text = it.firstName
            binding.detailsSurnameContent.text = it.lastName
            binding.detailsPhoneContent.text = it.phoneNumber
            binding.detailsButtonEdit.setOnClickListener {
                launchEditFragment(contactId)
            }
        }
    }

    private fun launchEditFragment(contactId: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ContactEditFragment.newInstance(contactId))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        const val NAME = "ContactDetailsFragment"
        private const val KEY_CONTACT_ID = "contactId"
        private const val UNDEFINED_ID = 0

        fun newInstance(contactId: Int) = ContactDetailsFragment().apply {
            arguments = bundleOf(KEY_CONTACT_ID to contactId)
        }
    }
}
