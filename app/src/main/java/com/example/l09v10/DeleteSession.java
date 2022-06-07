package com.example.l09v10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeleteSession extends AppCompatActivity {

    private EditText et_idSessionDeleteDevice;
    private Button btn_deleteSessionData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_session);

        SessionDataService sessionDataService = new SessionDataService(DeleteSession.this);

        et_idSessionDeleteDevice=findViewById(R.id.idSessionDeleteSession);
        btn_deleteSessionData=findViewById(R.id.deleteSessionData);

        btn_deleteSessionData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v) {
                sessionDataService.deleteSessionDBData(et_idSessionDeleteDevice.getText().toString());
            }
        });



    }
}