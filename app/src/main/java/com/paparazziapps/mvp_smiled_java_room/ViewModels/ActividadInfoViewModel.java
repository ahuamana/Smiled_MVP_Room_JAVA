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
    private MutableLiveData<Integer> amountComments;

    AppDatabase appDatabase;

    public ActividadInfoViewModel(@NonNull Application application) {
        super(application);

        listOfComments = new MutableLiveData<>();
        amountComments = new MutableLiveData<>();
        appDatabase = AppDatabase.getInstanceDatabase(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Comentario>> getComentarioListObserver()
    {
        return listOfComments;
    }

    public MutableLiveData<Integer> getAmountCommentsObserver()
    {
        return amountComments;
    }

    public  void getAllAcountCommnets(int codeReceived)
    {
        List<Comentario> comentariosList =appDatabase.comentarioDAO().getAllComentsByActividad(codeReceived);

        if(comentariosList.size()>0)
        {
            amountComments.postValue(comentariosList.size());
        }else
        {
            amountComments.postValue(null);
        }
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
                getAllAcountCommnets(idReceiver);

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


    public void deleteActividad(int codigo)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                appDatabase.actividadDAO().deleteActividad(codigo);


            }
        }).start();

    }



}
