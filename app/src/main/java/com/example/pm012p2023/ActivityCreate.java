package com.example.pm012p2023;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pm012p2023.RestApiMehods.Methods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;

public class ActivityCreate extends AppCompatActivity {

    ImageView imgaen;
    Button btngaleria, btnenviar;
    static final int Result_galeria = 101;
    String POSTMethod,currentPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ControlsSet();
        btngaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GaleriaImagenes();
            }
        });

        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsumeCreateApi();
            }
        });

    }

    private void GaleriaImagenes() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Result_galeria);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri;
        if(resultCode == RESULT_OK && requestCode == Result_galeria){
            imageUri = data.getData();
            imgaen.setImageURI(imageUri);
            currentPath = getRealPathFromURI(imageUri);
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void ControlsSet() {
        imgaen = (ImageView) findViewById(R.id.imagen);
        btngaleria = (Button) findViewById(R.id.btngaleria);
        btnenviar = (Button) findViewById(R.id.btnenviar);
    }

    private void ConsumeCreateApi() {
        HashMap<String, String> parametros = new HashMap<>();
        parametros.put("nombres","Ernesto");
        parametros.put("apellidos","Valverde");
        parametros.put("fechanac","23-03-02");
        parametros.put("foto",ImageToBase64(currentPath));

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

    public static String ImageToBase64(String path)
    {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String Image64String = null;

        try
        {
            bmp = BitmapFactory.decodeFile(path);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG,50,bos);
            bt = bos.toByteArray();
            Image64String =  android.util.Base64.encodeToString(bt, android.util.Base64.DEFAULT);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return Image64String;
    }
}