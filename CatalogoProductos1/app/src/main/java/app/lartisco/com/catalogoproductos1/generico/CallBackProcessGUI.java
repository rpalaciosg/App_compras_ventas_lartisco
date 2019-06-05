package app.lartisco.com.catalogoproductos1.generico;

/**
 * Created by user on 07/12/16.
 */


import app.lartisco.com.catalogoproductos1.exception.ErrorValidacion;

/**
 * Listenner de procesos Asincronos
 */
public abstract  class CallBackProcessGUI
{

        private ActivityTemplate activity;
        private String descripcionProceso;
        public CallBackProcessGUI(ActivityTemplate activity)
        {
            this.activity=activity;
        }
        public CallBackProcessGUI(ActivityTemplate activity, String descripcionProceso)
        {
            this.activity=activity;
            this.descripcionProceso=descripcionProceso;
        }

        public void onError(ErrorValidacion error, String observacion)
        {

                activity.toast(error.getMessage());

        }
        public void onStart()
        {
            System.out.println("Iniciando proceso:"+descripcionProceso);
        }
        public void onError(String msg)
        {

        }
        public void onSucess(String mensaje,Object... valor1)
        {

        }

        public void onSucess()
        {
                System.out.println("Sucess proceso:"+descripcionProceso);
        }

}
