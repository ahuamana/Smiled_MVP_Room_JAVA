package com.paparazziapps.mvp_smiled_java_room.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paparazziapps.mvp_smiled_java_room.databinding.CardviewComentarioBinding;
import com.paparazziapps.mvp_smiled_java_room.models.Comentario;

import java.util.List;

public class ComentariosAdapter  extends RecyclerView.Adapter<ComentariosAdapter.ViewHolder> {

    Context context;
    List<Comentario> mListComentarios;

    public ComentariosAdapter (Context context, List<Comentario> mListComentarios)
    {
        this.context = context;
        this.mListComentarios= mListComentarios;
    }

    @NonNull
    @Override
    public ComentariosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardviewComentarioBinding view = CardviewComentarioBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentariosAdapter.ViewHolder holder, int position) {

        //All code Here
        Log.e("TAG","Comentario"+ position);

        if(mListComentarios.get(position).mensaje != null)
        {
            holder.binding.message.setText(mListComentarios.get(position).mensaje);
            Log.e("TAG","Comentario"+ mListComentarios.get(position).mensaje);
        }





    }

    @Override
    public int getItemCount() {
        return mListComentarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardviewComentarioBinding binding;

        public ViewHolder(@NonNull CardviewComentarioBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
