package com.example.l09v10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.List;

public class NewDeviceActivity extends AppCompatActivity {

    private EditText et_idNewDeviceName;
    private EditText et_idNewDeviceDescription;
    private Button btn_postDeviceData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_device);

        DeviceDataService deviceDataService = new DeviceDataService(NewDeviceActivity.this);

        et_idNewDeviceName=findViewById(R.id.idNewDeviceName);
        et_idNewDeviceDescription=findViewById(R.id.idNewDeviceDescription);
        btn_postDeviceData=findViewById(R.id.postSensorData2);


        btn_postDeviceData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Timestamp timestampNew = new Timestamp(System.currentTimeMillis());
                NevDeviceModel newDeviceModel = new NevDeviceModel(et_idNewDeviceName.getText().toString(),et_idNewDeviceDescription.getText().toString(),timestampNew.toString(),timestampNew.toString());
                deviceDataService.postDeviceDBData(newDeviceModel);
            }
        });



    }
}

