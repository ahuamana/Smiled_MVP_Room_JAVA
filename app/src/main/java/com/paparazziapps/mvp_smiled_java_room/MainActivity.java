package com.paparazziapps.mvp_smiled_java_room;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.paparazziapps.mvp_smiled_java_room.appdatabase.AppDatabase;
import com.paparazziapps.mvp_smiled_java_room.databinding.ActivityMainBinding;
import com.paparazziapps.mvp_smiled_java_room.databinding.CardviewAddActivityBinding;
import com.paparazziapps.mvp_smiled_java_room.interfaces.ActividadDAO;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Actividad mActividad;
    AppDatabase mAppDatabase;
    ActividadDAO mActividadDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    private void showDialog() {

        CardviewAddActivityBinding binding2;
        binding2 = CardviewAddActivityBinding.inflate(getLayoutInflater());
        
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(binding2.getRoot());
        
        binding2.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        
        binding2.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActividad = new Actividad();
                mActividad.setContenido(binding2.contenido.getText().toString());
                mActividad.setTitulo(binding2.titulo.getText().toString());
                mActividad.setFecha_fin(binding2.fechaFin.getText().toString());

                final int min = 1;
                final int max = 20;
                final int random = new Random().nextInt((max - min) + 1) + min;

                String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                mActividad.setCompleted(false);
                mActividad.setNumero(random);
                mActividad.setFecha_inicio(currentDate);



                crearActividad(mActividad, dialog);
            }
        });

        dialog.show();

        
        
    }

    private void crearActividad(Actividad actividad, Dialog dialog) {

        mAppDatabase = AppDatabase.getUserDatabase(getApplicationContext()); // instancia a la dabase de datos
        mActividadDAO = mAppDatabase.actividadDAO();

        if(actividad != null)
        {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mActividadDAO.createActividad(actividad);

                    //start UIThead to show toast
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(MainActivity.this, "Actividad Creada!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });


                    //end runnable
                }
            }).start();
        }

    }
}