package com.paparazziapps.mvp_smiled_java_room.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "actividades")
public class Actividad {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="codigo")
    public int codigo;

    @ColumnInfo(name = "numero")
    public int numero;

    @ColumnInfo(name = "fecha_inicio")
    public String fecha_inicio;

    @ColumnInfo(name = "fecha_fin")
    public String fecha_fin;

    @ColumnInfo(name = "titulo")
    public String titulo;

    @ColumnInfo(name = "contenido")
    public String contenido;

    @ColumnInfo(name = "completed")
    public boolean completed;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

