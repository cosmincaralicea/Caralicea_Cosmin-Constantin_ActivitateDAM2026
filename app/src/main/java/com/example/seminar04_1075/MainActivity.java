package com.example.seminar04_1075;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvRezultat;

    private final ActivityResultLauncher<Intent> adaugaCalculatorLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();

                    if (bundle != null) {
                        Calculator calculatorPrimit = (Calculator) bundle.getSerializable(AddActivityCalculator.EXTRA_CALCULATOR);

                        if (calculatorPrimit != null) {
                            Toast.makeText(this, "Salvat: " + calculatorPrimit.getModel(), Toast.LENGTH_LONG).show();
                            tvRezultat.setText(calculatorPrimit.toString());
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClasic = findViewById(R.id.btn_clasic);
        Button btnAI = findViewById(R.id.btn_ai);
        tvRezultat = findViewById(R.id.tv_rezultat);

        btnClasic.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivityCalculator.class);
            adaugaCalculatorLauncher.launch(intent);
        });

        btnAI.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCalculatorAIActivity.class);
            adaugaCalculatorLauncher.launch(intent);
        });
    }
}