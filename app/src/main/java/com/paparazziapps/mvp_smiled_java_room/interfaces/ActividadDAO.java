package com.paparazziapps.mvp_smiled_java_room.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;

import com.paparazziapps.mvp_smiled_java_room.models.Actividad;


@Dao
public interface ActividadDAO {

    @Insert
    void createActividad(Actividad actividad);
}
