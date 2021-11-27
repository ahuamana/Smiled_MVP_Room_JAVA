package com.paparazziapps.mvp_smiled_java_room.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.paparazziapps.mvp_smiled_java_room.databinding.ActivityActividadInfoBinding;

public class ActividadInfoActivity extends AppCompatActivity {

    ActivityActividadInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActividadInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //all code here
        showToolbar();

        openDetailsActividad();

    }

    private void openDetailsActividad() {

        if(getIntent().getExtras() == null)
        {
            Log.e("TAG","No extras");
        }else
        {
           String titulo= getIntent().getStringExtra("titulo");
           String descripcion= getIntent().getStringExtra("descripcion");
           String fecha_inicio= getIntent().getStringExtra("fecha_inicio");
           String fecha_fin= getIntent().getStringExtra("fecha_fin");
           //String codigoActividad= getIntent().getStringExtra("codigoActividad");

           binding.mytoolbar.title.setText(titulo);
           binding.descripcion.setText(descripcion);
           binding.fechaInicio.setText(fecha_inicio);
           binding.fechaFin.setText(fecha_fin);

        }
    }

    private void showToolbar() {

        binding.mytoolbar.imageVisibility.setVisibility(View.GONE);
        binding.mytoolbar.linearEditDelete.setVisibility(View.VISIBLE);

        setSupportActionBar(binding.mytoolbar.getRoot());
    }
}