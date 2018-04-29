package fto.ee.swk.freetimeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 500;    //todo ----> SPLASH_TIME_OUT = 2000;
    JsonArrayRequest RequestOfJSonArray;
    RequestQueue requestQueue;
    String uniqueID;
    private static final String TAG = "MainActivity";

    private final String HTTP_JSON_URL = "https://fto.ee/api/v1/ip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uniqueID = String.valueOf(UUID.randomUUID());
        Log.d(TAG, "onCreate: unique ID "+ uniqueID);

        JSON_HTTP_CALL(HTTP_JSON_URL);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }//onCreate end

    public void JSON_HTTP_CALL(String url) {
        RequestOfJSonArray = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());
                    }
                }
                );

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(RequestOfJSonArray);
    }


    }