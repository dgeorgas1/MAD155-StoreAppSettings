package com.example.storeappsettings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    lateinit var firstNameEntry: EditText
    lateinit var lastNameEntry: EditText
    lateinit var birthdayEntry: EditText
    lateinit var phoneNumberEntry: EditText
    lateinit var emailAddressEntry: EditText

    lateinit var genderSpinner: Spinner

    lateinit var resetButton: Button

    private var genderSelected: String = ""
    private  var spinnerPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val genderSpinnerOptions = listOf("", "Female", "Male")

        firstNameEntry = findViewById(R.id.editTextFirstNameEntry)
        lastNameEntry = findViewById(R.id.editTextLastNameEntry)
        birthdayEntry = findViewById(R.id.editTextBirthdayEntry)
        phoneNumberEntry = findViewById(R.id.editTextPhoneNumberEntry)
        emailAddressEntry = findViewById(R.id.editTextEmailAddressEntry)

        genderSpinner = findViewById(R.id.spinnerGender)

        resetButton = findViewById(R.id.buttonReset)

        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genderSpinnerOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
        genderSpinner.adapter = adapter

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                genderSelected = genderSpinner.getItemAtPosition(position).toString()
                spinnerPosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        resetButton.setOnClickListener {
            firstNameEntry.requestFocus()

            firstNameEntry.setText("")
            lastNameEntry.setText("")
            birthdayEntry.setText("")
            phoneNumberEntry.setText("")
            emailAddressEntry.setText("")

            genderSpinner.setSelection(0)
        }
    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)

        val firstNameKey = sharedPreferences.getString("firstNameEntry", "")
        val lastNameKey = sharedPreferences.getString("lastNameEntry", "")
        val birthdayKey = sharedPreferences.getString("birthdayEntry", "")
        val phoneNumberKey = sharedPreferences.getString("phoneNumberEntry", "")
        val emailAddressKey = sharedPreferences.getString("emailAddressEntry", "")

        val spinnerSelectedPosition = sharedPreferences.getInt("selectedPosition", 0)

        firstNameEntry.setText(firstNameKey)
        lastNameEntry.setText(lastNameKey)
        birthdayEntry.setText(birthdayKey)
        phoneNumberEntry.setText(phoneNumberKey)
        emailAddressEntry.setText(emailAddressKey)

        genderSpinner.setSelection(spinnerSelectedPosition)
    }

    override fun onPause() {
        super.onPause()

        val sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
        val write = sharedPreferences.edit()

        write.putString("firstNameEntry", firstNameEntry.text.toString())
        write.putString("lastNameEntry", lastNameEntry.text.toString())
        write.putString("birthdayEntry", birthdayEntry.text.toString())
        write.putString("phoneNumberEntry", phoneNumberEntry.text.toString())
        write.putString("emailAddressEntry", emailAddressEntry.text.toString())

        write.putInt("selectedPosition", spinnerPosition)

        write.apply()
    }
}