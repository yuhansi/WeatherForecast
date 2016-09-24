package com.example.android.sunshine.app;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by hanssi on 9/24/16.
 */

/**
 * A PreferenceActivity that presents a set of application settings
 */
public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add general preferences
        addPreferencesFromResource(R.xml.pref_general);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)));
    }

    /**
     * Attaches a listener so the summary is always updated with the preference value
     * Fires the listener once to initialize the summary (so that it shows up before any changes in preference value)
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for changes in current preference value
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's current value
        onPreferenceChange(preference, PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if(preference instanceof ListPreference) {
            // For list preferences, loop up the current display value in the
            // preference's 'entries' list
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if(prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        else {
            // For other preferences, set the summary to the value's string representation
            preference.setSummary(stringValue);
        }
        return true;
    }
}
