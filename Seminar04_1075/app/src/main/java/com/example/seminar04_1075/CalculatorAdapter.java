package com.example.seminar04_1075;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CalculatorAdapter extends BaseAdapter {

    private List<Calculator> calculatoare;
    private Context ctx;
    private int resursaLayout;

    public CalculatorAdapter(List<Calculator> calculatoare, Context ctx, int resursaLayout) {
        this.calculatoare = calculatoare;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return calculatoare.size();
    }

    @Override
    public Object getItem(int position) {
        return calculatoare.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(resursaLayout, parent, false);

        TextView modelMarcaTv = v.findViewById(R.id.tv_row_model_marca);
        TextView pretTv = v.findViewById(R.id.tv_row_pret);
        TextView detaliiTv = v.findViewById(R.id.tv_row_detalii);

        Calculator calculator = (Calculator) getItem(position);

        modelMarcaTv.setText(calculator.getMarca() + " " + calculator.getModel());
        pretTv.setText(calculator.getPret() + " RON");

        String starea = calculator.isEsteNou() ? "Nou" : "Second Hand";
        String garantia = calculator.isAreGarantie() ? "Cu garanție" : "Fără garanție";

        String textDetalii = calculator.getTipSistem() + " | "
                + starea + " | Rating: "
                + calculator.getPerformanta() + " stele | "
                + garantia;

        detaliiTv.setText(textDetalii);

        return v;
    }
}