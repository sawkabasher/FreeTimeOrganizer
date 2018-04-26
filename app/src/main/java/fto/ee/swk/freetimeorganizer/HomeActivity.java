package fto.ee.swk.freetimeorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    public List<DataAdapter> ListOfdataAdapter;
    RecyclerView recyclerView;
    private String HTTP_JSON_URL;
    private String baseUrl = "https://fto.ee/api/v1/events/find";
    private String genrePart;
    private String cityPart;
    private String datePart;
    final String Event_Id_JSON = "id";
    final String Event_Name_JSON = "title";
    final String Event_Date_JSON = "date";
    final String Event_End_Date_JSON = "end_date";
    final String Event_Time_JSON = "time";
    final String Event_Location_JSON = "location";
    final String Event_City_JSON = "city";
    final String Event_Country_JSON = "country";
    final String Event_Genre_JSON = "genre";
    final String Event_Info_JSON = "info";
    final String Event_Link_JSON = "link";
    final String Image_URL_JSON = "img";
    final String Event_Price_JSON = "price";
    final String Event_Description_JSON = "des";
    public DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    JsonArrayRequest RequestOfJSonArray;
    RequestQueue requestQueue;
    View view;
    int RecyclerViewItemPosition;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    ArrayList<JSONObject> EventDataHold;
    SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "sawkabasher";


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                genrePart = data.getStringExtra("genrePart");
                Log.e(TAG,"onActivityResult" + genrePart);
            }
            JSON_HTTP_CALL(HTTP_JSON_URL);
        }
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                cityPart = data.getStringExtra("cityPart");
                Log.e(TAG,"onActivityResult" + cityPart);
            }
            JSON_HTTP_CALL(HTTP_JSON_URL);
        }
        if (requestCode == 3) {
            if(resultCode == RESULT_OK) {
                datePart = data.getStringExtra("datePart");
                Log.e(TAG,"onActivityResult" + datePart);
            }
            JSON_HTTP_CALL(HTTP_JSON_URL);
        }
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadData();
        HTTP_JSON_URL = baseUrl + datePart + cityPart + genrePart;
        JSON_HTTP_CALL(HTTP_JSON_URL);
        Log.i(TAG, "onPostResume: TRIGGERED ______________________________");
        Log.i(TAG, "HTTP_JSON_URL " + HTTP_JSON_URL);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setupDrawerContent(nvDrawer);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();


        EventDataHold = new ArrayList<>();
        ListOfdataAdapter = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);
        loadData();
        HTTP_JSON_URL = baseUrl + datePart + cityPart + genrePart;
        JSON_HTTP_CALL(HTTP_JSON_URL);
        Log.i(TAG, "onCreate: TRIGGERED ______________________________");
        Log.i(TAG, "HTTP_JSON_URL " + HTTP_JSON_URL);

