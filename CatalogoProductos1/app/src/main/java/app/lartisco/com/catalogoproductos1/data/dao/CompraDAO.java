package app.lartisco.com.catalogoproductos1.data.dao;

import android.content.Context;


import app.lartisco.com.catalogoproductos1.data.modelo.Compras;

/**
 * Created by user on 04/10/17.
 */

public class CompraDAO extends  DAO<Compras>
{
        public CompraDAO(Context context){
            super(context,Compras.class);

        }



}
