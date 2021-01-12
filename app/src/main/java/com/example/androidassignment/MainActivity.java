package com.example.androidassignment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidassignment.adapter.CustomAdapter;
import com.example.androidassignment.http.HttpService;
import com.example.androidassignment.model.Books;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private static String baseUrl = "https://api.nytimes.com/svc/books/v2/lists/overview.json?published_date=";
    private static String apiKey = "76363c9e70bc401bac1e6ad88b13bd1d";

    private ArrayList<Books> booksList;
    private ListView listView;
    private EditText editText;

    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        editText = findViewById(R.id.edit_text);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(adapter != null)
                    adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                    JSONObject results = jsonObj.getJSONObject("results");
                    JSONArray lists = results.getJSONArray("lists");

                    mainActivity.booksList = new ArrayList<>();

                    for(int i = 0; i < lists.length();i++) {
                        JSONObject lobj = lists.getJSONObject(i);
                        JSONArray books = lobj.getJSONArray("books");

                        for(int j = 0; j < books.length(); j++) {
                            JSONObject obj = books.getJSONObject(j);

                            Books book = new Books();
                            book.setTitle(obj.getString("title"));
                            book.setAuthor(obj.getString("author"));
                            book.setPublisher(obj.getString("publisher"));
                            book.setContributor(obj.getString("contributor"));
                            book.setDescription(obj.getString("description"));

                            mainActivity.booksList.add(book);
                        }
                    }
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
            final MainActivity mainActivity = activityReference.get();
            mainActivity.adapter = new CustomAdapter(mainActivity.booksList,mainActivity);
            mainActivity.listView.setAdapter(mainActivity.adapter);
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
