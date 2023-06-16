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

import by.bsuir.petrovskiy.mash_up.AsyncTask.CurrencyAsyncTask;
import by.bsuir.petrovskiy.mash_up.AsyncTask.WeatherAsyncTask;
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
        spinner_currensy_from = (Spinner) findViewById(R.id.spinner_currensy_from);
        spinner_currensy_in = (Spinner) findViewById(R.id.spinner_currensy_in);
        button_conversion = (Button) findViewById(R.id.button_conversion);

        spinner_cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(textView_info_weather, spinner_cities);
                weatherAsyncTask.execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        button_conversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText_number.getText().toString())) {
                    CurrencyAsyncTask currencyAsyncTask = new CurrencyAsyncTask(editText_number, spinner_currensy_from, spinner_currensy_in, getApplicationContext(), textView_currency_conversion);
                    currencyAsyncTask.execute();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Введите корректное значение суммы!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("textView_info_weather", textView_info_weather.getText().toString());
        outState.putString("textView_currency_conversion", textView_currency_conversion.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView_info_weather.setText(savedInstanceState.getString("textView_info_weather"));
        textView_currency_conversion.setText(savedInstanceState.getString("textView_currency_conversion"));
    }
}
