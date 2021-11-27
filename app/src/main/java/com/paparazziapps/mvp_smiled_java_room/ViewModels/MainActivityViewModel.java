package com.paparazziapps.mvp_smiled_java_room.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.paparazziapps.mvp_smiled_java_room.appdatabase.AppDatabase;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Actividad>> listOfActividades;
    private MutableLiveData<List<Actividad>> listOfActividadesCompleted;
    AppDatabase appDatabase;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        listOfActividades = new MutableLiveData<>();
        listOfActividadesCompleted = new MutableLiveData<>();
        appDatabase = AppDatabase.getInstanceDatabase(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Actividad>> getActividadesListObserver()
    {
        return listOfActividades;
    }

    public MutableLiveData<List<Actividad>> getActividadesListCompletedObserver()
    {
        return listOfActividadesCompleted;
    }

    public List<Actividad> getAllActivitiesNotCompleted()
    {
        List<Actividad> actividadListNotCompleted = appDatabase.actividadDAO().getallActividades();

        if(actividadListNotCompleted.size()>0)
        {
            listOfActividades.postValue(actividadListNotCompleted);
        }else
        {
            listOfActividades.postValue(null);
        }

        return actividadListNotCompleted;
    }

    public List<Actividad> getAllActivitiesCompleted()
    {
        List<Actividad> actividadListCompleted = appDatabase.actividadDAO().getCompletedActividades();

        if(actividadListCompleted.size()>0)
        {
            listOfActividadesCompleted.postValue(actividadListCompleted);
        }else
        {
            listOfActividadesCompleted.postValue(null);
        }

        return actividadListCompleted;
    }

    public void crearActividad(Actividad actividad)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

               appDatabase.actividadDAO().createActividad(actividad);
               getAllActivitiesNotCompleted();

            }
        }).start();
    }



}
