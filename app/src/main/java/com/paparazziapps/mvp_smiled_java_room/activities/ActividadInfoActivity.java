package com.paparazziapps.mvp_smiled_java_room.activities;

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

import com.paparazziapps.mvp_smiled_java_room.databinding.ActivityActividadInfoBinding;
import com.paparazziapps.mvp_smiled_java_room.ViewModels.ActividadInfoViewModel;
import com.paparazziapps.mvp_smiled_java_room.adapters.ComentariosAdapter;
import com.paparazziapps.mvp_smiled_java_room.databinding.CardviewAddActivityBinding;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;
import com.paparazziapps.mvp_smiled_java_room.models.Comentario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ActividadInfoActivity extends AppCompatActivity {

    ActivityActividadInfoBinding binding;
    ActividadInfoViewModel viewModel;

    Comentario mComentario;

    LinearLayoutManager mLinearLayoutManager;
    ComentariosAdapter mAdapter;
    List<Comentario> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActividadInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViewModel();

        //all code here
        showToolbar();

        openDetailsActividad();

        createComment();


        binding.mytoolbar.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show dialog
                editarActividad();
            }
        });


    }

    private void editarActividad() {

            CardviewAddActivityBinding binding2;
            binding2 = CardviewAddActivityBinding.inflate(getLayoutInflater());

            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(binding2.getRoot());

            binding2.name.setText("Editar Actividad");
            binding2.titulo.setText(binding.mytoolbar.title.getText().toString());
            binding2.contenido.setText(binding.descripcion.getText().toString());
            binding2.fechaFin.setText(binding.fechaFin.getText().toString());

            binding2.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            binding2.okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Actividad mActividad = new Actividad();
                    mActividad.setContenido(binding2.contenido.getText().toString());
                    mActividad.setCodigo(getIntent().getIntExtra("codigoActividad",1000));
                    mActividad.setTitulo(binding2.titulo.getText().toString());
                    mActividad.setFecha_fin(binding2.fechaFin.getText().toString());

                    updateActividad(mActividad, dialog);
                }
            });

            dialog.show();
    }

    private void updateActividad(Actividad mActividad, Dialog dialog) {

        viewModel.updateTituloContenidoFecha(mActividad);
        dialog.dismiss();
    }


    private void createComment() {

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            String message = binding.editTextMessage.getText().toString();

            if(message != null)
            {
                if(!message.isEmpty())
                {
                    mComentario = new Comentario();
                    int codReceiver= getIntent().getIntExtra("codigoActividad",1000);
                    mComentario.setCodigo_actividad(codReceiver);
                    mComentario.setMensaje(message);
                    mComentario.setUnixtime(System.currentTimeMillis() / 1000);

                    //mComentarioDAO.crearComentario(mComentario);
                    viewModel.insertComentario(mComentario,codReceiver);


                            Toast.makeText(getApplicationContext(), "Actividad Creada!", Toast.LENGTH_SHORT).show();
                            binding.editTextMessage.setText("");


                }else
                {
                    Log.e("TAG","Mensaje Vacio");
                }

            }else
            {
                Log.e("TAG","Mensaje Nulo");
            }


            }
        });




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
           int codigoActividad= getIntent().getIntExtra("codigoActividad",0000);


           binding.mytoolbar.title.setText(titulo);
           binding.descripcion.setText(descripcion);
           binding.fechaInicio.setText(fecha_inicio);
           binding.fechaFin.setText(fecha_fin);

           getComentariosActividad(codigoActividad);

        }
    }

    private void getComentariosActividad(int codigoActividad) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                //lista = mComentarioDAO.getAllComentsByActividad(codigoActividad);
                lista = viewModel.returnAllComentariosList(codigoActividad);

                if(lista.size() >= 0)
                {
                    mLinearLayoutManager = new LinearLayoutManager(ActividadInfoActivity.this);
                    mAdapter= new ComentariosAdapter(getApplicationContext());
                    mAdapter.setComentariosList(lista);
                    binding.recyclerview.setLayoutManager(mLinearLayoutManager);
                    binding.recyclerview.setAdapter(mAdapter);

                    //set data from DB
                    binding.cantidadComentarios.setText("("+lista.size()+")");


                }else
                {
                    Log.e("TAG","Comentarios vacias");
                }

            }
        }).start();

    }

    private void showToolbar() {

        binding.mytoolbar.imageVisibility.setVisibility(View.GONE);
        binding.mytoolbar.linearEditDelete.setVisibility(View.VISIBLE);
        setSupportActionBar(binding.mytoolbar.getRoot());
    }

    private void initViewModel()
    {
        viewModel = new ViewModelProvider(this).get(ActividadInfoViewModel.class);
        viewModel.getComentarioListObserver().observe(this, new Observer<List<Comentario>>() {
            @Override
            public void onChanged(List<Comentario> comentarios) {
                if(comentarios == null)
                {
                    Log.e("TAG","NO COMENTARIOS");
                }else
                {
                    //Update recycler each time
                    Log.e("TAG","UPDATE SQL");
                    mAdapter.setComentariosList(comentarios);
                    //show in the recyclerview
                }
            }
        });


    }

}