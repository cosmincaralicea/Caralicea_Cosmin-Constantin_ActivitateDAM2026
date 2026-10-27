package com.example.seminar04_1075;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaCalculatoareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_calculatoare);

        ListView listView = findViewById(R.id.lv_calculatoare);

        ArrayList<Calculator> lista = (ArrayList<Calculator>) getIntent().getSerializableExtra("LISTA_CALCULATOARE");

        if (lista != null) {
            ArrayAdapter<Calculator> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                Calculator calculatorSelectat = lista.get(position);
                Toast.makeText(ListaCalculatoareActivity.this, calculatorSelectat.toString(), Toast.LENGTH_LONG).show();
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