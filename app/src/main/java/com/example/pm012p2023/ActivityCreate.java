package com.example.pm012p2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pm012p2023.RestApiMehods.Methods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityCreate extends AppCompatActivity {

    String POSTMethod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ConsumeCreateApi();
    }

    private void ConsumeCreateApi() {
        HashMap<String, String> parametros = new HashMap<>();
        parametros.put("nombres","Ernesto");
        parametros.put("apellidos","Valverde");
        parametros.put("fechanac","23-03-02");
        parametros.put("foto","ASDASdasdasdasSAD");

        POSTMethod = Methods.ApiCreate;
        JSONObject JsonAlumn = new JSONObject(parametros);

        RequestQueue peticion = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.POST, POSTMethod, JsonAlumn, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray =new JSONArray(response);
                    for (int i =0; i<= jsonArray.length();i++){
                        JSONObject asg = jsonArray.getJSONObject(i);
                    }
                }catch (Exception e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        peticion.add(jsonObjectRequest);
    }
}