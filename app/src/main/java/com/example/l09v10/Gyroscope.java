package com.example.l09v10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Gyroscope extends AppCompatActivity  implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private boolean isGyroscopeAvailable;
    private TextView tv_SensorData;
    private Button btn_getSensorData;
    private Button btn_postSensorData;
    private EditText et_sensorID;
    private EditText et_okres;
    private ListView lv_dbSensorData;
    private float[] GyroscopeData = new float[3];
    long start_time = System.currentTimeMillis(); //ZADANIE 7
    int okresRCV;
    SampleDataService sampleDataService = new SampleDataService(Gyroscope.this);
    boolean timestamp_flag;
    Timestamp timestampNew;
    Date today;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        SensorDataService sensorDataService = new SensorDataService(Gyroscope.this);

        tv_SensorData =findViewById(R.id.actualSensorData);
        btn_getSensorData=findViewById(R.id.getSensorData);
        btn_postSensorData=findViewById(R.id.postSensorData);
        lv_dbSensorData=findViewById(R.id.lv_DBSensorData);
        et_sensorID=findViewById(R.id.pt_idSensor);
        et_okres=findViewById(R.id.pt_okres);
        timestamp_flag=true;


        btn_getSensorData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v) {
                sensorDataService.getSensorDBData(et_sensorID.getText().toString(), new
                        SensorDataService.DataBySensorID() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(Gyroscope.this, "Something wrong", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onResponse(List<GyroscopeModel> gyroscopeModel) {
                                ArrayAdapter arrayAdapter = new ArrayAdapter(Gyroscope.this,
                                        android.R.layout.simple_list_item_1, gyroscopeModel);
                                lv_dbSensorData.setAdapter(arrayAdapter);
                            }
                        });

            }
        });
        btn_postSensorData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v) {
                Timestamp timestampNew = new Timestamp(System.currentTimeMillis());
                GyroscopeModel gyroscopeModel = new GyroscopeModel(Integer.valueOf(et_sensorID.getText().toString()).intValue(),GyroscopeData[0],GyroscopeData[1], GyroscopeData[2], timestampNew.toString());
                sensorDataService.postSensorDBData(gyroscopeModel);
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


        start_time = System.currentTimeMillis(); //ZADANIE 7
        okresRCV = getIntent().getExtras().getInt("keyokres");//ZADANIE7

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
        long actual_time = System.currentTimeMillis();//ZADANIE 7
        if(actual_time-start_time>=okresRCV) {
        if(timestamp_flag) {
        }
            timestampNew = new Timestamp(System.currentTimeMillis());
            timestamp_flag=false;


            GyroscopeData[0] = event.values[0];
            GyroscopeData[1] = event.values[1];
            GyroscopeData[2] = event.values[2];

            //et_idStartAddSession.getText().toString()
            SampleModel sampleModel = new SampleModel(14, event.values[0],event.values[1],event.values[2],timestampNew.toString());
            sampleDataService.postSampleDBData(sampleModel);


        //WYSWIETLANIE NA PRÓBE W polu tv_SensorData
        tv_SensorData.setText("X: "+GyroscopeData[0]+" rad/s Y: "+GyroscopeData[1]+" rad/s Z: "+GyroscopeData[0]+" rad/s");
        start_time=System.currentTimeMillis();
        }}


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}