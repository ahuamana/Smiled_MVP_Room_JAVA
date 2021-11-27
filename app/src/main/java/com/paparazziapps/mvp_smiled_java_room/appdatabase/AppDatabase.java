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
    private static AppDatabase schoolDatabase;

    public static synchronized AppDatabase getUserDatabase(Context context)
    {
        if(schoolDatabase == null)
        {
            schoolDatabase = Room.databaseBuilder(context,AppDatabase.class, dbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return schoolDatabase;
    }

    public abstract ActividadDAO actividadDAO();

    public abstract ComentarioDAO comentarioDAO();


}
