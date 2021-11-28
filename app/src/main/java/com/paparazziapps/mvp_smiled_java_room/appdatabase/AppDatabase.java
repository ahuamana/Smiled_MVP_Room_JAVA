package com.paparazziapps.mvp_smiled_java_room.appdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.paparazziapps.mvp_smiled_java_room.interfaces.ActividadDAO;
import com.paparazziapps.mvp_smiled_java_room.interfaces.ComentarioDAO;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;
import com.paparazziapps.mvp_smiled_java_room.models.Comentario;


@Database(
        entities = {Actividad.class, Comentario.class},
        version = 2
)
public abstract class AppDatabase extends RoomDatabase {

    private static final String dbName = "schoolDB";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstanceDatabase(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context,AppDatabase.class, dbName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract ActividadDAO actividadDAO();

    public abstract ComentarioDAO comentarioDAO();


}
