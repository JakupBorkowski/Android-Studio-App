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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Gyroscope extends AppCompatActivity  implements SensorEventListener {

    private SensorManager sensorManager;
    private int idGyroscope = 14;
    private Sensor gyroscopeSensor;
    private boolean isGyroscopeAvailable;
    private TextView tv_SensorData;
    private Button btn_tmpButton;
    private float[] GyroscopeData = new float[3];
    long start_time = System.currentTimeMillis(); //ZADANIE 7
    int okresRCV;
    SampleDataService sampleDataService = new SampleDataService(Gyroscope.this);
    boolean timestamp_flag;
    Timestamp timestampNew;
    Date date;
    long timeMilli;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);

        SampleDataService sampleDataService = new SampleDataService(Gyroscope.this);

        tv_SensorData =findViewById(R.id.actualSensorData);
        btn_tmpButton = findViewById(R.id.btnTmp);
        //btn_getSensorData=findViewById(R.id.getSensorData);
        //btn_postSensorData=findViewById(R.id.postSensorData);
        //lv_dbSensorData=findViewById(R.id.lv_DBSensorData);
        //et_sensorID=findViewById(R.id.pt_idSensor);
        timestamp_flag=true;




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
        if(timestamp_flag) {
            date = new Date();
            //This method returns the time in millis
            timeMilli = date.getTime();
        }
        Date currentDate = new Date(timeMilli);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        SessionDataService sessionDataService = new SessionDataService(Gyroscope.this);
        SessionModel sessionModel = new SessionModel("sesja żyroskopu", df.format(currentDate), 20,okresRCV);
        sessionDataService.postSessionDBData(sessionModel);
        String StringDate =df.format(currentDate);
        String[] StringDateParts= StringDate.split(" ");
        //TimeUnit.MILLISECONDS.sleep(1000);
        try{
            Thread.sleep(500);
        }catch (InterruptedException e){
        e.printStackTrace();
        }



        sessionDataService.getSensorDBData(StringDateParts[0]+"%20"+StringDateParts[1], new
                SessionDataService.DataBySessionID() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(Gyroscope.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(List<SessionModel> sessionModel) {
                        SessionHasSensorDataService sessionHasSensorDataService = new SessionHasSensorDataService(Gyroscope.this);
                        SessionHasSensorModel sessionHasSensorModel = new SessionHasSensorModel(sessionModel.get(0).getIdSession(), idGyroscope);
                        sessionHasSensorDataService.postSessionHasSensorDBData(sessionHasSensorModel);
                    }
                });



        System.out.println("TEST"+" - " + df.format(currentDate)+" - "+SessionDataService.idTmp);
        System.out.println(StringDateParts[0]+"%"+StringDateParts[1]);
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
            timestamp_flag=false;
        }
        else {
            timeMilli = timeMilli + okresRCV;
        }
            Date currentDate = new Date(timeMilli);
            DateFormat df = new SimpleDateFormat("yy:MM:dd HH:mm:ss.SSS");
            timestampNew = new Timestamp(System.currentTimeMillis());



            GyroscopeData[0] = event.values[0];
            GyroscopeData[1] = event.values[1];
            GyroscopeData[2] = event.values[2];

            //et_idStartAddSession.getText().toString()
            SampleModel sampleModel = new SampleModel(idGyroscope, event.values[0],event.values[1],event.values[2],df.format(currentDate).toString());
            sampleDataService.postSampleDBData(sampleModel);


        //WYSWIETLANIE NA PRÓBE W polu tv_SensorData
        tv_SensorData.setText("X: "+GyroscopeData[0]+" rad/s Y: "+GyroscopeData[1]+" rad/s Z: "+GyroscopeData[0]+" rad/s");
        start_time=System.currentTimeMillis();
        }}


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}