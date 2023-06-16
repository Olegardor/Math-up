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
        spinner_currensy_from = (Spinner) findViewById(R.id.spinner_currensy_in);
        spinner_currensy_in = (Spinner) findViewById(R.id.spinner_currensy_from);
        button_conversion = (Button) findViewById(R.id.button_conversion);

        spinner_cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask(textView_info_weather);
                weatherAsyncTask.execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
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
}
