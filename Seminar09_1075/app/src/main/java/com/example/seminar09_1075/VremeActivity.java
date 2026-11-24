package com.example.seminar09_1075;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VremeActivity extends AppCompatActivity {

    private final String apiKey = "cheie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vreme);

        EditText etOras = findViewById(R.id.et_oras);
        Spinner spZile = findViewById(R.id.sp_zile);
        Button btnCauta = findViewById(R.id.btn_cauta);
        TextView tvRezultat = findViewById(R.id.tv_rezultat);

        String[] optiuni = {"1 zi", "5 zile"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, optiuni);
        spZile.setAdapter(adapter);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        btnCauta.setOnClickListener(v -> {
            String oras = etOras.getText().toString().trim();
            if (oras.isEmpty()) {
                return;
            }

            String metoda = spZile.getSelectedItemPosition() == 0 ? "1day" : "5day";

            executor.execute(() -> {
                try {
                    String orasCodat = URLEncoder.encode(oras, "UTF-8");
                    String linkOras = "https://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + apiKey + "&q=" + orasCodat;
                    String raspunsOras = citesteDate(linkOras);

                    JSONArray vector = new JSONArray(raspunsOras);
                    if (vector.length() == 0) {
                        handler.post(() -> tvRezultat.setText("Orasul nu a fost gasit."));
                        return;
                    }
                    JSONObject obiect = vector.getJSONObject(0);
                    String cheieOras = obiect.getString("Key");

                    handler.post(() -> Toast.makeText(VremeActivity.this, "Cheie oras: " + cheieOras, Toast.LENGTH_SHORT).show());

                    String linkVreme = "https://dataservice.accuweather.com/forecasts/v1/daily/" + metoda + "/" + cheieOras + "?apikey=" + apiKey + "&metric=true";
                    String raspunsVreme = citesteDate(linkVreme);

                    JSONObject obiectVreme = new JSONObject(raspunsVreme);
                    JSONArray dailyForecasts = obiectVreme.getJSONArray("DailyForecasts");

                    StringBuilder afisaj = new StringBuilder();
                    afisaj.append("Cod oras: ").append(cheieOras).append("\n\n");

                    for (int i = 0; i < dailyForecasts.length(); i++) {
                        JSONObject zi = dailyForecasts.getJSONObject(i);
                        String data = zi.getString("Date").substring(0, 10);
                        JSONObject temperature = zi.getJSONObject("Temperature");
                        double min = temperature.getJSONObject("Minimum").getDouble("Value");
                        double max = temperature.getJSONObject("Maximum").getDouble("Value");

                        afisaj.append("Data: ").append(data)
                                .append("\nMin: ").append(min).append(" C | Max: ").append(max).append(" C\n\n");
                    }

                    handler.post(() -> tvRezultat.setText(afisaj.toString()));

                } catch (Exception e) {
                    e.printStackTrace();
                    String mesajEroare = e.getClass().getSimpleName() + ": " + e.getMessage();
                    handler.post(() -> tvRezultat.setText("Eroare: " + mesajEroare));
                }
            });
        });
    }

    private String citesteDate(String link) throws Exception {
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int code = conn.getResponseCode();
        InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        String linie;
        while ((linie = reader.readLine()) != null) {
            builder.append(linie);
        }
        reader.close();
        conn.disconnect();
        if (code < 200 || code >= 300) {
            throw new Exception("HTTP " + code + " - " + builder.toString());
        }
        return builder.toString();
    }
}