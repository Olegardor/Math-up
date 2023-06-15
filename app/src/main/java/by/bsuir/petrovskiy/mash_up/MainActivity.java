package by.bsuir.petrovskiy.mash_up;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView_info_weather;
    TextView textView_currency_conversion;
    EditText editText_number;
    Spinner spinner_cities;
    Spinner spinner_currensy_in;
    Spinner spinner_currensy_from;
    Button button_conversion;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        textView_info_weather = (TextView) findViewById(R.id.textView_info_weather);
        textView_currency_conversion = (TextView) findViewById(R.id.textView_currency_conversion);
        editText_number = (EditText) findViewById(R.id.editText_number);
        spinner_cities = (Spinner) findViewById(R.id.spinner_cities);
        spinner_currensy_from = (Spinner) findViewById(R.id.spinner_currensy_in);
        spinner_currensy_in = (Spinner) findViewById(R.id.spinner_currensy_from);
        button_conversion = (Button) findViewById(R.id.button_conversion);

        spinner_cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cities = spinner_cities.getSelectedItem().toString();
                fetchWeather(cities);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkNull = editText_number.getText().toString();
                if (!TextUtils.isEmpty(checkNull)) {
                    Double amount = Double.valueOf(checkNull);
                    String fromCurrensy = spinner_currensy_from.getSelectedItem().toString();
                    String inCurrensy = spinner_currensy_in.getSelectedItem().toString();
                    //fetchExchangeRate(amount, fromCurrensy, inCurrensy);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Введите корректное значение суммы!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void fetchWeather (String cities) {
        try {
            String infoWeather = fetchWeatherFromAPI(cities);

            textView_info_weather.setText("Погода в " + cities + ": \n" + infoWeather);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ошибка при выполнении запроса! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String fetchWeatherFromAPI (String cities) throws IOException, JSONException {
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


}
