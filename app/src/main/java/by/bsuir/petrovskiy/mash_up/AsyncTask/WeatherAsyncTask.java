package by.bsuir.petrovskiy.mash_up.AsyncTask;

import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.util.Log;
import android.widget.TextView;

import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class WeatherAsyncTask extends AsyncTask<String, Void, String> {
    String cities;
    TextView textView_info_weather;

    public WeatherAsyncTask(TextView textView_info_weather) {
        this.textView_info_weather = textView_info_weather;
    }
    protected String doInBackground (String... strings) {
        try {
            cities = strings[0];
            String apiKey = "842874aa24b6923e2ecd2fe43eb32331";
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cities + "&appid=" + apiKey + "&units=metric";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            JSONObject jsonObject = new JSONObject(responseString);

            if (jsonObject.has("main")) {
                JSONObject mainData = jsonObject.getJSONObject("main");
                double temperature = mainData.getDouble("temp");
                double temp_min = mainData.getDouble("temp_min");
                double temp_max = mainData.getDouble("temp_max");
                double pressure = mainData.getDouble("pressure");
                double humidity = mainData.getDouble("humidity");

                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                String weatherDescription = weatherObject.getString("description");

                String weatherData = "Температура: " + temperature + " °C\nОписание: " + weatherDescription + "\nТемпература min: " + temp_min + " °C\nТемпература max: " + temp_max + " °C\nДавление: " + pressure + " mm\n" +
                        "Влажность: " + humidity + " %";
                return weatherData;
            } else {
                throw new IOException("Invalid response format");
            }
        }
        catch (IOException e) {
            Log.e("WeatherOIex", e.getMessage(), e);
        }
        catch (JSONException j) {
            Log.e("WeatherJSONex", j.getMessage(), j);
        }
        return null;
    }

    protected void onPostExecute (String weatherData) {
        textView_info_weather.setText("Погода в " + cities + ": \n" + weatherData);
    }
}
