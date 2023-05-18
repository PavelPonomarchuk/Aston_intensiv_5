package ru.ponomarchukpn.aston_intensiv_5.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.ponomarchukpn.aston_intensiv_5.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            launchStartFragment()
        }
    }

    private fun launchStartFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, ContactsListFragment.newInstance())
            .commit()
    }
}
