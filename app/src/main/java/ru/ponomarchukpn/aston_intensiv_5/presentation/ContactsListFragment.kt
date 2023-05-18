package ru.ponomarchukpn.aston_intensiv_5.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.ponomarchukpn.aston_intensiv_5.R
import ru.ponomarchukpn.aston_intensiv_5.databinding.FragmentContactsListBinding
import ru.ponomarchukpn.aston_intensiv_5.entity.ContactItem
import ru.ponomarchukpn.aston_intensiv_5.repository.ContactsRepository

class ContactsListFragment : Fragment() {

    private var _binding: FragmentContactsListBinding? = null
    private val binding: FragmentContactsListBinding
        get() = _binding ?: throw RuntimeException("FragmentContactsListBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        showContacts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showContacts() {
        binding.mainLayoutContacts.removeAllViews()
        ContactsRepository.getList().forEach {
            showContactView(it)
        }
    }

    private fun showContactView(item: ContactItem) {
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_contact, binding.mainLayoutContacts, false)

        val textViewName = view.findViewById<TextView>(R.id.main_tv_full_name)
        val textViewPhone = view.findViewById<TextView>(R.id.main_tv_phone)
        textViewName.text = String.format("%s %s", item.firstName, item.lastName)
        textViewPhone.text = item.phoneNumber

        view.setOnClickListener {
            launchDetailsFragment(item.id)
        }
        binding.mainLayoutContacts.addView(view)
    }

    private fun launchDetailsFragment(id: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ContactDetailsFragment.newInstance(id))
            .addToBackStack(ContactDetailsFragment.NAME)
            .commit()
    }

    companion object {

        fun newInstance() = ContactsListFragment()
    }
}
