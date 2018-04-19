package fto.ee.swk.freetimeorganizer;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ReportEventActivity extends AppCompatActivity {
    TextInputLayout reportInputLayout;
    Button submitReportButton;
    TextView eventIdReportTextView;
    TextView eventTitleReportTextView;
    String eventID;
    String URL_POST = "https://fto.ee/api/v1/reports/create";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_event);
        reportInputLayout = (TextInputLayout) findViewById(R.id.textReportInputLayout);
        submitReportButton = (Button) findViewById(R.id.submitReportButton);
        eventIdReportTextView = (TextView) findViewById(R.id.eventIdReportTextView2);
        eventTitleReportTextView = (TextView) findViewById(R.id.eventTitleReportTextView2);
        Bundle b = this.getIntent().getExtras();
        final String[] array = b.getStringArray("details");
        if (b != null) {
            eventIdReportTextView.setText("Id: " + array[0]);
            eventID = array[0];
            eventTitleReportTextView.setText("Title: " + array[1]);
        }

        submitReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTP_JSON_POST();

            }
        });

    } // onCreate end

    private void HTTP_JSON_POST(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                String eventId = eventIdReportTextView.getText().toString();
                String eventRepot = reportInputLayout.getEditText().getText().toString();
                params.put("event_id",eventID);
                params.put("report",eventRepot);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}



