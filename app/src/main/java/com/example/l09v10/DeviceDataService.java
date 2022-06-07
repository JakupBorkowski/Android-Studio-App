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

    public class DeviceDataService {
        Context context;
        public DeviceDataService(Context context)
        {
            this.context=context;
        }

        public interface DataByDeviceID{
            void onError(String message);
            void onResponse(List<NevDeviceModel> nevDeviceModel);
        }

        public void getDeviceDBData(String deviceID, DataByDeviceID dataByDeviceID){
            List<NevDeviceModel> nevDeviceModels = new ArrayList<>();
            // Dostosuj IP zgodnie ze specyfikacją własnego serwera
            String url = "http://192.168.1.5:8080/PolitechnikaModel/web/samplerest/viewsamples?idSensor="+deviceID;//do napisania akcja w yii php
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new
                    Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                String s=String.valueOf(response.length());
                                Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

                                for(int i=0;i<response.length();i++) {
                                    JSONObject oneSampleFromAPI = (JSONObject)response.get(i);

                                    NevDeviceModel nevDeviceModel= new NevDeviceModel(oneSampleFromAPI.getInt("idDevice"),oneSampleFromAPI.getString("name"), oneSampleFromAPI.getString("description"),oneSampleFromAPI.getString("additionDate"),oneSampleFromAPI.getString("lastActualization"));
                                    nevDeviceModels.add(nevDeviceModel);

                                }
                                dataByDeviceID.onResponse(nevDeviceModels);
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
        public JSONObject deviceDataToJSON(NevDeviceModel nevDeviceModel) {
            JSONObject deviceData = new JSONObject();
            try {
                deviceData.put("idDevice", nevDeviceModel.getIdDevice());
                deviceData.put("name", nevDeviceModel.getName());
                deviceData.put("description", nevDeviceModel.getDescription());
                deviceData.put("additionDate", nevDeviceModel.getAdditionDate());
                deviceData.put("lastActualization", nevDeviceModel.getLastActualization());

                return deviceData;
            } catch (Exception e) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
            return deviceData;
        }
        public void postDeviceDBData(NevDeviceModel nevDeviceModel){
            String url = "http://192.168.1.5:8080/PolitechnikaModel/web/devicerests";
            JSONObject deviceData;
            deviceData = deviceDataToJSON(nevDeviceModel);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,deviceData, new
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

        public void deleteDeviceDBData(String idDevice){
            String url = "http://192.168.1.5:8080/PolitechnikaModel/web/devicerests/"+idDevice;
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









