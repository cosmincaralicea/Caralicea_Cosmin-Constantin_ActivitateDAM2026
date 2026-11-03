package com.example.seminar04_1075;

import android.os.Parcel;
import android.os.Parcelable;

public class Calculator implements Parcelable {
    private String model;
    private double pret;
    private String marca;
    private float performanta;
    private boolean esteNou;
    private String tipSistem;
    private boolean areGarantie;

    public Calculator(String model, double pret, String marca, float performanta, boolean esteNou, String tipSistem, boolean areGarantie) {
        this.model = model;
        this.pret = pret;
        this.marca = marca;
        this.performanta = performanta;
        this.esteNou = esteNou;
        this.tipSistem = tipSistem;
        this.areGarantie = areGarantie;
    }

    protected Calculator(Parcel in) {
        model = in.readString();
        pret = in.readDouble();
        marca = in.readString();
        performanta = in.readFloat();
        esteNou = in.readByte() != 0;
        tipSistem = in.readString();
        areGarantie = in.readByte() != 0;
    }

    public static final Creator<Calculator> CREATOR = new Creator<Calculator>() {
        @Override
        public Calculator createFromParcel(Parcel in) {
            return new Calculator(in);
        }

        @Override
        public Calculator[] newArray(int size) {
            return new Calculator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(model);
        dest.writeDouble(pret);
        dest.writeString(marca);
        dest.writeFloat(performanta);
        dest.writeByte((byte) (esteNou ? 1 : 0));
        dest.writeString(tipSistem);
        dest.writeByte((byte) (areGarantie ? 1 : 0));
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public double getPret() { return pret; }
    public void setPret(double pret) { this.pret = pret; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public float getPerformanta() { return performanta; }
    public void setPerformanta(float performanta) { this.performanta = performanta; }

    public boolean isEsteNou() { return esteNou; }
    public void setEsteNou(boolean esteNou) { this.esteNou = esteNou; }

    public String getTipSistem() { return tipSistem; }
    public void setTipSistem(String tipSistem) { this.tipSistem = tipSistem; }

    public boolean isAreGarantie() { return areGarantie; }
    public void setAreGarantie(boolean areGarantie) { this.areGarantie = areGarantie; }

    @Override
    public String toString() {
        return "Calculator {" +
                "Model='" + model + '\'' +
                ", Pret=" + pret + " RON" +
                ", Marca='" + marca + '\'' +
                ", Performanta=" + performanta + " stele" +
                ", Nou=" + esteNou +
                ", Tip='" + tipSistem + '\'' +
                ", Garantie extinsă=" + (areGarantie ? "Da" : "Nu") +
                '}';
    }
}