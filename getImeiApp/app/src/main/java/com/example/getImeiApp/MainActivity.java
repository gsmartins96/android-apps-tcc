package com.example.getImeiApp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int READ_PHONE_STATE_REQUEST_CODE = 1000;

    TelephonyManager telephony;
    TextView phoneImeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneImeView = findViewById(R.id.imeiView);

        telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }

    public void getPermissionForIm(View view){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_PHONE_STATE},
                READ_PHONE_STATE_REQUEST_CODE
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getTelephonyIme(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            phoneImeView.setText("IMEI: Permissão negada");
        } else {
            String telephonyImei = telephony.getImei();

            phoneImeView.setText("IMEI: " + telephonyImei);
        }
    }


    public void resetResult(View view){
        phoneImeView.setText("IMEI: ");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == READ_PHONE_STATE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Phone State Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Phone State Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}