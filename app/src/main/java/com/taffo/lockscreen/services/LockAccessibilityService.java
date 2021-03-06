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

import android.Manifest;
import android.accessibilityservice.AccessibilityService;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.service.quicksettings.TileService;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.taffo.lockscreen.DeviceAdminActivity;
import com.taffo.lockscreen.MainActivity;
import com.taffo.lockscreen.utils.CheckPermissions;
import com.taffo.lockscreen.utils.SharedPref;

public final class LockAccessibilityService extends AccessibilityService {
    private SharedPref sp;
    private CheckPermissions cp;
    private CheckCalls callsListener;
    private CheckCallsS callsListenerS;
    private TelephonyManager telephony;
    private static LockAccessibilityService instance;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = new SharedPref(this);
        cp = new CheckPermissions();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                //Not tested yet
                callsListenerS = new CheckCallsS();
                telephony.registerTelephonyCallback(runnable -> {}, callsListenerS);
            } else {
                callsListener = new CheckCalls();
                telephony.listen(callsListener, PhoneStateListener.LISTEN_CALL_STATE);
            }
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        if (instance == null)
            instance = this;
        //Executed only at first connection
        if (sp.getSharedmPrefFirstRunAccessibilitySettings())
            startActivity(new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        //Auto-starts the service on boot if the restart setting is on
        if (cp.checkPermissions(this)) {
            sp.setSharedmPrefService(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                startForegroundService(new Intent(this, LockScreenService.class));
            else
                startService(new Intent(this, LockScreenService.class));
            if (sp.getSharedmPrefBootSwitchSetting()) {
                DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                if (dpm.isAdminActive(new ComponentName(this, DeviceAdminActivity.DeviceAdminActivityReceiver.class))) {
                    sp.setSharedmPrefNumberOfNotesToPlay(sp.getSharedmPrefBootListSettingNumberOfNotesToPlay());
                    dpm.lockNow();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                && telephony != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                telephony.unregisterTelephonyCallback(callsListenerS);
            else
                telephony.listen(callsListener, PhoneStateListener.LISTEN_NONE);
        }
        instance = null;
        sp.setSharedmPrefService(false);
        stopService(new Intent(this, LockScreenService.class));
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    public boolean isAccessibilitySettingsOn(Context context) {
        String prefString = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        return prefString != null && prefString.contains(context.getPackageName() + "/" + getClass().getName());
    }

    //Locks the screen (used in LockTileService)
    public static void lockTheScreen(Context context) {
        if (instance != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                instance.performGlobalAction(AccessibilityService.GLOBAL_ACTION_LOCK_SCREEN);
            else
                DeviceAdminActivity.adminLockTheScreen(context);
    }

    //Stops the main service if a call arrived and is ringing or waiting, or at least one call exists that is dialing, active, or on hold
    //and restarts it when the call terminates
    //for API <= 30
    //See also the implementation in LockScreenActivity class
    private final class CheckCalls extends PhoneStateListener {
        final Context mContext = getApplicationContext();
        boolean isServiceRunning = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING || state == TelephonyManager.CALL_STATE_OFFHOOK) {
                CheckPermissions.setIsCallLive(true);
                TileService.requestListeningState(mContext, new ComponentName(mContext, LockTileService.class));
                if (sp.getSharedmPrefService()) {
                    isServiceRunning = true;
                    sp.setSharedmPrefService(false);
                    stopService(new Intent(mContext, LockScreenService.class));
                } else
                    isServiceRunning = false;
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                CheckPermissions.setIsCallLive(false);
                TileService.requestListeningState(mContext, new ComponentName(mContext, LockTileService.class));
                if (cp.checkPermissions(mContext) && isServiceRunning) {
                    sp.setSharedmPrefService(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        startForegroundService(new Intent(mContext, LockScreenService.class));
                    else
                        startService(new Intent(mContext, LockScreenService.class));
                }
            }
        }

    }

    //Stops the main service if a call arrived and is ringing or waiting, or at least one call exists that is dialing, active, or on hold
    //and restarts it when the call terminates
    //for API >= 31
    //See also the implementation in LockScreenActivity class
    //Not tested yet
    @RequiresApi(api = Build.VERSION_CODES.S)
    private final class CheckCallsS extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        final Context mContext = getApplicationContext();
        boolean isServiceRunning = false;

        @Override
        public void onCallStateChanged(int state) {
            if (state == TelephonyManager.CALL_STATE_RINGING || state == TelephonyManager.CALL_STATE_OFFHOOK) {
                CheckPermissions.setIsCallLive(true);
                //The tile must be updated here because if the main service was not running when the called arrived,
                //LockScreenService will not be started and will not update the tile
                TileService.requestListeningState(mContext, new ComponentName(mContext, LockTileService.class));
                if (sp.getSharedmPrefService()) {
                    isServiceRunning = true;
                    sp.setSharedmPrefService(false);
                    stopService(new Intent(mContext, LockScreenService.class));
                } else
                    isServiceRunning = false;
            } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                CheckPermissions.setIsCallLive(false);
                //The tile must be updated here because if the main service was not running when the called arrived,
                //LockScreenService will not be started and will not update the tile
                TileService.requestListeningState(mContext, new ComponentName(mContext, LockTileService.class));
                if (cp.checkPermissions(mContext) && isServiceRunning) {
                    sp.setSharedmPrefService(true);
                    startForegroundService(new Intent(getApplicationContext(), LockScreenService.class));
                }
            }
        }

    }

}
