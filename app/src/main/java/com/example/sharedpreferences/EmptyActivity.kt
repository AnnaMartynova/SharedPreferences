package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharedpreferences.databinding.ActivityEmptyBinding

const val APP_PREFERENCES = "APP_PREFERENCES"
const val SP_KEY = "SP_KEY"

class EmptyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmptyBinding

    private lateinit var preferences: SharedPreferences

    private val preferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { preferences, key ->
        if (key == SP_KEY){
            binding.currentValueSharedPreferences.text = preferences.getString(key,"")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyBinding.inflate(layoutInflater).also { setContentView(it.root) }

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val currentValue = preferences.getString(SP_KEY, "")

        binding.valueEditText.setText(currentValue)
        binding.currentValueSharedPreferences.text = currentValue

        binding.saveButton.setOnClickListener {
            val value = binding.valueEditText.text.toString()
            preferences.edit()
                .putString(SP_KEY, value)
                .apply()
        }

        preferences.registerOnSharedPreferenceChangeListener(preferencesListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.unregisterOnSharedPreferenceChangeListener(preferencesListener)
    }
}