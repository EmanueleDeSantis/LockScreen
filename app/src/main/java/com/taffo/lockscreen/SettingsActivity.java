/*
   LockScreen, an Android lockscreen for people with perfect pitch
   Copyright (C) 2021  Emanuele De Santis

   LockScreen is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License as published
   by the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   LockScreen is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with LockScreen.  If not, see <https://www.gnu.org/licenses/>.
*/

package com.taffo.lockscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.taffo.lockscreen.utils.CheckPermissions;
import com.taffo.lockscreen.utils.SharedPref;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_frame_layout, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            ((Preference) (Objects.requireNonNull(findPreference(getString(R.string.about))))).setSummary(BuildConfig.VERSION_NAME);
        }

        @Override
        public void onResume() {
            super.onResume();
            requireActivity().setTitle(R.string.settings);
        }
    }

    public static class OnRestartSettingFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.on_restart_setting, rootKey);
            Context context = requireContext();
            Activity activity = requireActivity();
            SharedPref sp = new SharedPref(context);
            activity.setTitle(R.string.on_restart_setting_title);
            SwitchPreference switchOnRestartSetting = findPreference(getString(R.string.on_restart_switch_setting_shared_pref));
            ListPreference listNumberOfNotes = findPreference(getString(R.string.on_restart_list_setting_number_of_notes_to_play_shared_pref));

            if (!new CheckPermissions().checkPermissions(context))
                Objects.requireNonNull(switchOnRestartSetting).setEnabled(false);
            //Saves OnRestartSetting state
            Objects.requireNonNull(switchOnRestartSetting).setOnPreferenceChangeListener((preference, newValue) -> {
                sp.setSharedmPrefOnRestartSetting((Boolean.parseBoolean(newValue.toString())));
                return true;
            });

            //Saves OnRestartNumberOfNotesToPlay
            Objects.requireNonNull(listNumberOfNotes).setOnPreferenceChangeListener((preference, newValue) -> {
                sp.setSharedmPrefOnRestartListSettingNumberOfNotesToPlay(newValue.toString());
                return true;
            });
        }
    }

    public static class VolumeAdapterSettingFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            Context context = requireContext();
            Activity activity = requireActivity();
            SharedPref sp = new SharedPref(context);
            activity.setTitle(R.string.volume_adapter_setting_title);
            setPreferencesFromResource(R.xml.volume_adapter_setting, rootKey);
            SwitchPreference switchVolumeAdapterSetting = findPreference(getString(R.string.volume_adapter_switch_setting_shared_pref));

            //Asks for permissions
            Objects.requireNonNull(switchVolumeAdapterSetting).setOnPreferenceClickListener(preference -> {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                    switchVolumeAdapterSetting.setChecked(false);
                }
                return true;
            });

            //Saves VolumeAdapterService state
            switchVolumeAdapterSetting.setOnPreferenceChangeListener((preference, newValue) -> {
                sp.setSharedmVolumeAdapterServiceSetting((Boolean.parseBoolean(newValue.toString())));
                return true;
            });
        }
    }

    public static class Warnings extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            requireActivity().setTitle(R.string.warnings_title);
            setPreferencesFromResource(R.xml.warnings, rootKey);
        }
    }

}