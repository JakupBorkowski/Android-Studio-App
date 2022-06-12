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

public class SampleDataService {

    Context context;
    public SampleDataService(Context context)
    {
        this.context=context;
    }
    public interface DataBySampleID{
        void onError(String message);
        void onResponse(List<SampleModel> sampleModel);
    }
    ServerInfo serverInfo = new ServerInfo();
    public void getSampleDBData(String sensorID, DataBySampleID dataBySampleID){
        List<SampleModel> sampleModels = new ArrayList<>();
        // Dostosuj IP zgodnie ze specyfikacją własnego serwera
        String url = "http://+"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/samplerest/viewsensors?idSensor="+sensorID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String s=String.valueOf(response.length());
                            Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

                            for(int i=0;i<response.length();i++) {
                                JSONObject oneSampleFromAPI = (JSONObject)response.get(i);

                                SampleModel sampleModel= new SampleModel(oneSampleFromAPI.getInt("idSample"),oneSampleFromAPI.getInt("idSensor"), (float) oneSampleFromAPI.getDouble("value_1"),(float) oneSampleFromAPI.getDouble("value_2"),(float) oneSampleFromAPI.getDouble("value_3"),oneSampleFromAPI.getString("timestamp"));
                                sampleModels.add(sampleModel);

                            }
                            dataBySampleID.onResponse(sampleModels);
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

    public void getSampleBySessionIdDBData(String sessionID, DataBySampleID dataBySampleID){
        List<SampleModel> sampleModels = new ArrayList<>();
        // Dostosuj IP zgodnie ze specyfikacją własnego serwera
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sessionrest/find-all-samples-sql?idSession="+sessionID;
        url="http://172.20.10.2:8080/PolitechnikaModel/web/sessionrest/find-all-samples-sql?idSession=3";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String s=String.valueOf(response.length());
                            Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

                            for(int i=0;i<response.length();i++) {
                                JSONObject oneSampleFromAPI = (JSONObject)response.get(i);

                                SampleModel sampleModel= new SampleModel(oneSampleFromAPI.getInt("idSample"),oneSampleFromAPI.getInt("idSensor"), (float) oneSampleFromAPI.getDouble("value_1"),(float) oneSampleFromAPI.getDouble("value_2"),(float) oneSampleFromAPI.getDouble("value_3"),oneSampleFromAPI.getString("timestamp"));
                                sampleModels.add(sampleModel);

                            }
                            dataBySampleID.onResponse(sampleModels);
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


    public JSONObject sampleDataToJSON(SampleModel sampleModel) {
        JSONObject sampleData = new JSONObject();
        try {
            sampleData.put("idSample", sampleModel.getIdSample());
            sampleData.put("idSensor", sampleModel.getIdSensor());
            sampleData.put("value_1", sampleModel.getValue_1());
            sampleData.put("value_2", sampleModel.getValue_1());
            sampleData.put("value_3", sampleModel.getValue_1());
            sampleData.put("timestamp", sampleModel.getTimestamp());

            return sampleData;
        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return sampleData;
    }
    public void postSampleDBData(SampleModel sampleModel){
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/samplerests";
        JSONObject  sampleData;
        sampleData = sampleDataToJSON(sampleModel);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,sampleData, new
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

    public void deleteSensorDBData(String idSample){
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/samplerests/"+idSample;
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
