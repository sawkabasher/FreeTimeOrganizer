package fto.ee.swk.freetimeorganizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class EventDetailsActivity extends AppCompatActivity {
    String Title;
    String ID;
    String Time;
    String StartDate;
    String City;
    String Description;
    String DateTime;
    String Location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back); // your drawable

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        TextView eventNameTextView1 = (TextView) findViewById(R.id.eventNameTextView1);
        TextView eventDateTextView1 = (TextView) findViewById(R.id.eventDateTextView1);
        TextView eventTimeTextView1 = (TextView) findViewById(R.id.eventTimeTextView1);
        TextView eventCityTextView1 = (TextView) findViewById(R.id.eventCityTextView1);
        TextView eventPriceTextView1 = (TextView) findViewById(R.id.eventPriceTextView1);
        final TextView eventDescriptionTextView1 = (TextView) findViewById(R.id.eventDescriptionTextView1);
        NetworkImageView VolleyImageView1 = (NetworkImageView) findViewById(R.id.VolleyImageView1);
        FloatingActionButton getTicketsButton = (FloatingActionButton) findViewById(R.id.button2);
        //eventInfoTextView1
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm");







        Bundle b = this.getIntent().getExtras();
        final String[] array = b.getStringArray("details");
        if (b != null) {
            Title = array[0];
            StartDate = array[1];
            StartDate += " - " + array[2].replace("-", ".");
            Time = "Time: " + array[3];
            ID = array[13];
            DateTime = array[2] + " " + array[3];
            Log.e("datetime", DateTime);
            Log.e("helpp", Arrays.toString(array));
            eventNameTextView1.setText(array[0]);

            eventDateTextView1.setText(array[1]);

            eventDateTextView1.append(" - " + array[2].replace("-", "."));
            eventTimeTextView1.setText(array[3]);
            eventCityTextView1.setText(array[4] + ", ");
            City = array[4] + ", ";
            if (!array[5].equals("null") && !array[5].equals("Estonia  ")) {
                eventCityTextView1.append(array[5] + ", ");
                City += array[5] + ", ";
                eventCityTextView1.append(array[6]);
                City += array[6];
            } else {
                eventCityTextView1.append(array[6]);
                City += array[6];
            }

            if(array[11].equals("")){
                eventPriceTextView1.setText("FREE / unknown" );
            }else {
                eventPriceTextView1.setText(array[11] + " €");
            }

            eventDescriptionTextView1.setText(array[12]);
            Description = array[12];
            VolleyImageView1.setImageUrl(array[10], RecyclerViewAdapter.getImageLoader());

/*
getEventName(),        // INDEX 0
getEventDate(),        // INDEX 1
getEventEndDate(),     // INDEX 2
getEventTime(),        // INDEX 3
getEventLocation(),    // INDEX 4
getEventCity(),        // INDEX 5
getEventCountry(),     // INDEX 6
getEventGenre(),       // INDEX 7
getEventInfo(),        // INDEX 8
getEventLink(),        // INDEX 9
getEventImageURL(),    // INDEX 10
getEventPrice(),       // INDEX 11
getEventDescription(), // INDEX 12
*/

        getTicketsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(array[9]));
                startActivity(i);
            }
        });
        }
        try {
            Date mDate = sdf.parse(DateTime);
            long timeInMilliseconds = mDate.getTime();
            Log.e("datetime", String.valueOf(timeInMilliseconds));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_details_menu, menu);
        // Locate MenuItem with ShareActionProvider
        // Fetch and store ShareActionProvider
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText =
                        Title + "\n" +
                        StartDate + "\n" +
                        Time + "\n" +
                        City;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
                return true;
            case R.id.add_event:
                //TODO something wrong with time, you can set date but time is wrong(set by default)
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, Title);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, City);
                intent.putExtra(CalendarContract.Events.ORIGINAL_INSTANCE_TIME, Time);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, DateTime);
                intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
                intent.putExtra(CalendarContract.Events.DESCRIPTION,Description);
                startActivity(intent);

                break;
            case R.id.report_a_bug:

                Intent reportIntent = new Intent(this, ReportEventActivity.class);
                Bundle b = new Bundle();
                b.putStringArray("details", new String[]{ID,Title});
                reportIntent.putExtras(b);
                startActivity(reportIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
