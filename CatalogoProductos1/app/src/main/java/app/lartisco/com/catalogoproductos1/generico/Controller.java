package app.lartisco.com.catalogoproductos1.generico;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.lartisco.com.catalogoproductos1.ListarProductos;
import app.lartisco.com.catalogoproductos1.data.dao.CompraDAO;
import app.lartisco.com.catalogoproductos1.data.dao.DAO;
import app.lartisco.com.catalogoproductos1.data.dao.ProductoDAO;
import app.lartisco.com.catalogoproductos1.data.dao.VentaDAO;
import app.lartisco.com.catalogoproductos1.exception.ErrorValidacion;


/**
 * Created by user on 07/12/16.
 */

public class Controller {
    private static Controller instance;

    private CallBackProcessGUI callBack;
private ActivityTemplate actividadActual;



    private Controller() {

    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    public CallBackProcessGUI getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBackProcessGUI callBack) {
        this.callBack = callBack;
    }


    public ActivityTemplate getActividadActual() {
        return actividadActual;
    }

    public void setActividadActual(ActivityTemplate actividadActual) {
        this.actividadActual = actividadActual;
    }




    public  void cargarBase()
    {

        this.callBack=new CallBackProcessGUI(actividadActual,"carga de base de datos") {
            @Override
            public void onError(String observacion) {
                actividadActual.toast("Error al importar"+"<br>"+observacion);
                actividadActual.stopProgressDialog();
            }

            @Override
            public void onSucess() {
                actividadActual.toast("Importacion Exitosa!!");
                actividadActual.stopProgressDialog();
            }

            @Override
            public void onStart() {
                super.onStart();
                actividadActual.startProgressDialog("Importando!!","Por favor espere...");
                actividadActual.toast("Iniciando Importacion!!");
            }
        };


        callBack.onStart();
        TaskImportarBase tarea=new TaskImportarBase();
        tarea.execute();



    }
}





