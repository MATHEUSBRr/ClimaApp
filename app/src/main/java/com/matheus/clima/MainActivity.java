package com.matheus.clima;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private List<Weather> weatherList = new ArrayList<>();
    private WeatherArrayAdapter weatherArrayAdapter;
    private ListView weatherListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherListView = findViewById(R.id.weatherListView);
        weatherArrayAdapter = new WeatherArrayAdapter(this, weatherList);
        weatherListView.setAdapter(weatherArrayAdapter);

        final EditText locationEditText = findViewById(R.id.locationEditText);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = locationEditText.getText().toString().trim();
                URL url = createURL(city);
                if (url != null) {
                    dismissKeyboard(locationEditText);
                    new GetWeatherTask(view).execute(url);
                } else {
                    Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.invalid_url, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private URL createURL(String city) {
        try {
            if (city == null || city.isEmpty()) return null;

            String baseUrl = getString(R.string.web_service_url);
            String apiKey = getString(R.string.api_key);

            String urlString = baseUrl
                    + "?city=" + URLEncoder.encode(city, "UTF-8")
                    + "&days=7"
                    + "&APPID=" + URLEncoder.encode(apiKey, "UTF-8");

            Log.e("API_URL", "URL GERADA >>> " + urlString);

            return new URL(urlString);

        } catch (Exception e) {
            Log.e("API_URL", "ERRO AO MONTAR URL", e);
            return null;
        }
    }


    private class GetWeatherTask extends AsyncTask<URL, Void, JSONObject> {

        private View anchor;

        GetWeatherTask(View anchor) { this.anchor = anchor; }

        @Override
        protected JSONObject doInBackground(URL... params) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) params[0].openConnection();

                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)");

                int response = connection.getResponseCode();
                Log.e("API_HTTP", "HTTP RESPONSE CODE: " + response);

                BufferedReader reader;
                if (response == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                } else {
                    if (connection.getErrorStream() != null) {
                        reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    } else {
                        Log.e("API_HTTP", "No error stream available");
                        return null;
                    }
                }

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();

                Log.e("API_JSON", "JSON RECEBIDO >>> " + builder.toString());

                if (response == HttpURLConnection.HTTP_OK) {
                    return new JSONObject(builder.toString());
                } else {
                    return null;
                }
            } catch (IOException | JSONException e) {
                Log.e("API_HTTP", "EXCEPTION reading API", e);
                return null;
            } finally {
                if (connection != null) connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(JSONObject forecast) {
            if (forecast == null) {
                Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.read_error, Snackbar.LENGTH_LONG).show();
                return;
            }
            convertJSONtoArrayList(forecast);
            weatherArrayAdapter.notifyDataSetChanged();
            weatherListView.smoothScrollToPosition(0);
            Snackbar.make(findViewById(R.id.coordinatorLayout), "Dados atualizados", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void convertJSONtoArrayList(JSONObject forecast) {
        weatherList.clear();
        try {
            JSONArray days = forecast.getJSONArray("days");
            for (int i = 0; i < days.length(); i++) {
                JSONObject day = days.getJSONObject(i);
                String date = day.optString("date", "");
                double min = day.optDouble("minTempC", 0.0);
                double max = day.optDouble("maxTempC", 0.0);
                double humidity = day.optDouble("humidity", 0.0);
                if (humidity > 1.0) {
                    humidity = humidity / 100.0;
                }
                String description = day.optString("description", "");
                String icon = day.optString("icon", "");
                weatherList.add(new Weather(date, min, max, humidity, description, icon));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
