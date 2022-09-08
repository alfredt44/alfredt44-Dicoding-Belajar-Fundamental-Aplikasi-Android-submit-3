
package com.dicoding.submissionbfaa3.ui.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.preference.*
import com.dicoding.submissionbfaa3.R
import com.dicoding.submissionbfaa3.alarm.AlarmReceiver


class SettingsFragment : PreferenceFragmentCompat(){

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        findPreference<Preference>("language")?.setOnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
        val notifier = findPreference<SwitchPreference>(getString(R.string.key_notification))
        notifier?.setOnPreferenceChangeListener { _, newValue ->
            when(newValue){
                true -> AlarmReceiver().setRepeatingAlarm(requireContext().applicationContext)
                false -> AlarmReceiver().cancelAlarm(requireContext().applicationContext)
            }
            true
        }
    }



}