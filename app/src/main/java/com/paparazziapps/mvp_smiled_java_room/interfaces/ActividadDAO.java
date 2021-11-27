package com.paparazziapps.mvp_smiled_java_room.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
}
