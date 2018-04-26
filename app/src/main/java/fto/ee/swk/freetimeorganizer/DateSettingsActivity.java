package fto.ee.swk.freetimeorganizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class DateSettingsActivity extends AppCompatActivity {
    private String datePart;
    private static final String TAG = "DateSettingsActivity";
    Toolbar toolbar;


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
        RadioButton radioAllTime = (RadioButton)findViewById(R.id.radioAllTime);
        radioAllTime.setOnClickListener(radioButtonClickListener);

        RadioButton greenRadioButton = (RadioButton)findViewById(R.id.radioToday);
        greenRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton radioTomorrow = (RadioButton)findViewById(R.id.radioTomorrow);
        radioTomorrow.setOnClickListener(radioButtonClickListener);

        RadioButton radioThisWeek = (RadioButton)findViewById(R.id.radioThisWeek);
        radioThisWeek.setOnClickListener(radioButtonClickListener);

        RadioButton radioThisMonth = (RadioButton)findViewById(R.id.ThisMonth);
        radioThisMonth.setOnClickListener(radioButtonClickListener);
    }
    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;
            switch (rb.getId()) {
                case R.id.radioAllTime: datePart = "/All";
                    Toast.makeText(DateSettingsActivity.this, datePart, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: "+datePart);
                    saveData(datePart);
                    break;
                case R.id.radioToday: datePart = "/today";
                    Toast.makeText(DateSettingsActivity.this, datePart, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: "+datePart);
                    saveData(datePart);
                    break;
                case R.id.radioTomorrow: datePart = "/tomorrow";
                    Toast.makeText(DateSettingsActivity.this, datePart, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: "+datePart);
                    saveData(datePart);
                    break;
                case R.id.radioThisWeek: datePart = "/week";
                    Toast.makeText(DateSettingsActivity.this, datePart, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: "+datePart);
                    saveData(datePart);
                    break;
                case R.id.ThisMonth: datePart = "/month";
                    Toast.makeText(DateSettingsActivity.this, datePart, Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: "+datePart);
                    Toast.makeText(DateSettingsActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                    saveData(datePart);
                    break;

                default:
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
}
