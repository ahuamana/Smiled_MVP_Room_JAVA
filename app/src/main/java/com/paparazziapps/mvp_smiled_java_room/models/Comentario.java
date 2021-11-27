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
    public long unixtime;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo_actividad() {
        return codigo_actividad;
    }

    public void setCodigo_actividad(int codigo_actividad) {
        this.codigo_actividad = codigo_actividad;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public long getUnixtime() {
        return unixtime;
    }

    public void setUnixtime(long unixtime) {
        this.unixtime = unixtime;
    }
}
