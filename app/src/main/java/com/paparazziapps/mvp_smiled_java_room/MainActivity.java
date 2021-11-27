package com.paparazziapps.mvp_smiled_java_room;

import androidx.appcompat.app.AppCompatActivity;
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
import com.paparazziapps.mvp_smiled_java_room.interfaces.ActividadDAO;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Actividad mActividad;
    AppDatabase mAppDatabase;
    ActividadDAO mActividadDAO;

    LinearLayoutManager mLinearLayoutManager,mLinearLayoutManager2;
    ActividadesAdapter mAdapter;

    List<Actividad> lista, lista2;

    boolean on;

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

        listaOnActivities();
        listaCompletedActivities();

        setOptionsActivities();
    }



    private void setOptionsActivities() {

        on = true;

        binding.mytoolbar.imageVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(on == true)
                {
                    on = !on;
                    binding.mytoolbar.imageVisibility.setImageResource(R.drawable.ic_visibility_off);
                    binding.recyclerview.setVisibility(View.GONE);
                    binding.recyclerviewCompleted.setVisibility(View.VISIBLE);
                    //Implement recycler with

                }else
                {
                    on = !on;
                    binding.mytoolbar.imageVisibility.setImageResource(R.drawable.ic_visibility);
                    binding.recyclerview.setVisibility(View.VISIBLE);
                    binding.recyclerviewCompleted.setVisibility(View.GONE);
                    //


                }


            }
        });

    }

    private void listaCompletedActivities() {

        mAppDatabase = AppDatabase.getUserDatabase(getApplicationContext()); // instancia a la dabase de datos
        mActividadDAO = mAppDatabase.actividadDAO(); // crea la lista de consultar a utilizar

        new Thread(new Runnable() {
            @Override
            public void run() {


                lista2 = mActividadDAO.getCompletedActividades();

                if(lista2.size() >= 0)
                {
                    mLinearLayoutManager2 = new LinearLayoutManager(MainActivity.this);
                    binding.recyclerviewCompleted.setLayoutManager(mLinearLayoutManager2);
                    mLinearLayoutManager2.setStackFromEnd(true);
                    mAdapter= new ActividadesAdapter(lista2,getApplicationContext());

                    binding.recyclerviewCompleted.setAdapter(mAdapter);

                }else
                {
                    Log.e("TAG","Actividades vacias");
                }



            }
        }).start();

    }

    private void listaOnActivities() {

        mAppDatabase = AppDatabase.getUserDatabase(getApplicationContext()); // instancia a la dabase de datos
        mActividadDAO = mAppDatabase.actividadDAO(); // crea la lista de consultar a utilizar

            new Thread(new Runnable() {
                @Override
                public void run() {


                    lista = mActividadDAO.getallActividades();

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

                            //implement observer to update -- Ojo
                            lista.add(actividad);
                            mAdapter= new ActividadesAdapter(lista, getApplicationContext());
                            binding.recyclerview.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    });


                    //end runnable
                }
            }).start();
        }

    }


}