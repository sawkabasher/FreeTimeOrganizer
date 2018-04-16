package fto.ee.swk.freetimeorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;


public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    JsonArrayRequest RequestOfJSonArray;
    RequestQueue requestQueue;

    private final String HTTP_JSON_URL = "https://fto.ee/api/v1/ip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSON_HTTP_CALL();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);}

    public void JSON_HTTP_CALL() {
        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(RequestOfJSonArray);
    }


    }