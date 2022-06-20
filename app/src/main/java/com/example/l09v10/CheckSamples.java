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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CheckSamples extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private boolean isGyroscopeAvailable;
    private TextView tv_SensorData;
    private Button btn_getSensorData;
    private Button btn_postSensorData;
    private EditText et_sensorID;
    private ListView lv_dbSensorData;
    private float[] GyroscopeData = new float[3];
    long start_time = System.currentTimeMillis(); //ZADANIE 7
    int okresRCV;
    boolean timestamp_flag;
    Timestamp timestampNew;
    Date date;
    long timeMilli;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_samples);

        SampleDataService sampleDataService = new SampleDataService(CheckSamples.this);
SensorDataService sensorDataService = new SensorDataService(CheckSamples.this);
        SessionDataService sessionDataService = new SessionDataService(CheckSamples.this);
        //tv_SensorData =findViewById(R.id.actualSensorData);
        btn_getSensorData=findViewById(R.id.getSensorData2);
        //btn_postSensorData=findViewById(R.id.postSensorData);
        lv_dbSensorData=findViewById(R.id.lv_DBSensorData);
        et_sensorID=findViewById(R.id.pt_idSensor2);
        timestamp_flag=true;


        btn_getSensorData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v) {
                sensorDataService.getSensorDBData(et_sensorID.getText().toString(), new
                        SensorDataService.DataBySensorID() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(CheckSamples.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onResponse(List<GyroscopeModel> sampleModel) {
                                System.out.println("TIMESTAMP PIERWSZEJ PROBKI" +sampleModel.get(0).getTimestamp());
                                ArrayAdapter arrayAdapter = new ArrayAdapter(CheckSamples.this,
                                        android.R.layout.simple_list_item_1, sampleModel);
                                lv_dbSensorData.setAdapter(arrayAdapter);
                            }
                        });

            }
        });


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!=null)
        {
            gyroscopeSensor =
                    sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);//TYPE_ACCELEROMETER

            isGyroscopeAvailable = true;
        }
        else {
            isGyroscopeAvailable = false;
        }

    }


    @Override
    public void onResume(){
        super.onResume();
        if(isGyroscopeAvailable) {
            sensorManager.registerListener(this, gyroscopeSensor,
                    sensorManager.SENSOR_DELAY_FASTEST);//sensorManager.SENSOR_DELAY_NORMAL
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isGyroscopeAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}