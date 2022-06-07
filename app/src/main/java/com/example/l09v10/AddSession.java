package com.example.l09v10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Timestamp;

public class AddSession extends AppCompatActivity {

    private EditText et_idNameAddSession;
    //private EditText et_idStartAddSession;
    private EditText et_idSamplesAddSession;
    private EditText et_idTpAddSession;

    private Button btn_postSessionData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);

        SessionDataService sessionDataService = new SessionDataService(AddSession.this);

        et_idNameAddSession=findViewById(R.id.idNameAddSession);
        //et_idStartAddSession=findViewById(R.id.idStartAddSession);
        et_idSamplesAddSession=findViewById(R.id.idSamplesAddSession);
        et_idTpAddSession=findViewById(R.id.idTpAddSession);

        btn_postSessionData=findViewById(R.id.postSensorData4);

        btn_postSessionData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Timestamp timestampNew = new Timestamp(System.currentTimeMillis());
                //et_idStartAddSession.getText().toString()
                SessionModel sessionModel = new SessionModel(et_idNameAddSession.getText().toString(),timestampNew.toString(),Integer.valueOf(et_idSamplesAddSession.getText().toString()).intValue(),Float.parseFloat(et_idTpAddSession.getText().toString()));
                sessionDataService.postSessionDBData(sessionModel);
            }
        });



    }
}