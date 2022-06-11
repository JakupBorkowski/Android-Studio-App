package com.example.l09v10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Timestamp;

public class SessionHasSensor extends AppCompatActivity {

    private EditText et_idSessionSessionHasSensor;
    private EditText et_idSensorSessionHasSensor;

    private Button btn_postSessionHasSensorData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_has_sensor);

        SessionHasSensorDataService sessionHasSensorDataService = new SessionHasSensorDataService(SessionHasSensor.this);

        et_idSessionSessionHasSensor=findViewById(R.id.idSessionSessionHasSensor);
        et_idSensorSessionHasSensor=findViewById(R.id.idSensorSessionHasSensor);

        btn_postSessionHasSensorData=findViewById(R.id.postSessionHasSensorData);

        btn_postSessionHasSensorData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Timestamp timestampNew = new Timestamp(System.currentTimeMillis());
                //et_idStartAddSession.getText().toString()
                SessionHasSensorModel sessionHasSensorModel = new SessionHasSensorModel(Integer.valueOf(et_idSessionSessionHasSensor.getText().toString()).intValue(),Integer.valueOf(et_idSensorSessionHasSensor.getText().toString()).intValue());
                sessionHasSensorDataService.postSessionHasSensorDBData(sessionHasSensorModel);
            }
        });



    }
}