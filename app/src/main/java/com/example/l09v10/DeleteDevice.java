package com.example.l09v10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.List;

public class DeleteDevice extends AppCompatActivity {

    private EditText et_idDeviceDeleteDevice;
    private Button btn_deleteDeviceData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_device);

        DeviceDataService deviceDataService = new DeviceDataService(DeleteDevice.this);

        et_idDeviceDeleteDevice=findViewById(R.id.idDeviceDeleteDevice);
        btn_deleteDeviceData=findViewById(R.id.deleteDeviceData);

        btn_deleteDeviceData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v) {
                deviceDataService.deleteDeviceDBData(et_idDeviceDeleteDevice.getText().toString());
            }
        });



    }
}