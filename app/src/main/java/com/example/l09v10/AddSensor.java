package com.example.l09v10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Timestamp;

public class AddSensor extends AppCompatActivity {

    private EditText et_idDeviceAddSensor;
    private EditText et_idSensor_TypeAddSensor;
    private EditText et_idNameAddSensor;
    private Button btn_postSensorForGivenDeviceData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensor);

        Sensor2DataService sensor2DataService = new Sensor2DataService(AddSensor.this);

        et_idDeviceAddSensor=findViewById(R.id.idDeviceAddSensor);
        et_idSensor_TypeAddSensor=findViewById(R.id.idSensor_TypeAddSensor);
        et_idNameAddSensor=findViewById(R.id.idNameAddSensor);
        btn_postSensorForGivenDeviceData=findViewById(R.id.postSensorData3);


        btn_postSensorForGivenDeviceData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                SensorModel sensorModel = new SensorModel(Integer.valueOf(et_idDeviceAddSensor.getText().toString()).intValue(),Integer.valueOf(et_idSensor_TypeAddSensor.getText().toString()).intValue(),et_idNameAddSensor.getText().toString());
                sensor2DataService.postSensorDBData(sensorModel);
            }
        });



    }
}