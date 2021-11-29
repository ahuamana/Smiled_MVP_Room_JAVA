package com.paparazziapps.mvp_smiled_java_room.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.paparazziapps.mvp_smiled_java_room.models.Actividad;

import java.util.List;


@Dao
public interface ActividadDAO {

    @Insert
    void createActividad(Actividad actividad);

    @Query("Select * from actividades where completed = 0 order by codigo desc")
    List<Actividad> getallActividades();

    @Query("Select * from actividades where completed = 1 order by codigo desc")
    List<Actividad> getCompletedActividades();

    @Query("update actividades set completed= :iscompleted where codigo = :codigo")
    void updateActividad(int codigo, boolean iscompleted);

    @Query("update actividades set titulo= :tituloReceived, contenido= :contenidoReceived, fecha_fin=:fechaReceived where codigo = :codigoReceived")
    void updateTituloContenidoFecha(int codigoReceived, String tituloReceived, String contenidoReceived, String fechaReceived);
}
