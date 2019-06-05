package app.lartisco.com.catalogoproductos1.data.dao;

import android.content.Context;

import java.util.Date;

import app.lartisco.com.catalogoproductos1.data.modelo.Producto;

/**
 * Created by user on 04/10/17.
 */

public class ProductoDAO extends  DAO<Producto>
{
        public ProductoDAO (Context context){


            super(context,Producto.class);



           // insert(p1);
        }



}
