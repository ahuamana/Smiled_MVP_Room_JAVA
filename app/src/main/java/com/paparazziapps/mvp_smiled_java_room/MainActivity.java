package com.paparazziapps.mvp_smiled_java_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.paparazziapps.mvp_smiled_java_room.adapters.ActividadesAdapter;
import com.paparazziapps.mvp_smiled_java_room.appdatabase.AppDatabase;
import com.paparazziapps.mvp_smiled_java_room.databinding.ActivityMainBinding;
import com.paparazziapps.mvp_smiled_java_room.databinding.CardviewAddActivityBinding;
import com.paparazziapps.mvp_smiled_java_room.databinding.ToolbarBinding;
import com.paparazziapps.mvp_smiled_java_room.interfaces.ActividadDAO;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Actividad mActividad;
    AppDatabase mAppDatabase;
    ActividadDAO mActividadDAO;

    LinearLayoutManager mLinearLayoutManager;
    ActividadesAdapter mAdapter;

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

        showToolbar();

        listallActivities();

    }

    private void listallActivities() {

        mAppDatabase = AppDatabase.getUserDatabase(getApplicationContext()); // instancia a la dabase de datos
        mActividadDAO = mAppDatabase.actividadDAO(); // crea la lista de consultar a utilizar



            new Thread(new Runnable() {
                @Override
                public void run() {


                    List<Actividad> lista = mActividadDAO.getallActividades();

                    if(lista.size() >= 0)
                    {
                        mLinearLayoutManager = new LinearLayoutManager(MainActivity.this);


                        binding.recyclerview.setLayoutManager(mLinearLayoutManager);
                        mLinearLayoutManager.setStackFromEnd(true);
                        mAdapter= new ActividadesAdapter(lista,getApplicationContext());

                        binding.recyclerview.setAdapter(mAdapter);

                    }else
                    {
                        Log.e("TAG","Actividades vacias");
                    }



                }
            }).start();

    }

    private void showToolbar() {
        setSupportActionBar(binding.mytoolbar.getRoot());
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