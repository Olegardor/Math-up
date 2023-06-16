package by.bsuir.petrovskiy.mash_up.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CurrencyAsyncTask extends AsyncTask <Void, Void, String> {
    EditText editText_number;
    Spinner spinner_currensy_from, spinner_currensy_in;
    Context context;
    TextView textView_currency_conversion;

    public CurrencyAsyncTask(EditText editText_number, Spinner spinner_currensy_from, Spinner spinner_currensy_in, Context context, TextView textView_currency_conversion) {
        this.editText_number = editText_number;
        this.spinner_currensy_from = spinner_currensy_from;
        this.spinner_currensy_in = spinner_currensy_in;
        this.context = context;
        this.textView_currency_conversion = textView_currency_conversion;
    }

    protected String doInBackground (Void... voids) {
        String checkNull = editText_number.getText().toString();
        if (!TextUtils.isEmpty(checkNull)) {
            try {
                Double amount = Double.valueOf(checkNull);
                String fromCurrency = spinner_currensy_from.getSelectedItem().toString();
                String inCurrency = spinner_currensy_in.getSelectedItem().toString();

                String apiKey = "a0WLmf670pKhvicZch2AviUZ6eJnH97L";
                String url = "https://api.apilayer.com/exchangerates_data/convert?from=" + fromCurrency + "&to=" + inCurrency + "&amount=1";
                URLConnection connection = new URL(url).openConnection();
                connection.setRequestProperty("apikey", apiKey);
                connection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                Log.d("CurrencyResponce", response);
                reader.close();

                JSONObject jsonObject = new JSONObject(response);
                double exchangeRate = jsonObject.getDouble("result");

                double convertedAmount = amount * exchangeRate;
                String resultText = amount + " " + fromCurrency + " = " + convertedAmount + " " + inCurrency;
                Log.d("CurrencyResultText", resultText);
                return resultText;
            }
            catch (IOException e) {
                Log.e("CurrencyOIex", e.getMessage(), e);
            }
            catch (JSONException j) {
                Log.e("CurrencyJSONex", j.getMessage(), j);
            }
            catch (Exception ex) {
                Log.e("CurrencyEx", ex.getMessage(), ex);
            }
        }
        else {
            Toast.makeText(context, "Введите корректное значение суммы!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    protected void onPostExecute (String resultText) {
        textView_currency_conversion.setText(resultText);
    }
}
