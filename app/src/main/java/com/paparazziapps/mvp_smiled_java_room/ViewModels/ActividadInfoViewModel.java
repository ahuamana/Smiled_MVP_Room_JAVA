package com.paparazziapps.mvp_smiled_java_room.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.paparazziapps.mvp_smiled_java_room.appdatabase.AppDatabase;
import com.paparazziapps.mvp_smiled_java_room.models.Actividad;
import com.paparazziapps.mvp_smiled_java_room.models.Comentario;

import java.util.List;

public class ActividadInfoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Comentario>> listOfComments;
    AppDatabase appDatabase;

    public ActividadInfoViewModel(@NonNull Application application) {
        super(application);

        listOfComments = new MutableLiveData<>();
        appDatabase = AppDatabase.getInstanceDatabase(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Comentario>> getComentarioListObserver()
    {
        return listOfComments;
    }


    public void getAllComentariosList(int codeReceived)
    {
        List<Comentario> comentariosList =appDatabase.comentarioDAO().getAllComentsByActividad(codeReceived);

        if(comentariosList.size()>0)
        {
            listOfComments.postValue(comentariosList);
        }else
        {
            listOfComments.postValue(null);
        }
    }

    public List<Comentario> returnAllComentariosList(int codeReceived)
    {
        List<Comentario> comentariosList =appDatabase.comentarioDAO().getAllComentsByActividad(codeReceived);

        if(comentariosList.size()>0)
        {
            listOfComments.postValue(comentariosList);
        }else
        {
            listOfComments.postValue(null);
        }

        return comentariosList;
    }

    public void insertComentario(Comentario comentario, int idReceiver)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                appDatabase.comentarioDAO().crearComentario(comentario);
                getAllComentariosList(idReceiver);

            }
        }).start();

    }

    public void updateTituloContenidoFecha(Actividad actividad)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                appDatabase.actividadDAO().updateTituloContenidoFecha(actividad.getCodigo(), actividad.getTitulo(), actividad.getContenido(), actividad.getFecha_fin());

            }
        }).start();

    }



}
