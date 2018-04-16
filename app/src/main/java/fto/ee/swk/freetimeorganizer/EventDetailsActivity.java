package fto.ee.swk.freetimeorganizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class EventDetailsActivity extends AppCompatActivity {
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        TextView eventNameTextView1 = (TextView) findViewById(R.id.eventNameTextView1);
        TextView eventDateTextView1 = (TextView) findViewById(R.id.eventDateTextView1);
        TextView eventTimeTextView1 = (TextView) findViewById(R.id.eventTimeTextView1);
        TextView eventCityTextView1 = (TextView) findViewById(R.id.eventCityTextView1);
        TextView eventDescriptionTextView1 = (TextView) findViewById(R.id.eventDescriptionTextView1);
        NetworkImageView VolleyImageView1 = (NetworkImageView) findViewById(R.id.VolleyImageView1);
        Button getTicketsButton = (Button)findViewById(R.id.button2);
        //eventInfoTextView1

        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            final String[] array = b.getStringArray("details");
//            Log.e("helpp", Arrays.toString(array));
            eventNameTextView1.setText(array[0]);
            eventDateTextView1.setText(array[1]);
            eventDateTextView1.append(" - " + array[2].replace("-", "."));
            eventTimeTextView1.setText(array[3]);
            eventCityTextView1.setText(array[4]+ ", ");
            if (!array[5].equals("null") && !array[5].equals("Estonia  ")) {
                eventCityTextView1.append(array[5] + ", ");
                eventCityTextView1.append(array[6]);
            } else {
                eventCityTextView1.setText(array[6]);
            }
            eventDescriptionTextView1.setText(array[12]);
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

        /*Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("Event_name_JSON");
            eventNameTextView1.setText(value);

            //The key argument here must match that used in the other activity
        }*/
    }
}
