package app.lartisco.com.catalogoproductos1.data.dao;

import android.content.Context;

import java.util.Date;

import app.lartisco.com.catalogoproductos1.data.modelo.Producto;
import app.lartisco.com.catalogoproductos1.data.modelo.Ventas;

/**
 * Created by user on 04/10/17.
 */

public class VentaDAO extends  DAO<Ventas>
{
        public VentaDAO(Context context){
            super(context,Ventas.class);


        }



}
