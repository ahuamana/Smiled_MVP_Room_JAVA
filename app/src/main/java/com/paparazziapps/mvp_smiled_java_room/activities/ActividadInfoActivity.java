package com.paparazziapps.mvp_smiled_java_room.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.paparazziapps.mvp_smiled_java_room.MainActivity;
import com.paparazziapps.mvp_smiled_java_room.adapters.ActividadesAdapter;
import com.paparazziapps.mvp_smiled_java_room.adapters.ComentariosAdapter;
import com.paparazziapps.mvp_smiled_java_room.appdatabase.AppDatabase;
import com.paparazziapps.mvp_smiled_java_room.databinding.ActivityActividadInfoBinding;
import com.paparazziapps.mvp_smiled_java_room.interfaces.ComentarioDAO;
import com.paparazziapps.mvp_smiled_java_room.models.Comentario;

import java.util.List;

public class ActividadInfoActivity extends AppCompatActivity {

    ActivityActividadInfoBinding binding;

    AppDatabase mAppDatabase;
    ComentarioDAO mComentarioDAO;
    Comentario mComentario;

    LinearLayoutManager mLinearLayoutManager;
    ComentariosAdapter mAdapter;
    List<Comentario> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActividadInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Previuos Code
        mAppDatabase = AppDatabase.getUserDatabase(getApplicationContext());
        mComentarioDAO = mAppDatabase.comentarioDAO();

        //all code here
        showToolbar();

        openDetailsActividad();

        createComment();


    }

    private void createComment() {


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String message = binding.editTextMessage.getText().toString();

                        if(message != null)
                        {
                            if(!message.isEmpty())
                            {

                                mComentario = new Comentario();
                                mComentario.setCodigo_actividad(getIntent().getIntExtra("codigoActividad",1000));
                                mComentario.setMensaje(message);
                                mComentario.setUnixtime(System.currentTimeMillis() / 1000);

                                mComentarioDAO.crearComentario(mComentario);


                                //Main Threat
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Actividad Creada!", Toast.LENGTH_SHORT).show();
                                        binding.editTextMessage.setText("");

                                    }
                                });

                            }else
                            {
                                Log.e("TAG","Mensaje Vacio");
                            }

                        }else
                        {
                            Log.e("TAG","Mensaje Nulo");
                        }


                    }
                }).start();

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

                lista = mComentarioDAO.getAllComentsByActividad(codigoActividad);

                if(lista.size() >= 0)
                {
                    mLinearLayoutManager = new LinearLayoutManager(ActividadInfoActivity.this);
                    mAdapter= new ComentariosAdapter(getApplicationContext(), lista);
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
}