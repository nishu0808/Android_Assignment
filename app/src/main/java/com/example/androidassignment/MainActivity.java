package com.example.androidassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.androidassignment.http.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private static String baseUrl = "https://api.nytimes.com/svc/books/v2/lists/overview.json?published_date=";
    private static String apiKey = "76363c9e70bc401bac1e6ad88b13bd1d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchData(this).execute();
    }

    private static class FetchData extends AsyncTask<Void, Void, Void> {
        private WeakReference<MainActivity> activityReference;

        FetchData(MainActivity mainActivity) {
            activityReference = new WeakReference<>(mainActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            final MainActivity mainActivity = activityReference.get();

            HttpService sh = new HttpService();
            // Making a request to url and getting response
            String url = baseUrl + mainActivity.getCurrentDate() + "&api-key=" + apiKey;
            String jsonStr = sh.makeHTTPRequest(url);

            Log.d(TAG, "Response " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                } catch (final JSONException e) {
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.showToast("Error: " + e.getMessage());
                        }
                    });
                }
            } else {
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.showToast("Couldn't fetch json from server");
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    /**
     * Function to get current date
     * @return current date in yyyy-MM-dd format
     */
    private String getCurrentDate() {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return formatter.format(todayDate);
    }
}
