package com.paparazziapps.mvp_smiled_java_room.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paparazziapps.mvp_smiled_java_room.MainActivity;
import com.paparazziapps.mvp_smiled_java_room.ViewModels.MainActivityViewModel;
import com.paparazziapps.mvp_smiled_java_room.databinding.CardviewActividadBinding;
import com.paparazziapps.mvp_smiled_java_room.activities.ActividadInfoActivity;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;

import java.util.List;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.ViewHolder> {

    Context context;
    List<Actividad> mListActividades;
    MainActivityViewModel viewModel;

    public ActividadesAdapter(Context context)
    {
        this.context = context;
    }

    public void setActividadesList(List<Actividad> mListActividades)
    {
        this.mListActividades= mListActividades;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ActividadesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardviewActividadBinding view = CardviewActividadBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadesAdapter.ViewHolder holder, int position) {


        holder.binding.activityNumber.setText("0"+ (mListActividades.size() - position));
        holder.binding.titulo.setText( mListActividades.get(position).titulo+ " - "+ mListActividades.get(position).numero );
        holder.binding.descripcion.setText(String.valueOf(mListActividades.get(position).contenido));

        holder.binding.fechaInicio.setText(String.valueOf(mListActividades.get(position).fecha_inicio));
        holder.binding.fechaFin.setText(String.valueOf(mListActividades.get(position).fecha_fin));

        if(mListActividades.get(position).isCompleted())
        {
            Log.e("TAG COMPLETED","TAREA: "+ mListActividades.get(position).titulo);
            Log.e("TAG COMPLETED","CHECKED: "+ mListActividades.get(position).isCompleted());

            holder.binding.checkBox.setChecked(true);

        }else
        {
            if(mListActividades.get(position).isCompleted() == false)
            {
                Log.e("TAG UNCOMPLETED","TAREA: "+ mListActividades.get(position).titulo);
                Log.e("TAG UNCOMPLETED","CHECKED: "+ mListActividades.get(position).isCompleted());
                holder.binding.checkBox.setChecked(false);

            }
        }

        openDetailsActivity(holder, position);

        updateStatusActivities(holder, position);

    }

    private void updateStatusActivities(ViewHolder holder, int position) {

        Log.e("LOG","RUNNING - SetonChangeListener");

        holder.binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Log.e("TAG","Checked: "+ holder.binding.checkBox.isChecked());
                //end checked
                ((MainActivity)context).updateChecked(mListActividades.get(position).codigo, holder.binding.checkBox.isChecked(), mListActividades.get(position).isCompleted());

            }
        });

    }

    private void openDetailsActivity(ViewHolder holder, int position) {

        holder.binding.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, ActividadInfoActivity.class);
                i.putExtra("titulo",mListActividades.get(position).titulo);
                i.putExtra("descripcion",mListActividades.get(position).contenido);
                i.putExtra("fecha_inicio",mListActividades.get(position).fecha_inicio);
                i.putExtra("fecha_fin",mListActividades.get(position).fecha_fin);
                i.putExtra("codigoActividad",mListActividades.get(position).codigo);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
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
