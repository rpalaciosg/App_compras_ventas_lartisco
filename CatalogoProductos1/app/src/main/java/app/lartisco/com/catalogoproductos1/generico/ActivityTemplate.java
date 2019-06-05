package app.lartisco.com.catalogoproductos1.generico;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import app.lartisco.com.catalogoproductos1.R;
import app.lartisco.com.catalogoproductos1.exception.ErrorValidacion;


public class ActivityTemplate extends AppCompatActivity
{

    protected ProgressDialog dialog;


    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        Controller.getInstance().setActividadActual(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }



    public void  goToActivity(Class activity, String ... params) throws ErrorValidacion
    {

            try {
                Intent intent = new Intent(this, activity);
                Bundle b = new Bundle();
                int i=0;
                for(String p:params) {
                    b.putString("param" + i, p); //Your id
                    i++;
                }
                intent.putExtras(b); //
                startActivity(intent);
            }catch (Exception e)
            {
                throw  new ErrorValidacion("Error al intentar navegar a actividad",e);
            }

    }


    /**
     * Metodo para ir  a otra actividad
     * @param activity   Clase de la actividad a abrir

     */
    public void goToActivity(Class activity)
    {
        try {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        }catch (Exception e)
        {
            e.printStackTrace();
            //throw  new ExceptionGUI(e,"Error al intentar navegar a actividad");
        }

    }



    /**
     * Metodo para generar un Toast
     * @param msg
     */
    public  void toast(String msg)
    {
        String html="<div style=\" \n" +
                "background-position: center; \n" +
                "display: table;\n" +
                "background:#3F51B5;\n" +
                "border: solid #ece1e1;\n" +
                "border-radius: 30px;\n" +
                "box-shadow: 0 9px 12px rgba(0,0,0,0.8);\n" +
                //"opacity: 0.85;\n" +
                "padding:20px;\n" +
                "\"\n" +
                ">\n" +
                "<center>\n" +
                "<p style=\"display: table-cell;\n" +
                "    font-size: 2em;\n" +
                "\tvertical-align: middle;\n" +
                "\twidth: 80%;\n" +
                "\tcolor:white;\n" +
                "\t\n" +
                "\t\">\n" +
                msg+
                "</p>\n" +
                "</center>\n" +
                "</div>";

        //Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

        Context contexto = getApplicationContext();
        LayoutInflater inflater;
        inflater = (LayoutInflater)contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View notif = inflater.inflate(R.layout.activity_msj,(ViewGroup) findViewById(R.id.notificacion));
        //ImageView imagen = (ImageView) notif.findViewById(R.id.imagen);
        //imagen.setImageResource(R.drawable.audifonos1);
        WebView texto = (WebView ) notif.findViewById(R.id.texto);
        texto.setBackgroundColor(Color.TRANSPARENT);
        texto.setVerticalScrollBarEnabled(false);
        texto.setVerticalScrollbarOverlay(false);
        texto.loadData("<br><br>"+html ,"text/html; charset=utf-8", "utf-8");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(notif);
        toast.show();
    }

    /**
     * Manejo de los logs de las actividades
     * @param e
     */
    protected void  log(Exception e)
    {
        log(e,"");

    }

    /**
     * Manejo de los logs de las actividades
     * @param msg
     */
    protected void  log(String msg)
    {
        log(null,msg);

    }

    /**
     * Manejo de los logs de las actividades
     * @param e
     * @param msg
     */
    public void log(Exception e,String msg)
    {
        if(msg==null)msg="";
        if(e!=null)msg=msg+" ["+e.getMessage()+"]";
        toast(msg);
    }

    /**
     * Accion que se debe efectuar visualmente en caso de un error
     * @param e
     */
    public  void onError(Exception e)
    {
        onError(e,null);

    }

    /**
     * Accion que se debe efectuar visualmente en caso de un error
     * @param e
     * @param msg
     */
    public  void onError(Exception e,String msg)
    {
        e.printStackTrace();
        String mensajeVisual="";
        if(msg!=null)mensajeVisual=mensajeVisual+msg;
        if(e!=null)mensajeVisual=mensajeVisual+" "+e.getCause().getMessage();
        toast(mensajeVisual);
    }

    /**
     * Accion que se debe efectuar visualmente en caso de un error
     * @param msg
     */
    public  void onError(String msg)
    {
        onError(null,msg);

    }


    public String getRotation(){
        final int rotation = ((WindowManager) this.getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return "vertical";//"portrait";
            case Surface.ROTATION_90:
                return "horizontal";//"landscape";
            case Surface.ROTATION_180:
                return "reverse vertical";
            default:
                return "reverse horizontal";
        }
    }

    public  void  startProgressDialog(String title,String detail)
    {
        dialog=ProgressDialog.show(this, title, detail, true);
    }
    public  void  stopProgressDialog()
    {
        if(dialog!=null)
            dialog.dismiss();
    }

}
