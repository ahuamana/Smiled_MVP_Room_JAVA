package com.paparazziapps.mvp_smiled_java_room.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comentarios")
public class Comentario {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="codigo")
    public int codigo;

    @ColumnInfo(name="codigo_actividad")
    public int codigo_actividad;

    @ColumnInfo(name="mensaje")
    public String mensaje;

    @ColumnInfo(name="unixtime")
    public String unixtime;

}
