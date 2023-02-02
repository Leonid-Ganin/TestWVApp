package com.ganin.testwvapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String PREF = "settings";
    public static final String URL = "url";

    private String url = "";
    private SharedPreferences mSettings;

    private boolean checkIsEmu() {
        if (BuildConfig.DEBUG) return false;

        String phoneModel = Build.MODEL;
        String buildProduct = Build.PRODUCT;
        String buildHardware = Build.HARDWARE;
        String brand = Build.BRAND;

        boolean result = (Build.FINGERPRINT.startsWith("generic")
                || phoneModel.contains("google_sdk")
                || phoneModel.toLowerCase(Locale.getDefault()).contains("droid4x")
                || phoneModel.contains("Emulator")
                || phoneModel.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || buildHardware.equals("goldfish")
                || brand.contains("google")
                || buildHardware.equals("vbox86")
                || buildProduct.equals("sdk")
                || buildProduct.equals("google_sdk")
                || buildProduct.equals("sdk_x86")
                || buildProduct.equals("vbox86p")
                || Build.BOARD.toLowerCase(Locale.getDefault()).contains("nox")
                || Build.BOOTLOADER.toLowerCase(Locale.getDefault()).contains("nox")
                || buildHardware.toLowerCase(Locale.getDefault()).contains("nox")
                || buildProduct.toLowerCase(Locale.getDefault()).contains("nox"));

        result = result || (brand.startsWith("generic") && Build.DEVICE.startsWith("generic"));

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(PREF, MODE_PRIVATE);
        url = mSettings.getString(URL, "");

        if (url.equals("")) {
            FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(3600)
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

            mFirebaseRemoteConfig.fetchAndActivate()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            url = mFirebaseRemoteConfig.getString(URL);

                            if (url.isEmpty() || checkIsEmu()) {
                                // Добавить заглушку
                                return;
                            }

                            mSettings.edit().putString(URL, url).apply();

                            // Добавить webview
                        } else {
                            Toast.makeText(MainActivity.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Добавить проверку включён ли интернет

            Toast.makeText(MainActivity.this, url,
                    Toast.LENGTH_SHORT).show();
        }
    }
}