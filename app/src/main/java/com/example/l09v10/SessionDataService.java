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

public class SessionDataService {
    Context context;

    public SessionDataService(Context context)
    {
        this.context=context;
    }

    public interface DataBySessionID{
        void onError(String message);
        void onResponse(List<SessionModel> sessionModel);
    }
    ServerInfo serverInfo = new ServerInfo();
    public void getSessionDBData(String sessionID, DataBySessionID dataBySessionID){
        List<SessionModel> sessionModels = new ArrayList<>();
        // Dostosuj IP zgodnie ze specyfikacją własnego serwera
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/samplerest/viewsamples?idSensor="+sessionID;//do napisania akcja w yii php
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String s=String.valueOf(response.length());
                            Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

                            for(int i=0;i<response.length();i++) {
                                JSONObject oneSampleFromAPI = (JSONObject)response.get(i);

                                SessionModel sessionModel= new SessionModel(oneSampleFromAPI.getInt("idSession"),oneSampleFromAPI.getString("name"), oneSampleFromAPI.getString("start"),oneSampleFromAPI.getInt("samples"),(float)oneSampleFromAPI.getDouble("tp"));
                                sessionModels.add(sessionModel);

                            }
                            dataBySessionID.onResponse(sessionModels);
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
    public JSONObject sessionDataToJSON(SessionModel sessionModel) {
        JSONObject sessionData = new JSONObject();
        try {
            sessionData.put("idSession", sessionModel.getIdSession());
            sessionData.put("name", sessionModel.getName());
            sessionData.put("start", sessionModel.getStart());
            sessionData.put("samples", sessionModel.getSamples());
            sessionData.put("tp", sessionModel.getTp());

            return sessionData;
        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return sessionData;
    }

    public void postSessionDBData(SessionModel sessionModel){
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sessionrests";
        JSONObject  sessionData;
        sessionData = sessionDataToJSON(sessionModel);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,sessionData, new
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

    public void deleteSessionDBData(String idSession){
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sessionrests/"+idSession;
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
