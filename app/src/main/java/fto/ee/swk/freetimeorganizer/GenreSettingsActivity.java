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


public class GenreSettingsActivity extends AppCompatActivity {

    private static final String TAG = "sawkabasher";
    private String genrePart = "/";
    private List<GenreDataObject> ListOfGenres;
    JsonArrayRequest RequestOfJSonArray;
    RequestQueue requestQueue;
    LinearLayout ll;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_settings);
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

        ListOfGenres = new ArrayList<>();
        JSON_HTTP_CALL("https://fto.ee/api/v1/genres");
        Log.e(TAG, ListOfGenres.toString());
        ll = (LinearLayout) findViewById(R.id.l_layout);
        Log.i(TAG,"genrePart === " + genrePart);


    } //onCreate end

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadData();
        Log.i(TAG, "onPostResume:loaddata: " + genrePart);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.putExtra("genrePart", genrePart);
        setResult(RESULT_OK, intent);
        saveData(genrePart);
        Log.d(TAG, "onDestroy: Triggered. genrePart=" + genrePart);
        finish();

    }

    //TODO create "X" button which will return to HomeActivity and close mDrawer ,, ===> back toolbar button doesn't close the mDrawer

    private void saveData(String genrePart){
        SharedPreferences sharedPreferences = getSharedPreferences("genrePart", MODE_PRIVATE);
        SharedPreferences.Editor genrePartEditor = sharedPreferences.edit();
        genrePartEditor.putString("genrePart part", genrePart);
        genrePartEditor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("genrePart", MODE_PRIVATE);
        genrePart = sharedPreferences.getString("genrePart part",genrePart);
        if (genrePart == null) {
            genrePart = "";
        }
    }


    
    /**
     creates switches
    */
    public void populateCardView(){
        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int genre_id = buttonView.getId();
                if (buttonView.isChecked()) {
                    genrePart +=  genre_id + "_";
                    Log.e("GenreSettingsActivity", "genrePart === " + genrePart);
                    saveData(genrePart);
                    //Toast.makeText(GenreSettingsActivity.this, buttonView.getId() + "Checked", Toast.LENGTH_SHORT).show();

                    //save switch state
                    SharedPreferences.Editor editor = getSharedPreferences((String) buttonView.getText(), MODE_PRIVATE).edit();
                    editor.putBoolean((String) buttonView.getText(), true);
                    editor.apply();
                } else {
                    genrePart = genrePart.replaceAll( String.valueOf(genre_id) + "_","");
                    Log.e("genrePart ===", genrePart);
                    //Toast.makeText(GenreSettingsActivity.this, buttonView.getId() + "Unchecked", Toast.LENGTH_SHORT).show();
                    saveData(genrePart);
                    //save switch state
                    SharedPreferences.Editor editor = getSharedPreferences((String) buttonView.getText(), MODE_PRIVATE).edit();
                    editor.putBoolean((String) buttonView.getText(), false);
                    editor.apply();

                }
            }
        };
        for (int i = 0; i < ListOfGenres.size(); i++) {
            Switch genre = new Switch(this);
        SharedPreferences sharedPrefs = getSharedPreferences(ListOfGenres.get(i).getGenre(), MODE_PRIVATE);
        genre.setTextOff("OFF");
        genre.setTextOn("ON");
        genre.setChecked(sharedPrefs.getBoolean(ListOfGenres.get(i).getGenre(), true));
            if (genre.isChecked()){
                genrePart +=  ListOfGenres.get(i).getid() + "_";
            }
        genre.setText(ListOfGenres.get(i).getGenre());
        genre.setId(ListOfGenres.get(i).getid());
        genre.setOnCheckedChangeListener(multiListener);
        ll.addView(genre);
    }
        Log.e(TAG, ListOfGenres.toString());
    }

    public void JSON_HTTP_CALL(String url) {
        RequestOfJSonArray = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ListOfGenres.clear();
                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        requestQueue = Volley.newRequestQueue(GenreSettingsActivity.this);
        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            GenreDataObject obj = new GenreDataObject();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                obj.setid(json.getInt("id"));
                obj.setGenre(json.getString("genre"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListOfGenres.add(obj);
        }
        populateCardView();
    }

}

