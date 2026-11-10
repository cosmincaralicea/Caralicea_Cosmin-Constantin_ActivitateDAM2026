package com.example.seminar04_1075;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImaginiActivity extends AppCompatActivity {

    public static class ImaginiDomeniu {
        private String textAfisat;
        private Bitmap imagine;
        private String link;

        public ImaginiDomeniu(String textAfisat, Bitmap imagine, String link) {
            this.textAfisat = textAfisat;
            this.imagine = imagine;
            this.link = link;
        }

        public String getTextAfisat() {
            return textAfisat;
        }

        public Bitmap getImagine() {
            return imagine;
        }

        public String getLink() {
            return link;
        }
    }

    public class ImagineAdapter extends BaseAdapter {
        private List<ImaginiDomeniu> lista;

        public ImagineAdapter(List<ImaginiDomeniu> lista) {
            this.lista = lista;
        }

        @Override
        public int getCount() {
            return lista.size();
        }

        @Override
        public Object getItem(int position) {
            return lista.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(ImaginiActivity.this).inflate(R.layout.item_imagine, parent, false);
            }
            ImageView imagineIv = convertView.findViewById(R.id.imagineIV);
            TextView descriereTv = convertView.findViewById(R.id.tv_descriere);

            ImaginiDomeniu item = lista.get(position);
            imagineIv.setImageBitmap(item.getImagine());
            descriereTv.setText(item.getTextAfisat());

            return convertView;
        }
    }

    private List<ImaginiDomeniu> lista = new ArrayList<>();
    private ImagineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagini);

        ListView lv = findViewById(R.id.imagini);

        List<String> linkuriImagini = new ArrayList<>();
        linkuriImagini.add("https://images.unsplash.com/photo-1587831990711-23ca6441447b?w=300");
        linkuriImagini.add("https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=300");
        linkuriImagini.add("https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=300");
        linkuriImagini.add("https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=300");
        linkuriImagini.add("https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=300");

        List<String> texte = new ArrayList<>();
        texte.add("Calculator Desktop PC");
        texte.add("Laptop Gaming ASUS");
        texte.add("Apple MacBook Pro");
        texte.add("IBM Personal Computer 5150");
        texte.add("Supercalculator Cray-1");

        List<String> linkuriWeb = new ArrayList<>();
        linkuriWeb.add("https://ro.wikipedia.org/wiki/Calculator_personal");
        linkuriWeb.add("https://ro.wikipedia.org/wiki/Laptop");
        linkuriWeb.add("https://ro.wikipedia.org/wiki/MacBook");
        linkuriWeb.add("https://ro.wikipedia.org/wiki/IBM_PC");
        linkuriWeb.add("https://ro.wikipedia.org/wiki/Supercalculator");

        new Thread(() -> {
            for (int i = 0; i < linkuriImagini.size(); i++) {
                try {
                    URL url = new URL(linkuriImagini.get(i));
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("User-Agent", "Mozilla/5.0");
                    InputStream is = con.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    con.disconnect();

                    if (bitmap != null) {
                        lista.add(new ImaginiDomeniu(texte.get(i), bitmap, linkuriWeb.get(i)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            runOnUiThread(() -> {
                adapter = new ImagineAdapter(lista);
                lv.setAdapter(adapter);
            });
        }).start();

        lv.setOnItemClickListener((p, v, position, id) -> {
            Intent it = new Intent(ImaginiActivity.this, WebViewActivity.class);
            it.putExtra("link", lista.get(position).getLink());
            startActivity(it);
        });
    }
}