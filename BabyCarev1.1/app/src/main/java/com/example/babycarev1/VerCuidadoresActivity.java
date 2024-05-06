package com.example.babycarev1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class VerCuidadoresActivity extends AppCompatActivity {

    protected ListView lista1;
    private ArrayList<String> cuidadores = new ArrayList<String>();
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> cuidador;
    protected DataBaseSQL db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_cuidadores);

        //BASE DE DATOS Y REFERENCIAS
        db = new DataBaseSQL(this);
        lista1 = (ListView) findViewById(R.id.lista1_verCuidadores);

        //MOSTRAR NOTAS EN LISTA
        cuidadores = db.consultarCuidadores();
        adaptador = new ArrayAdapter<String>(VerCuidadoresActivity.this, android.R.layout.simple_list_item_1, cuidadores);
        lista1.setAdapter(adaptador);

    }
}