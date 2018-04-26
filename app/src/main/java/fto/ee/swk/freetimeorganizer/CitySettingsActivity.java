package fto.ee.swk.freetimeorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import javax.security.auth.login.LoginException;


public class CitySettingsActivity extends AppCompatActivity {

    private static final String TAG = "sawkabasher";
    private String cityPart = "/";
    private List<CityDataObject> ListOfCities;
    JsonArrayRequest RequestOfJSonArray;
    RequestQueue requestQueue;
    LinearLayout ll;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_settings);
        loadData();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        ListOfCities = new ArrayList<>();
        JSON_HTTP_CALL("https://fto.ee/api/v1/events/city");
        Log.e(TAG, ListOfCities.toString());
        ll = (LinearLayout) findViewById(R.id.l_layout_city);
        Log.i(TAG,"CityPart === " + cityPart);


    } //onCreate end

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadData();
        Log.i(TAG, "onPostResume:loaddata: " + cityPart);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.putExtra("cityPart", cityPart);
        setResult(RESULT_OK, intent);
        saveData(cityPart);
        Log.d(TAG, "onDestroy: Triggered. cityPart=" + cityPart);
        finish();

    }

    //TODO create "X" button which will return to HomeActivity and close mDrawer ,, ===> back toolbar button doesn't close the mDrawer

    private void saveData(String cityPart){
        SharedPreferences sharedPreferences = getSharedPreferences("cityPart", MODE_PRIVATE);
        SharedPreferences.Editor genrePartEditor = sharedPreferences.edit();
        genrePartEditor.putString("cityPart part", cityPart);
        genrePartEditor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("cityPart", MODE_PRIVATE);
        cityPart = sharedPreferences.getString("cityPart part",cityPart);
        if (cityPart == null) {
            cityPart = "";
        }
    }


    /**
     creates switches
     */
    public void populateCardView(){
        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String city_name = (String) buttonView.getText();
                if (buttonView.isChecked()) {
                    cityPart += city_name + "_";
                    Log.e("CitySettingsActivity", "cityPart === " + cityPart);
                    //Toast.makeText(GenreSettingsActivity.this, buttonView.getId() + "Checked", Toast.LENGTH_SHORT).show();
                    saveData(cityPart);

                    //save switch state
                    SharedPreferences.Editor editor = getSharedPreferences((String) buttonView.getText(), MODE_PRIVATE).edit();
                    editor.putBoolean((String) buttonView.getText(), true);
                    editor.apply();
                } else {
                    cityPart = cityPart.replaceAll( String.valueOf(city_name) + "_","");
                    Log.e("genrePart ===", cityPart);
                    //Toast.makeText(GenreSettingsActivity.this, buttonView.getId() + "Unchecked", Toast.LENGTH_SHORT).show();
                    saveData(cityPart);

                    //save switch state
                    SharedPreferences.Editor editor = getSharedPreferences((String) buttonView.getText(), MODE_PRIVATE).edit();
                    editor.putBoolean((String) buttonView.getText(), false);
                    editor.apply();
                }
            }
        };
        for (int i = 0; i < ListOfCities.size(); i++) {
            Switch city = new Switch(this);
            SharedPreferences sharedPrefs = getSharedPreferences(ListOfCities.get(i).getCity(), MODE_PRIVATE);
            city.setTextOff("OFF");
            city.setTextOn("ON");
            city.setChecked(sharedPrefs.getBoolean(ListOfCities.get(i).getCity(), true));
            if (city.isChecked()){
                cityPart += ListOfCities.get(i).getCity()+ "_";
            }
            city.setText(ListOfCities.get(i).getCity());
            city.setId(ListOfCities.get(i).getId()); //todo ask Artur to add id in Cities table
            city.setOnCheckedChangeListener(multiListener);
            ll.addView(city);
        }
        Log.e(TAG, ListOfCities.toString());
    }

    public void JSON_HTTP_CALL(String url) {
        RequestOfJSonArray = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ListOfCities.clear();
                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        requestQueue = Volley.newRequestQueue(CitySettingsActivity.this);
        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            CityDataObject obj = new CityDataObject();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                obj.setCity(json.getString("city"));
                obj.setId(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListOfCities.add(obj);
        }
        populateCardView();
    }

}

