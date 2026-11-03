package com.example.seminar04_1075;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaCalculatoareActivity extends AppCompatActivity {

    private CalculatorAdapter adapter;
    private int pozitieModificata = -1;

    private final ActivityResultLauncher<Intent> editCalculatorLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    if (bundle != null) {
                        Calculator calculatorModificat = bundle.getParcelable(AddActivityCalculator.EXTRA_CALCULATOR);
                        if (calculatorModificat != null && pozitieModificata != -1) {
                            ArrayList<Calculator> lista = getIntent().getParcelableArrayListExtra("LISTA_CALCULATOARE");
                            if (lista != null) {
                                lista.set(pozitieModificata, calculatorModificat);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(this, "Modificat cu succes!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_calculatoare);

        ListView listView = findViewById(R.id.lv_calculatoare);

        ArrayList<Calculator> lista = getIntent().getParcelableArrayListExtra("LISTA_CALCULATOARE");

        if (lista != null) {
            adapter = new CalculatorAdapter(lista, this, R.layout.item_calculator);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                pozitieModificata = position;
                Calculator calculatorSelectat = lista.get(position);
                Intent intent = new Intent(ListaCalculatoareActivity.this, AddActivityCalculator.class);
                intent.putExtra(AddActivityCalculator.EXTRA_CALCULATOR, calculatorSelectat);
                editCalculatorLauncher.launch(intent);
            });

            listView.setOnItemLongClickListener((parent, view, position, id) -> {
                lista.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(ListaCalculatoareActivity.this, "Elementul a fost șters!", Toast.LENGTH_SHORT).show();
                return true;
            });
        }
    }
}