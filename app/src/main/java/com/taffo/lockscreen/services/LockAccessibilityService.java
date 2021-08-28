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

package com.taffo.lockscreen.services;

import android.accessibilityservice.AccessibilityService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.provider.Settings;
import android.service.quicksettings.TileService;
import android.view.accessibility.AccessibilityEvent;

import com.taffo.lockscreen.MainActivity;
import com.taffo.lockscreen.utils.CheckPermissions;
import com.taffo.lockscreen.utils.SharedPref;

public final class LockAccessibilityService extends AccessibilityService {
    private SharedPref sp;
    private static LockAccessibilityService instance;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        if (instance == null)
            instance = this;
        sp = new SharedPref(this);
        CheckPermissions cp = new CheckPermissions();
        if (cp.checkPermissions(this)) {
            sp.setSharedmPrefService(true);
            startForegroundService(new Intent(this, LockScreenService.class));
            TileService.requestListeningState(this, new ComponentName(this, LockTileService.class));
        }
        //Executed only at first connection
        if (sp.getSharedmPrefFirstRunAccessibilitySettings()) {
            sp.setSharedmPrefFirstRunAccessibilitySettings(false);
            startActivity(new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else {
            //Auto-starts the service on boot if the restart setting is on
            if (cp.checkPermissions(this) && sp.getSharedmPrefOnRestartSwitchSetting()) {
                sp.setSharedmPrefNumberOfNotesToPlay(sp.getSharedmPrefOnRestartListSettingNumberOfNotesToPlay());
                lockTheScreen();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
        sp.setSharedmPrefService(false);
        stopService(new Intent(this, LockScreenService.class));
        TileService.requestListeningState(this, new ComponentName(this, LockTileService.class));
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    //Locks the screen (also used in LockTileService)
    public void lockTheScreen() {
        if (instance != null)
            instance.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
    }

    public boolean isAccessibilitySettingsOn(Context context) {
        String prefString = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        return prefString != null && prefString.contains(context.getPackageName() + "/" + getClass().getName());
    }

}
