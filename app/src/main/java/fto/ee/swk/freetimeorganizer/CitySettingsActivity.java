package fto.ee.swk.freetimeorganizer;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class CitySettingsActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private List<CityDataObject> ListOfCities;
    private JsonArrayRequest RequestOfJSonArray;
    private RequestQueue requestQueue;
    private AutoCompleteTextView acTextView;
    private Button btn;
    private String cityName;
    private LinearLayout ll;
    private ArrayList<String> listOfAddedCities;
    private String cityPart = "/";
    private String cityCheck;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_settings);
        ListOfCities = new ArrayList<>();
        JSON_HTTP_CALL("https://fto.ee/api/v1/events/city");
        acTextView = (AutoCompleteTextView) findViewById(R.id.cityAutoCompleteTextView);
        btn = (Button)findViewById(R.id.button);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,ListOfCities);
        ll = (LinearLayout)findViewById(R.id.ll);
        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);

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

        loadData();
        final CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String city_name = (String) buttonView.getText();
                if (buttonView.isChecked()) {
                    cityPart += city_name + "_";
                    Log.e("CitySettingsActivity", "cityPart === " + cityPart);
                    //Toast.makeText(GenreSettingsActivity.this, buttonView.getId() + "Checked", Toast.LENGTH_SHORT).show();
                    saveData();

                    //save switch state
                    SharedPreferences.Editor editor = getSharedPreferences((String) buttonView.getText(), MODE_PRIVATE).edit();
                    editor.putBoolean((String) buttonView.getText(), true);
                    editor.apply();
                } else {
                    cityPart = cityPart.replaceAll( String.valueOf(city_name) + "_","");
                    Log.e("cityPart ===", cityPart);
                    //Toast.makeText(GenreSettingsActivity.this, buttonView.getId() + "Unchecked", Toast.LENGTH_SHORT).show();
                    saveData();

                    //save switch state
                    SharedPreferences.Editor editor = getSharedPreferences((String) buttonView.getText(), MODE_PRIVATE).edit();
                    editor.putBoolean((String) buttonView.getText(), false);
                    editor.apply();
                }
            }
        };
        if (!listOfAddedCities.isEmpty()) {
            for (int i = 0; i < listOfAddedCities.size(); i++) {
                Switch city = new Switch(CitySettingsActivity.this);
                SharedPreferences sharedPrefs = getSharedPreferences(listOfAddedCities.get(i), MODE_PRIVATE);
                city.setTextOff("OFF");
                city.setTextOn("ON");
                city.setChecked(sharedPrefs.getBoolean(listOfAddedCities.get(i), true));
                Log.d(TAG, "onCreate: " + listOfAddedCities.get(i));
                if (city.isChecked()){
                    cityPart += listOfAddedCities.get(i) + "_";
                }
                city.setText(listOfAddedCities.get(i));
                city.setOnCheckedChangeListener(multiListener);
                ll.addView(city);
            }
        } else {
            Log.d(TAG, "onCreate: listOfAddedCities is empty!");
            listOfAddedCities = new ArrayList<>();
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityName = acTextView.getText().toString();
                if (!cityName.isEmpty()) {
                    cityCheck = cityName.substring(0, 1).toUpperCase() + cityName.substring(1);
                    Switch city = new Switch(CitySettingsActivity.this);
                    city.setTextOff("OFF");
                    city.setTextOn("ON");
                    city.setText(cityCheck);
                    city.setOnCheckedChangeListener(multiListener);



                    if (ListOfCities.toString().contains(cityCheck + ",") && !listOfAddedCities.contains(cityCheck + ",")) {
                        listOfAddedCities.add(cityCheck);
                        city.setChecked(true);
                        ll.addView(city);
                        acTextView.setText("");
                        Toast.makeText(CitySettingsActivity.this, "City " + cityCheck +
                                " added to filter settings.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CitySettingsActivity.this, "There is no City such as: " +
                                cityCheck + " or City " + cityCheck +
                                " is already added to filter settings.", Toast.LENGTH_SHORT).show();
                    }
                    saveData();
                    Log.d(TAG, "onClick: listOfAddedCities Check --------- " + listOfAddedCities);
                }
            }
        });
//        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener(){
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//            }
//        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        saveData();
        Log.d(TAG, "onDestroy: Triggered");
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("cityPart", MODE_PRIVATE);
        SharedPreferences.Editor cityPartEditor = sharedPreferences.edit();
        cityPartEditor.putString("cityPart part", cityPart);
        cityPartEditor.apply();
        Log.d(TAG, "saveData: Saving cityPart");


        SharedPreferences sharedPreferences1 = getSharedPreferences("listOfAddedCities", MODE_PRIVATE);
        SharedPreferences.Editor addedCitiesEditor = sharedPreferences1.edit();
        Gson gson = new Gson();
        String str = gson.toJson(listOfAddedCities);
        addedCitiesEditor.putString("addedCities", str);
        addedCitiesEditor.apply();
        Log.d(TAG, "saveData: Saving listOfAddedCities");

    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("cityPart", MODE_PRIVATE);
        cityPart = sharedPreferences.getString("cityPart part",cityPart);
        if (cityPart == null) {
            cityPart = "/all";
        }
        SharedPreferences sharedPreferences1 = getSharedPreferences("listOfAddedCities", MODE_PRIVATE);
        Gson gson = new Gson();

        String str = sharedPreferences1.getString("addedCities", null);
        Type type = new TypeToken<ArrayList>() {}.getType();
        listOfAddedCities = gson.fromJson(str,type);
        if (listOfAddedCities == null) {
            listOfAddedCities = new ArrayList<>();
        }

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
        Log.d(TAG, "ParseJSonResponse: ListOfCities" + ListOfCities.toString());
    }
}