// Pull to refresh
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
// cancel the Visual indication of a refresh
        swipeRefreshLayout.setRefreshing(false);
        loadData();
        JSON_HTTP_CALL(HTTP_JSON_URL);
            }
        });

        // Implementing Click Listener on RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(HomeActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);
                if (view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    Intent intent = new Intent(HomeActivity.this, EventDetailsActivity.class);
                    Bundle b = new Bundle();
                        b.putStringArray("details", new String[]{
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventName(),        // INDEX 0
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventDate(),        // INDEX 1
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventEndDate(),     // INDEX 2
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventTime(),        // INDEX 3
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventLocation(),    // INDEX 4
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventCity(),        // INDEX 5
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventCountry(),     // INDEX 6
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventGenre(),       // INDEX 7
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventInfo(),        // INDEX 8
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventLink(),        // INDEX 9
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventImageURL(),    // INDEX 10
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventPrice(),       // INDEX 11
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventDescription(), // INDEX 12
                                ListOfdataAdapter.get(RecyclerViewItemPosition).getEventID()           // INDEX 13
                        });
                    intent.putExtras(b);

                    startActivity(intent);
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }

        });

    }
    private void saveData(String data){
        SharedPreferences sharedPreferences = getSharedPreferences("HTTP_JSON_URL link", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("HTTP_JSON_URL link", data);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences genreSharedPreferences = getSharedPreferences("genrePart", MODE_PRIVATE);
        SharedPreferences dateSharedPreferences = getSharedPreferences("datePart", MODE_PRIVATE);
        SharedPreferences citySharedPreferences = getSharedPreferences("cityPart", MODE_PRIVATE);
        SharedPreferences HTTP_JSON_URLSharedPreferences = getSharedPreferences("HTTP_JSON_URL link", MODE_PRIVATE);


        genrePart = genreSharedPreferences.getString("genrePart part",genrePart);
        cityPart = citySharedPreferences.getString("cityPart part",cityPart);
        datePart = dateSharedPreferences.getString("datePart part",datePart);
        HTTP_JSON_URL = HTTP_JSON_URLSharedPreferences.getString("HTTP_JSON_URL link", HTTP_JSON_URL);
        if (genrePart == null) {
            genrePart = "";
            Log.i(TAG, "loadData: genrePart IS NULL!!!!");
        }
        if (cityPart == null) {
            cityPart = "/all";
            Log.i(TAG, "loadData: cityPart IS NULL!!!!");

        }
        if (datePart == null) {
            datePart = "/all";
            Log.i(TAG, "loadData: datePart IS NULL!!!!");
        }
        if (HTTP_JSON_URL == null) {
            HTTP_JSON_URL = "https://fto.ee/api/v1/events";
            Log.i(TAG, "loadData: HTTP_JSON_URL IS NULL!!!!");
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData(HTTP_JSON_URL);
        Log.i(TAG, "onDestroy: TRIGGERED _______________________________" + HTTP_JSON_URL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch(menuItem.getItemId()) {
                            case R.id.nav_find:
                                //todo seems like it works a bit different. not critical, but better change this to simple "start activity" instead of "start activity for result"
                                Intent genreSettingsIntent = new Intent(HomeActivity.this, GenreSettingsActivity.class);
                                startActivityForResult(genreSettingsIntent,1);
                                mDrawer.closeDrawers();

                                break;
                            case R.id.nav_location:
                                //startActivityForResult(settingsIntent,1);
                                Intent citySettingsIntent = new Intent(HomeActivity.this, CitySettingsActivity.class);
                                startActivityForResult(citySettingsIntent,2);
                                mDrawer.closeDrawers();
                                break;
                            case R.id.nav_time:
                                Intent dateSettingsIntent = new Intent(HomeActivity.this, DateSettingsActivity.class);
                                startActivityForResult(dateSettingsIntent,3);
                                mDrawer.closeDrawers();
                                //startActivityForResult(settingsIntent,1);
                                break;
                            case R.id.nav_liked:
                                Toast.makeText(HomeActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_random:
                                HTTP_JSON_URL = "https://fto.ee/api/v1/events/random";
                                JSON_HTTP_CALL(HTTP_JSON_URL);
                                mDrawer.closeDrawers();
                                break;

                        }
                        return true;
                    }
                });
        // Выделение существующего элемента выполнено с помощью
// NavigationView
        //        if (menuItem.isChecked()){
        //            menuItem.setChecked(false);
        //        } else menuItem.setChecked(true);
//Log.e("asd", HTTP_JSON_URL);
        // Установить заголовок для action bar'а
//        setTitle(menuItem.getTitle());
        // TODO --- nothing special, just note for myself.  mDrawer.closeDrawers();  Закрыть navigation drawer
//        mDrawer.closeDrawers();
    }



    public void JSON_HTTP_CALL(String url) {
        RequestOfJSonArray = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ListOfdataAdapter.clear();
                        EventDataHold.clear();
                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        requestQueue = Volley.newRequestQueue(HomeActivity.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            DataAdapter GetDataAdapter2 = new DataAdapter();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setEventID(json.getString(Event_Id_JSON));
                GetDataAdapter2.setEventName(json.getString(Event_Name_JSON));
                GetDataAdapter2.setEventDate(json.getString(Event_Date_JSON));
                GetDataAdapter2.setEventEndDate(json.getString(Event_End_Date_JSON));
                GetDataAdapter2.setEventTime(json.getString(Event_Time_JSON));
                GetDataAdapter2.setEventLocation(json.getString(Event_Location_JSON));
                GetDataAdapter2.setEventCity(json.getString(Event_City_JSON));
                GetDataAdapter2.setEventCountry(json.getString(Event_Country_JSON));
                GetDataAdapter2.setEventGenre(json.getString(Event_Genre_JSON));
                GetDataAdapter2.setEventInfo(json.getString(Event_Info_JSON));
                GetDataAdapter2.setEventLink(json.getString(Event_Link_JSON));
                GetDataAdapter2.setEventImageURL(json.getString(Image_URL_JSON));
                GetDataAdapter2.setEventPrice(json.getString(Event_Price_JSON));
                GetDataAdapter2.setEventDescription(json.getString(Event_Description_JSON));


//TODO ВНИМАНИЕ!!!!! КОСТЫЛЬ!!!!!
                if(json.getString(Image_URL_JSON).equals("null")){
                    GetDataAdapter2.setEventImageURL("https://pp.userapi.com/c622227/v622227481/ea18/Ac6o0JZhg6k.jpg");
                }
                else{
                    GetDataAdapter2.setEventImageURL(json.getString(Image_URL_JSON));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
//           Log.e("Test_list",  ListOfdataAdapter.get(0).getEventDate());
        }
        recyclerViewadapter = new RecyclerViewAdapter(ListOfdataAdapter, this);
        recyclerView.setAdapter(recyclerViewadapter);
    }
}

