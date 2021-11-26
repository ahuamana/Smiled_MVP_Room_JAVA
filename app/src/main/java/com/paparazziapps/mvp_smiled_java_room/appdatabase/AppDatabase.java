package com.paparazziapps.mvp_smiled_java_room.appdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.paparazziapps.mvp_smiled_java_room.interfaces.ActividadDAO;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;


@Database(
        entities = {Actividad.class},
        version = 1
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

}
