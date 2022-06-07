package com.example.l09v10;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sensor2DataService {

    Context context;
    public Sensor2DataService(Context context)
    {
        this.context=context;
    }
    public interface DataBySensorID{
        void onError(String message);
        void onResponse(List<SensorModel> sensorModel);
    }
    ServerInfo serverInfo = new ServerInfo();
    public void getSensorDBData(String deviceID, DataBySensorID dataBySensorID){
        List<SensorModel> sensorModels = new ArrayList<>();
        // Dostosuj IP zgodnie ze specyfikacją własnego serwera
        String url = "http://+"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sensorrest/viewsensors?idDevice="+deviceID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String s=String.valueOf(response.length());
                            Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

                            for(int i=0;i<response.length();i++) {
                                JSONObject oneSampleFromAPI = (JSONObject)response.get(i);

                                SensorModel sensorModel= new SensorModel(oneSampleFromAPI.getInt("idSensor"),oneSampleFromAPI.getInt("idDevice"), oneSampleFromAPI.getInt("idSensor_Type"),oneSampleFromAPI.getString("name"));
                                sensorModels.add(sensorModel);

                            }
                            dataBySensorID.onResponse(sensorModels);
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
    public JSONObject sensorDataToJSON(SensorModel sensorModel) {
        JSONObject sensorData = new JSONObject();
        try {
            sensorData.put("idDevice", sensorModel.getIdDevice());
            sensorData.put("idSensor", sensorModel.getIdSensor());
            sensorData.put("idSensor_Type", sensorModel.getIdSensor_Type());
            sensorData.put("name", sensorModel.getName());

            return sensorData;
        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return sensorData;
    }
    public void postSensorDBData(SensorModel sensorModel){
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sensorrests";
        JSONObject  sensorData;
        sensorData = sensorDataToJSON(sensorModel);
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

    public void deleteSensorDBData(String idDevice){
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sensorrests/"+idDevice;
        //JSONObject deviceData;
        //deviceData = deviceDataToJSON(nevDeviceModel);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new
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
