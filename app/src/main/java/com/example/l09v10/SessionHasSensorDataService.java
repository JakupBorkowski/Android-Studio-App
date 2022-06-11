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

public class SessionHasSensorDataService {


    Context context;

    public SessionHasSensorDataService(Context context)
    {
        this.context=context;
    }

    public interface DataBySessionHasSensorID{
        void onError(String message);
        void onResponse(List<SessionHasSensorModel> sessionHasSensorModel);
    }
    ServerInfo serverInfo = new ServerInfo();
    public void getSessionHasSensorDBData(String sessionID, DataBySessionHasSensorID dataBySessionHasSensorID){
        List<SessionHasSensorModel> sessionHasSensorModels = new ArrayList<>();
        // Dostosuj IP zgodnie ze specyfikacją własnego serwera
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sessionhassensorrest/viewsamples?idSensor="+sessionID;//do napisania akcja w yii php
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String s=String.valueOf(response.length());
                            Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

                            for(int i=0;i<response.length();i++) {
                                JSONObject oneSampleFromAPI = (JSONObject)response.get(i);

                                SessionHasSensorModel sessionHasSensorModel = new SessionHasSensorModel(oneSampleFromAPI.getInt("idSession"),oneSampleFromAPI.getInt("idSensor"));
                                sessionHasSensorModels.add(sessionHasSensorModel);

                            }
                            dataBySessionHasSensorID.onResponse(sessionHasSensorModels);
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
    public JSONObject sessionDataToJSON(SessionHasSensorModel sessionHasSensorModel) {
        JSONObject sessionHasSensorData = new JSONObject();
        try {
            sessionHasSensorData.put("idSession", sessionHasSensorModel.getIdSession());
            sessionHasSensorData.put("idSensor", sessionHasSensorModel.getIdSensor());

            return sessionHasSensorData;
        } catch (Exception e) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return sessionHasSensorData;
    }

    public void postSessionHasSensorDBData(SessionHasSensorModel sessionHasSensorModel){
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sessionhassensorrests";
        JSONObject  sessionHasSensorData;
        sessionHasSensorData = sessionDataToJSON(sessionHasSensorModel);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,sessionHasSensorData, new
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

    public void deleteSessionHasSensorDBData(String idSession){
        String url = "http://"+serverInfo.getIpAdress()+":8080/PolitechnikaModel/web/sessionhassensorrests/"+idSession;
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
