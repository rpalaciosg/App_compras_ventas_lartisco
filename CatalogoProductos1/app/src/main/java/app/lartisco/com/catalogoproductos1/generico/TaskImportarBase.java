package app.lartisco.com.catalogoproductos1.generico;

/**
 * Created by user on 09/10/17.
 */


import android.os.AsyncTask;

import java.util.Date;
import java.util.Map;

import app.lartisco.com.catalogoproductos1.data.dao.CompraDAO;
import app.lartisco.com.catalogoproductos1.data.dao.DAO;
import app.lartisco.com.catalogoproductos1.data.dao.ProductoDAO;
import app.lartisco.com.catalogoproductos1.data.dao.VentaDAO;
import app.lartisco.com.catalogoproductos1.data.modelo.Compras;
import app.lartisco.com.catalogoproductos1.data.modelo.Producto;
import app.lartisco.com.catalogoproductos1.data.modelo.Ventas;

public class TaskImportarBase extends AsyncTask<String, String, Integer> {

    String error;
    @Override
    public Integer doInBackground(String... params) {
        try {

            Controller.getInstance().getActividadActual().deleteDatabase(DAO.DATABASE_NAME);


            ProductoDAO productoDAO=new ProductoDAO(Controller.getInstance().getActividadActual());
            productoDAO.loadFromCsv("Producto.csv");

            CompraDAO compraDAO=new CompraDAO(Controller.getInstance().getActividadActual());
            compraDAO.loadFromCsv("Compras.csv");

            VentaDAO ventaDAO=new VentaDAO(Controller.getInstance().getActividadActual());
            ventaDAO.loadFromCsv("Ventas.csv");

            return  1;
        }catch (Exception e)
        {
            e.printStackTrace();
            error=e.getMessage();
        }



        return 0;
    }


    public boolean test(){

        ProductoDAO productoDAO=new ProductoDAO(Controller.getInstance().getActividadActual());



        Producto p1=new Producto();
        p1.setPro_cantidad("1132");
        p1.setPro_descripcion("descripcion del producto felxometro 55");
        p1.setPro_cod("codigo del producto");
        p1.setPro_cantidad("cant"+new Date().getTime());
        p1.setPro_referencia("referencia del producto"+new Date().getTime());
        p1.setPro_referencia("fecha"+new Date().toString());
        p1.setPro_valor_compra("10");

            productoDAO.insert(p1);
        return true;
    }


    @Override
    protected void onPostExecute(Integer respuesta) {

        if(respuesta==1)
       Controller.getInstance().getCallBack().onSucess();
        else
        Controller.getInstance().getCallBack().onError(error);

    }



}
