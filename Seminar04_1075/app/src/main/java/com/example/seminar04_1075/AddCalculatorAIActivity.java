package com.example.seminar04_1075;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddCalculatorAIActivity extends AppCompatActivity {

    public static final String EXTRA_CALCULATOR = "extra_calculator";

    private EditText modelEt, pretEt;
    private Spinner marcaSp;
    private RatingBar performantaRb;
    private CheckBox esteNouCb;
    private RadioGroup tipRg;
    private Button salveazaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calculator_ai);

        modelEt = findViewById(R.id.model_et_ai);
        pretEt = findViewById(R.id.pret_et_ai);
        marcaSp = findViewById(R.id.marca_sp_ai);
        performantaRb = findViewById(R.id.performanta_rb_ai);
        esteNouCb = findViewById(R.id.esteNou_cb_ai);
        tipRg = findViewById(R.id.tip_rg_ai);
        salveazaBtn = findViewById(R.id.salveaza_btn_ai);

        String[] marci = {"Asus", "Dell", "Lenovo", "Apple", "HP"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, marci);
        marcaSp.setAdapter(adapter);

        salveazaBtn.setOnClickListener(v -> salveazaDate());
    }

    private void salveazaDate() {
        String model = modelEt.getText().toString().trim();
        String pretStr = pretEt.getText().toString().trim();

        if (model.isEmpty() || pretStr.isEmpty()) {
            Toast.makeText(this, "Completați modelul și prețul!", Toast.LENGTH_SHORT).show();
            return;
        }

        double pret = Double.parseDouble(pretStr);
        String marca = marcaSp.getSelectedItem().toString();
        float performanta = performantaRb.getRating();
        boolean esteNou = esteNouCb.isChecked();

        String tipSistem = (tipRg.getCheckedRadioButtonId() == R.id.desktop_rb_ai) ? "Desktop PC" : "Laptop";

        Calculator calculator = new Calculator(model, pret, marca, performanta, esteNou, tipSistem);

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CALCULATOR, calculator);
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        finish();
    }
}