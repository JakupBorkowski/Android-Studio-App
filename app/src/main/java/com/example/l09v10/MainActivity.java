package com.example.l09v10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            System.out.println("*** My thread is now configured to allow connection");
        }

    }

    public void GyroscopeActivity(View Button)
    {
        Intent GyroscopeIntent = new Intent(this, Gyroscope.class);
        startActivity(GyroscopeIntent);
    }

    public void NewDeviceActivity(View Button)
    {
        Intent AddDeviceIntent = new Intent(this, NewDeviceActivity.class);
        startActivity(AddDeviceIntent);
    }

    public void AddSensorActivity(View Button)
    {
        Intent AddSensorIntent = new Intent(this, AddSensor.class);
        startActivity(AddSensorIntent);
    }
    public void AddSessionActivity(View Button)
    {
        Intent AddSessionIntent = new Intent(this, AddSession.class);
        startActivity(AddSessionIntent);
    }
    public void DeleteDeviceActivity(View Button)
    {
        Intent DeleteDeviceIntent = new Intent(this, DeleteDevice.class);
        startActivity(DeleteDeviceIntent);
    }
    public void DeleteSensorActivity(View Button)
    {
        Intent DeleteSensorIntent = new Intent(this, DeleteSensor.class);
        startActivity(DeleteSensorIntent);
    }


}