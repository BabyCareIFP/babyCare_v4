package com.example.babycarev1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    //TODO pasarlo a VerCuidadoresActivity
    //TODO iba a poner algo mas pero no me acuerdo
    //TODO PerfilCuidadorActivity, haciendolo clickable con listview, con un atributo que desconozca o con una imagen clicable(true)
    //TODO posiciones de elementos, y meter formula de la edad?

    protected ScrollView scroll;
    protected LinearLayout layout;
    protected DataBaseSQL db;
    private ArrayList<Cuidador> listaCuidadores = new ArrayList<Cuidador>();
    private String nombre, apellidos, telefono, fechaNacimiento, sexo, experiencia, disponibilidad, puntuacion, resenias;
    private String[] datosCuidador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        db = new DataBaseSQL(this);
        scroll = findViewById(R.id.scroll1_test);
        layout = findViewById(R.id.layout1_test);

        for (String cuidadorTexto :
                db.consultarCuidadores()) {
            datosCuidador = cuidadorTexto.split(".-");

            nombre = datosCuidador[0];
            sexo = datosCuidador[1];
            experiencia = datosCuidador[2];
            disponibilidad = datosCuidador[3];
            puntuacion = datosCuidador[4];

            listaCuidadores.add(new Cuidador(nombre, apellidos, telefono, fechaNacimiento, sexo, experiencia, disponibilidad, puntuacion, resenias));
        }

        for (Cuidador cuidador :  listaCuidadores)
        {
            View previewCuidador = LayoutInflater.from(this).inflate(R.layout.item_cuidador, scroll, false);

            // Obtener referencias a los elementos de la vista
            ImageView image1 = (ImageView) previewCuidador.findViewById(R.id.image1_cuidador);
            TextView label1 = previewCuidador.findViewById(R.id.label1_cuidador);
            TextView label2 = previewCuidador.findViewById(R.id.label2_cuidador);
            TextView label3 = previewCuidador.findViewById(R.id.label3_cuidador);
            TextView label4 = previewCuidador.findViewById(R.id.label4_cuidador);

            image1.setImageResource(R.drawable.default_avatar_icon);
            label1.setText(cuidador.getNombre());
            label2.setText(cuidador.getExperiencia());
            label3.setText(cuidador.getDisponibilidad());
            label4.setText(cuidador.getPuntuacion());

            layout.addView(previewCuidador);

        }
        //scroll.addView(layout);
    }
}