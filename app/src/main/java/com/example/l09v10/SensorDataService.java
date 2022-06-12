package com.example.l09v10;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.io.DataInput;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SensorDataService {
    Context context;

    public SensorDataService(Context context)
    {
        this.context=context;
    }

    public interface DataBySensorID{
        void onError(String message);
        void onResponse(List<GyroscopeModel> gyroscopeModel);
    }
    ServerInfo serverInfo= new ServerInfo();
    public void getSensorDBData(String sensorID, DataBySensorID dataBySensorID){
        List<GyroscopeModel> gyroscopeModels = new ArrayList<>();
        // Dostosuj IP zgodnie ze specyfikacją własnego serwera
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sessionrest/find-all-samples-sql?idSession="+sensorID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String s=String.valueOf(response.length());
                            Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

                            for(int i=0;i<response.length();i++) {
 /* Miejsce na pętlę zapisującą odebrane z serwera dane do listy klas
/ GyroscopeModel.
 Zmienna JSONArray response zawiera tablicę obiektów reprezentujących dane
/ z serwera.
 Kolejne obiekty można odczytać wykorzystując kod:*/
                                JSONObject oneSampleFromAPI = (JSONObject)response.get(i);
                                //ObjectMapper objectMapper = new ObjectMapper();

                                //GyroscopeModel gyroscopeModel = objectMapper.readValue((DataInput) oneSampleFromAPI, GyroscopeModel.class);
                                System.out.println(oneSampleFromAPI);
                                oneSampleFromAPI.getInt("idSensor");
                                GyroscopeModel gyroscopeModel = new GyroscopeModel(oneSampleFromAPI.getInt("idSample"),oneSampleFromAPI.getInt("idSensor"), (float) oneSampleFromAPI.getDouble("value_1"),(float) oneSampleFromAPI.getDouble("value_2"),(float) oneSampleFromAPI.getDouble("value_3"),oneSampleFromAPI.getString("timestamp"));
                                gyroscopeModels.add(gyroscopeModel);


                            }
                            dataBySensorID.onResponse(gyroscopeModels);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public JSONObject sensorDataToJSON(GyroscopeModel gyroscopeModel) {
        JSONObject sensorData = new JSONObject();
        try {
            sensorData.put("idSensor", gyroscopeModel.getIdSensor());
            sensorData.put("idSample",gyroscopeModel.getIdSample());
            sensorData.put("value_1", gyroscopeModel.getX());
            sensorData.put("value_2",gyroscopeModel.getY());
            sensorData.put("value_3", gyroscopeModel.getZ());
            sensorData.put("timestamp",gyroscopeModel.getTimestamp());

            return sensorData;
        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return sensorData;
    }

    public void postSensorDBData(GyroscopeModel gyroscopeModel){
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaView/web/samplerests";
        JSONObject sensorData;
        sensorData = sensorDataToJSON(gyroscopeModel);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,sensorData, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, response.toString(),Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
