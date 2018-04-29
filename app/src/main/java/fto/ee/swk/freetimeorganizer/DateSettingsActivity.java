package fto.ee.swk.freetimeorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class DateSettingsActivity extends AppCompatActivity {
    private String datePart;
    private static final String TAG = "DateSettingsActivity";
    private Toolbar toolbar;
    private RadioButton radioAllTime;
    private RadioButton greenRadioButton;
    private RadioButton radioTomorrow;
    private RadioButton radioThisWeek;
    private RadioButton radioThisMonth;
    private RadioButton radioPickDate;
    private String date;

    private CalendarView calendar;
    private LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_settings);

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
        radioAllTime = (RadioButton)findViewById(R.id.radioAllTime);
        radioAllTime.setOnClickListener(radioButtonClickListener);

        greenRadioButton = (RadioButton)findViewById(R.id.radioToday);
        greenRadioButton.setOnClickListener(radioButtonClickListener);

        radioTomorrow = (RadioButton)findViewById(R.id.radioTomorrow);
        radioTomorrow.setOnClickListener(radioButtonClickListener);

        radioThisWeek = (RadioButton)findViewById(R.id.radioThisWeek);
        radioThisWeek.setOnClickListener(radioButtonClickListener);

        radioThisMonth = (RadioButton)findViewById(R.id.ThisMonth);
        radioThisMonth.setOnClickListener(radioButtonClickListener);

        radioPickDate = (RadioButton) findViewById(R.id.pickDateRadio);
        radioPickDate.setOnClickListener(radioButtonClickListener);

        ll = (LinearLayout) findViewById(R.id.pickDate);
        ll.setVisibility(View.GONE);


        calendar = (CalendarView) findViewById(R.id.pickDateCalendar);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "-" + (month + 1) + "-" + dayOfMonth;
                Log.d(TAG, "onSelectedDayChange: date = " + date);
                datePart = "/" + date;
                saveData(datePart);
            }
        });
    }
    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;
            switch (rb.getId()) {
                case R.id.radioAllTime:
                    datePart = "/all";
                    Log.i(TAG, "onClick: "+datePart);
                    saveData(datePart);
                    break;
                case R.id.radioToday:
                    datePart = "/today";
                    Log.i(TAG, "onClick: "+datePart);
                    saveData(datePart);
                    break;
                case R.id.radioTomorrow:
                    datePart = "/tomorrow";
                    Log.i(TAG, "onClick: "+datePart);
                    saveData(datePart);
                    break;
                case R.id.radioThisWeek:
                    datePart = "/week";
                    Log.i(TAG, "onClick: "+datePart);
                    saveData(datePart);
                    break;
                case R.id.ThisMonth:
                    datePart = "/month";
                    Log.i(TAG, "onClick: " + datePart);
                    saveData(datePart);
                    break;
                case R.id.pickDateRadio:

                    ll.setVisibility(View.VISIBLE);

                    break;

                default:
                    ll.setVisibility(View.GONE);
                    break;
            }
        }
    };



    private void saveData(String data) {
        SharedPreferences sharedPreferences = getSharedPreferences("datePart", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("datePart part", data);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("datePart", MODE_PRIVATE);
        datePart = sharedPreferences.getString("datePart part", datePart);
        if (datePart == null) {
            datePart = "/all";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.putExtra("datePart", datePart);
        setResult(RESULT_OK, intent);
        saveData(datePart);
        Log.d(TAG, "onDestroy: Triggered. datePart=" + datePart);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Tiggered. =================");
    }
}
