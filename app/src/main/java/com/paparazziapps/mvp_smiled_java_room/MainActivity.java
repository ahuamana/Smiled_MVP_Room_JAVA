package com.paparazziapps.mvp_smiled_java_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.paparazziapps.mvp_smiled_java_room.ViewModels.MainActivityViewModel;
import com.paparazziapps.mvp_smiled_java_room.adapters.ActividadesAdapter;
import com.paparazziapps.mvp_smiled_java_room.databinding.ActivityMainBinding;
import com.paparazziapps.mvp_smiled_java_room.databinding.CardviewAddActivityBinding;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainActivityViewModel viewModel;

    Actividad mActividad;

    LinearLayoutManager mLinearLayoutManager;
    ActividadesAdapter mAdapter;

    List<Actividad> lista;

    boolean on;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Constructores
        mAdapter= new ActividadesAdapter( getApplicationContext());
        mLinearLayoutManager = new LinearLayoutManager(MainActivity.this);


        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        initViewModel();

        showToolbar();

        listaOnActivities();


        setOptionsActivities();
    }


    private void setOptionsActivities() {

        on = false;

        binding.mytoolbar.imageVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(on == true)
                {
                    on = !on;
                    binding.mytoolbar.imageVisibility.setImageResource(R.drawable.ic_visibility_off);
                    //Implement recycler with
                    viewModel.getAllActivitiesNotCompleted();

                }else
                {
                    on = !on;
                    binding.mytoolbar.imageVisibility.setImageResource(R.drawable.ic_visibility);
                    viewModel.getActividadesCompleted();



                }


            }
        });

    }


    private void listaOnActivities() {

            new Thread(new Runnable() {
                @Override
                public void run() {


                    lista = viewModel.getAllActivitiesNotCompleted();

                    if(lista.size() >= 0)
                    {
                        binding.recyclerview.setLayoutManager(mLinearLayoutManager);
                        mAdapter.setActividadesList(lista);
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


        if(actividad != null)
        {
            viewModel.crearActividad(actividad);

            Toast.makeText(MainActivity.this, "Actividad Creada!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

        }

    }

    private void initViewModel() {

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getActividadesListObserver().observe(this, new Observer<List<Actividad>>() {
            @Override
            public void onChanged(List<Actividad> actividads) {

                if(actividads == null)
                {
                    Log.e("TAG","NO Actividades");
                }else
                {
                    mAdapter.setActividadesList(actividads);
                }

            }
        });



    }


}