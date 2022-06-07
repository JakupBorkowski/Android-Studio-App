package com.example.l09v10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeleteSensor extends AppCompatActivity {


    private EditText et_idSensorDeleteSensor;
    private Button btn_deleteSensorData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_sensor);

        Sensor2DataService sensor2DataService = new Sensor2DataService(DeleteSensor.this);

        et_idSensorDeleteSensor=findViewById(R.id.idSensorDeleteSensor);
        btn_deleteSensorData=findViewById(R.id.deleteSensorData);

        btn_deleteSensorData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v) {
                sensor2DataService.deleteSensorDBData(et_idSensorDeleteSensor.getText().toString());
            }
        });



    }
}