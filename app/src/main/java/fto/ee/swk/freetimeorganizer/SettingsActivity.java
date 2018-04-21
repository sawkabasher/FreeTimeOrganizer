package fto.ee.swk.freetimeorganizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
//genres
Switch music;
Switch family;
Switch theater;
Switch sport;
Switch film;
Switch festival;
Switch other;
//cities
Switch cityNarva;
Switch cityTallinn;
Switch cityTartu;
//dates
Switch dateToday;
Switch dateTomorrow;
Switch dateWeek;
Switch dateAll;


// todo  events/city/time/0/1/2/3/4/5/6/7
String link;
String cityPart;
String datePart;
String genrePart;
String HTTP_JSON_URL;
private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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

        music = (Switch) findViewById(R.id.switchMusic);
        family = (Switch) findViewById(R.id.switchFamily);
        theater = (Switch) findViewById(R.id.switchTheater);
        sport = (Switch) findViewById(R.id.switchSport);
        film = (Switch) findViewById(R.id.switchFilm);
        festival = (Switch) findViewById(R.id.switchFestival);
        other = (Switch) findViewById(R.id.switchOther);

        cityNarva = (Switch) findViewById(R.id.switchNarva);
        cityTallinn = (Switch) findViewById(R.id.switchTallinn);
        cityTartu = (Switch) findViewById(R.id.switchTartu);

        dateToday = (Switch) findViewById(R.id.switchToday);
        dateTomorrow = (Switch) findViewById(R.id.switchTomorrow);
        dateWeek = (Switch) findViewById(R.id.switchWeek);
        dateAll = (Switch) findViewById(R.id.switchAllTime);
        link ="https://fto.ee/api/v1/events/genre";
        cityPart = "";
        datePart = "";
        genrePart = "";

    } //OnCreate end

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.submit:
//genre
                if (music.isChecked()){
                    genrePart += "/1";
                } else genrePart = genrePart.replaceAll("/1","");
                if (family.isChecked()){
                    genrePart += "/3";
                } else genrePart = genrePart.replaceAll("/3","");
                if (theater.isChecked()){
                    genrePart += "/2";
                } else genrePart = genrePart.replaceAll("/2","");
                if (sport.isChecked()){
                    genrePart += "/4";
                } else genrePart = genrePart.replaceAll("/4","");
                if (film.isChecked()){
                    genrePart += "/6";
                } else genrePart = genrePart.replaceAll("/6","");
                if (festival.isChecked()){
                    genrePart += "/5";
                } else genrePart = genrePart.replaceAll("/5","");
                if (other.isChecked()){
                    genrePart += "/0";
                } else genrePart = genrePart.replaceAll("/0","");
//city
                if (cityNarva.isChecked()){
                    cityPart += "/Narva";
                } else cityPart = cityPart.replaceAll("/Narva","");
                if (cityTallinn.isChecked()){
                    cityPart += "/Tallinn";
                } else cityPart = cityPart.replaceAll("/Tallinn","");
                if (cityTartu.isChecked()){
                    cityPart += "/Tartu";
                } else cityPart = cityPart.replaceAll("/Tartu","");
//date
                if (dateToday.isChecked()){
                    datePart += "/Today";
                } else datePart = datePart.replaceAll("/Today","");
                if (dateTomorrow.isChecked()){
                    datePart += "/Tomorrow";
                } else datePart = datePart.replaceAll("/Tomorrow","");
                if (dateWeek.isChecked()){
                    datePart += "/Week";
                } else datePart = datePart.replaceAll("/Week","");
                if (dateAll.isChecked()){
                    datePart += "/All";
                } else datePart = datePart.replaceAll("/All","");



                link += cityPart + datePart + genrePart;
                //todo remove toast below .|.
//                Toast.makeText(getApplicationContext(), link, Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("url", link);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
     return true;
    }
}
