package com.paparazziapps.mvp_smiled_java_room.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paparazziapps.mvp_smiled_java_room.databinding.CardviewActividadBinding;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;

import java.util.List;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.ViewHolder> {

    Context context;
    List<Actividad> mListActividades;

    public ActividadesAdapter(List<Actividad> mListActividades, Context context)
    {
        this.context = context;
        this.mListActividades= mListActividades;
    }


    @NonNull
    @Override
    public ActividadesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardviewActividadBinding view = CardviewActividadBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesAdapter.ViewHolder holder, int position) {

        Log.e("TAG","ADAPTER WORKING");
        //All code Here

        holder.binding.activityNumber.setText("0"+String.valueOf(mListActividades.get(position).codigo));
        holder.binding.titulo.setText( String.valueOf(mListActividades.get(position).titulo));
        holder.binding.descripcion.setText(String.valueOf(mListActividades.get(position).contenido));

        holder.binding.fechaInicio.setText(String.valueOf(mListActividades.get(position).fecha_inicio));
        holder.binding.fechaFin.setText(String.valueOf(mListActividades.get(position).fecha_fin));



    }

    @Override
    public int getItemCount() {
        return mListActividades.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardviewActividadBinding binding;

        public ViewHolder(@NonNull CardviewActividadBinding binding) {
            super(binding.getRoot());

            this.binding= binding;
        }
    }
}
