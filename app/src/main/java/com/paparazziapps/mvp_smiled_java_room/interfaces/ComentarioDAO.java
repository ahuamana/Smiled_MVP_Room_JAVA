package com.paparazziapps.mvp_smiled_java_room.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.paparazziapps.mvp_smiled_java_room.models.Comentario;

import java.util.List;

@Dao
public interface ComentarioDAO {

    @Insert
    void crearComentario(Comentario comentario);

    @Query("Select * from comentarios where codigo_actividad = (:codigoReceived) order by unixtime desc ")
    List<Comentario> getAllComentsByUser(String codigoReceived);

}
