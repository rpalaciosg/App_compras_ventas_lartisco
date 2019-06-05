package app.lartisco.com.catalogoproductos1;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import app.lartisco.com.catalogoproductos1.data.dao.CompraDAO;
import app.lartisco.com.catalogoproductos1.data.dao.ProductoDAO;
import app.lartisco.com.catalogoproductos1.data.dao.VentaDAO;
import app.lartisco.com.catalogoproductos1.data.modelo.Compras;
import app.lartisco.com.catalogoproductos1.data.modelo.Producto;
import app.lartisco.com.catalogoproductos1.data.modelo.Ventas;

/**
 * 4JCCV001056.jpg
 * Vista para el detalle del abogado
 */
public class ProductoDetailFragment extends Fragment {
    private static final String ARG_LAWYER_ID = "lawyerId";

    private String mLawyerId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView detCodigo;
    private TextView detReferencia;
    private TextView detDescripcion;
    private TextView detDescripcionIngles;
    private TextView detEmbalaje;
    private TextView detPrecioLista;
    private TextView detStock;
    private TextView detFecha;
    private TextView detCantidad;
    private TextView detValorCompra;
    TableLayout tblCompras;
    TableLayout tblVentas;

    private ProductoDAO dbHelper;


    public ProductoDetailFragment() {
        // Required empty public constructor
    }

    public static ProductoDetailFragment newInstance(String lawyerId) {
        ProductoDetailFragment fragment = new ProductoDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LAWYER_ID, lawyerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mLawyerId = getArguments().getString(ARG_LAWYER_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_producto_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) root.findViewById(R.id.iv_imagen);
        detCodigo = (TextView) root.findViewById(R.id.det_codigo);
        detReferencia = (TextView) root.findViewById(R.id.det_referencia);
        detDescripcion = (TextView) root.findViewById(R.id.det_descripcion);
        detDescripcionIngles = (TextView) root.findViewById(R.id.det_descripcion_ingles);
        detEmbalaje = (TextView) root.findViewById(R.id.det_embalaje);
        detPrecioLista = (TextView) root.findViewById(R.id.det_precio_lista);
        detStock = (TextView) root.findViewById(R.id.det_stock);
        detFecha = (TextView) root.findViewById(R.id.det_fecha);
        detCantidad = (TextView) root.findViewById(R.id.det_cantidad);
        detValorCompra = (TextView) root.findViewById(R.id.det_valor_compra);
        tblCompras = (TableLayout) root.findViewById(R.id.tablaDetalleCompras);
        tblVentas = (TableLayout) root.findViewById(R.id.tablaDetalleVentas);
        dbHelper = new ProductoDAO(getActivity());

        loadLawyer();

        return root;
    }

    private void loadLawyer() {
        new GetLawyerByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteLawyerTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ProductosFragment.REQUEST_UPDATE_DELETE_LAWYER) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showLawyer(Producto lawyer) {


        mCollapsingView.setTitle(lawyer.getPro_descripcion());



try {


    File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    File file = new File(sdcard, "imgcatalogo/" + lawyer.getPro_cod() + ".jpg");
    if (!file.exists()) {
        file = new File(sdcard, "" + lawyer.getPro_cod() + ".jpg");
    }

    if (file.exists()) {

            Uri imageUri = Uri.fromFile(file);
            Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .into(mAvatar);
        }
        else
    {

        mAvatar.getLayoutParams().height=0;
        mAvatar.getLayoutParams().width=0;

    }

}catch (Exception e){

    e.printStackTrace();
}
        detCodigo.setText(lawyer.getPro_cod());
        detReferencia.setText(lawyer.getPro_referencia()) ;
        detDescripcion.setText(lawyer.getPro_descripcion());
        detDescripcionIngles.setText(lawyer.getPro_descrip_ing());
        detEmbalaje.setText(lawyer.getPro_embalaje());
        detPrecioLista.setText("$"+lawyer.getPro_precio_lista()+"");
        detStock .setText(lawyer.getPro_stock()+"");
        detFecha .setText(lawyer.getPro_fecha());
        detCantidad .setText(lawyer.getPro_cantidad());
        detValorCompra .setText("$"+lawyer.getPro_valor_compra());

        loadCompras(lawyer);
        loadVentas(lawyer);

    }

    public  String maskValue(double value){
    if(value==0)return "$0.00";

        DecimalFormat DEC_FOR=new DecimalFormat(".##");
        DEC_FOR.setRoundingMode(RoundingMode.UP);

        String out=DEC_FOR.format(value).replace(",",".");

        try{
            if(out.split("\\.")[1].length()==1)
                out=out+"0";

        }catch (Exception e)
        {
            e.printStackTrace();

        }
        if(value<1&&value>0)
            out="0"+out;
        return out;
    }

    public void loadCompras(Producto lawyer)
    {
        CompraDAO compraDAO=new CompraDAO(this.getContext());
        String criteria="com_pro_cod = ?";
        String []params=new  String[]{lawyer.getPro_cod()};

        {
            TableRow tbrow = new TableRow(this.getContext());

            TextView t0v = new TextView(this.getContext());
            t0v.setText("Año   ");
            t0v.setTextColor(Color.BLACK);
            t0v.setGravity(Gravity.CENTER);
            t0v.setTextColor(Color.parseColor("#3F51B5"));
            t0v.setTextSize(18);
            tbrow.addView(t0v);

            TextView t1v = new TextView(this.getContext());
            t1v.setText("Enero   ");
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setTextColor(Color.parseColor("#3F51B5"));
            t1v.setTextSize(18);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this.getContext());
            t2v.setText("Febrero    " );
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.RIGHT);
            t2v.setTextColor(Color.parseColor("#3F51B5"));
            t2v.setTextSize(18);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this.getContext());
            t3v.setText("Marzo     ");
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            t3v.setTextColor(Color.parseColor("#3F51B5"));
            t3v.setTextSize(18);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this.getContext());
            t4v.setText("Abril    " );
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            t4v.setTextColor(Color.parseColor("#3F51B5"));
            t4v.setTextSize(18);
            tbrow.addView(t4v);


            TextView t5v = new TextView(this.getContext());
            t5v.setText("Mayo    " );
            t5v.setTextColor(Color.BLACK);
            t5v.setTextColor(Color.parseColor("#3F51B5"));
            t5v.setTextSize(18);
            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);

            TextView t6v = new TextView(this.getContext());
            t6v.setText("Junio    ");
            t6v.setTextColor(Color.BLACK);
            t6v.setTextColor(Color.parseColor("#3F51B5"));
            t6v.setTextSize(18);
            t6v.setGravity(Gravity.CENTER);
            tbrow.addView(t6v);

            TextView t7v = new TextView(this.getContext());
            t7v.setText("Julio   ");
            t7v.setTextColor(Color.BLACK);
            t7v.setTextColor(Color.parseColor("#3F51B5"));
            t7v.setTextSize(18);
            t7v.setGravity(Gravity.CENTER);
            tbrow.addView(t7v);

            TextView t8v = new TextView(this.getContext());
            t8v.setText("Agosto    ");
            t8v.setTextColor(Color.BLACK);
            t8v.setTextColor(Color.parseColor("#3F51B5"));
            t8v.setTextSize(18);
            t8v.setGravity(Gravity.CENTER);
            tbrow.addView(t8v);

            TextView t9v= new TextView(this.getContext());
            t9v.setText("Septiembre   ");
            t9v.setTextColor(Color.BLACK);
            t9v.setTextColor(Color.parseColor("#3F51B5"));
            t9v.setTextSize(18);
            t9v.setGravity(Gravity.CENTER);
            tbrow.addView(t9v);

            TextView t10v= new TextView(this.getContext());
            t10v.setText("Octubre    " );
            t10v.setTextColor(Color.BLACK);
            t10v.setTextColor(Color.parseColor("#3F51B5"));
            t10v.setTextSize(18);
            t10v.setGravity(Gravity.CENTER);
            tbrow.addView(t10v);

            TextView t11v= new TextView(this.getContext());
            t11v.setText("Noviembre   " );
            t11v.setTextColor(Color.BLACK);
            t11v.setTextColor(Color.parseColor("#3F51B5"));
            t11v.setTextSize(18);
            t11v.setGravity(Gravity.CENTER);
            tbrow.addView(t11v);

            TextView t12v= new TextView(this.getContext());
            t12v.setText("Diciembre   " );
            t12v.setTextColor(Color.BLACK);
            t12v.setTextColor(Color.parseColor("#3F51B5"));
            t12v.setTextSize(18);
            t12v.setGravity(Gravity.CENTER);
            tbrow.addView(t12v);


            t2v.setTextSize(15);

            tblCompras.addView(tbrow);

        }


        Cursor c=compraDAO.listByCriteria(criteria,params,"com_anio ASC");
        while(c.moveToNext()) {
            Compras compras=compraDAO.cursorToObj2(c);
            TableRow tbrow = new TableRow(this.getContext());

            TextView t0v = new TextView(this.getContext());
            t0v.setText(""+((int)compras.getCom_anio()) +"       " );
            t0v.setTextColor(Color.BLACK);
            t0v.setGravity(Gravity.RIGHT);
            tbrow.addView(t0v);

            TextView t1v = new TextView(this.getContext());
            t1v.setText(""+maskValue(compras.getCom_enero()) +"       " );
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.RIGHT);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this.getContext());
            t2v.setText(""+ maskValue(compras.getCom_febrero())+"       " );
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.RIGHT);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this.getContext());
            t3v.setText("" +maskValue( compras.getCom_marzo())+"       " );
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.RIGHT);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this.getContext());
            t4v.setText("" + maskValue(compras.getCom_abril())+"       " );
            t4v.setGravity(Gravity.RIGHT);
            tbrow.addView(t4v);


            TextView t5v = new TextView(this.getContext());
            t5v.setText("" + maskValue(compras.getCom_mayo())+"       " );
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.RIGHT);
            tbrow.addView(t5v);

            TextView t6v = new TextView(this.getContext());
            t6v.setText("" + maskValue(compras.getCom_junio())+"       " );
            t6v.setTextColor(Color.BLACK);
            t6v.setGravity(Gravity.RIGHT);
            tbrow.addView(t6v);

            TextView t7v = new TextView(this.getContext());
            t7v.setText("" + maskValue(compras.getCom_julio())+"       " );
            t7v.setTextColor(Color.BLACK);
            t7v.setGravity(Gravity.RIGHT);
            tbrow.addView(t7v);

            TextView t8v = new TextView(this.getContext());
            t8v.setText("" + maskValue(compras.getCom_agosto())+"       " );
            t8v.setTextColor(Color.BLACK);
            t8v.setGravity(Gravity.RIGHT);
            tbrow.addView(t8v);

            TextView t9v= new TextView(this.getContext());
            t9v.setText("" + maskValue(compras.getCom_agosto())+"       " );
            t9v.setTextColor(Color.BLACK);
            t9v.setGravity(Gravity.RIGHT);
            tbrow.addView(t9v);

            TextView t10v= new TextView(this.getContext());
            t10v.setText("" + maskValue(compras.getCom_octubre())+"       " );
            t10v.setTextColor(Color.BLACK);
            t10v.setGravity(Gravity.RIGHT);
            tbrow.addView(t10v);

            TextView t11v= new TextView(this.getContext());
            t11v.setText("" + maskValue(compras.getCom_noviembre())+"       " );
            t11v.setTextColor(Color.BLACK);
            t11v.setGravity(Gravity.RIGHT);
            tbrow.addView(t11v);

            TextView t12v= new TextView(this.getContext());
            t12v.setText("" + maskValue(compras.getCom_diciembre())+"       " );
            t12v.setTextColor(Color.BLACK);
            t12v.setGravity(Gravity.RIGHT);
            tbrow.addView(t12v);

            tblCompras.addView(tbrow);
        }


    }


    public void loadVentas(Producto lawyer)
    {
        VentaDAO compraDAO=new VentaDAO(this.getContext());
        String criteria="ven_pro_cod = ?";
        String []params=new  String[]{lawyer.getPro_cod()};

        {
            TableRow tbrow = new TableRow(this.getContext());


            TextView t0v = new TextView(this.getContext());
            t0v.setText("Año   ");
            t0v.setGravity(Gravity.CENTER);
            t0v.setTextColor(Color.parseColor("#3F51B5"));
            t0v.setTextSize(18);
            tbrow.addView(t0v);

            TextView t1v = new TextView(this.getContext());
            t1v.setText("Enero   ");
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setTextColor(Color.parseColor("#3F51B5"));
            t1v.setTextSize(18);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this.getContext());
            t2v.setText("Febrero   " );
            t2v.setGravity(Gravity.CENTER);
            t2v.setTextColor(Color.parseColor("#3F51B5"));
            t2v.setTextSize(18);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this.getContext());
            t3v.setText("Marzo   ");
            t3v.setTextColor(Color.parseColor("#3F51B5"));
            t3v.setTextSize(18);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this.getContext());
            t4v.setText("Abril    " );
            t4v.setTextColor(Color.parseColor("#3F51B5"));
            t4v.setTextSize(18);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);


            TextView t5v = new TextView(this.getContext());
            t5v.setText("Mayo   " );
            t5v.setTextColor(Color.parseColor("#3F51B5"));
            t5v.setTextSize(18);

            t5v.setGravity(Gravity.CENTER);
            tbrow.addView(t5v);

            TextView t6v = new TextView(this.getContext());
            t6v.setText("Junio   ");
            t6v.setTextColor(Color.BLACK);
            t6v.setGravity(Gravity.CENTER);
            t6v.setTextColor(Color.parseColor("#3F51B5"));
            t6v.setTextSize(18);
            tbrow.addView(t6v);

            TextView t7v = new TextView(this.getContext());
            t7v.setText("Julio   ");
            t7v.setTextColor(Color.BLACK);
            t7v.setGravity(Gravity.CENTER);
            t7v.setTextColor(Color.parseColor("#3F51B5"));
            t7v.setTextSize(18);
            tbrow.addView(t7v);

            TextView t8v = new TextView(this.getContext());
            t8v.setText("Agosto   ");
            t8v.setTextColor(Color.BLACK);
            t8v.setGravity(Gravity.CENTER);
            t8v.setTextColor(Color.parseColor("#3F51B5"));
            t8v.setTextSize(18);
            tbrow.addView(t8v);

            TextView t9v= new TextView(this.getContext());
            t9v.setText("Septiembre   ");
            t9v.setTextColor(Color.BLACK);
            t9v.setGravity(Gravity.CENTER);
            t9v.setTextColor(Color.parseColor("#3F51B5"));
            t9v.setTextSize(18);
            tbrow.addView(t9v);

            TextView t10v= new TextView(this.getContext());
            t10v.setText("Octubre   " );
            t10v.setTextColor(Color.BLACK);
            t10v.setGravity(Gravity.CENTER);
            t10v.setTextColor(Color.parseColor("#3F51B5"));
            t10v.setTextSize(18);
            tbrow.addView(t10v);

            TextView t11v= new TextView(this.getContext());
            t11v.setText("Noviembre   " );
            t11v.setTextColor(Color.BLACK);
            t11v.setGravity(Gravity.CENTER);
            t11v.setTextColor(Color.parseColor("#3F51B5"));
            t11v.setTextSize(18);
            tbrow.addView(t11v);

            TextView t12v= new TextView(this.getContext());
            t12v.setText("Diciembre   " );
            t12v.setTextColor(Color.BLACK);
            t12v.setGravity(Gravity.CENTER);
            t12v.setTextColor(Color.parseColor("#3F51B5"));
            t12v.setTextSize(18);
            tbrow.addView(t12v);

            tblVentas.addView(tbrow);

        }


        Cursor c=compraDAO.listByCriteria(criteria,params,"ven_anio ASC");
        while(c.moveToNext()) {
            Ventas compras=compraDAO.cursorToObj2(c);
            TableRow tbrow = new TableRow(this.getContext());

            TextView t0v = new TextView(this.getContext());
            t0v.setText(""+((int)compras.getven_anio())+"       " );
            t0v.setTextColor(Color.BLACK);
            t0v.setGravity(Gravity.RIGHT);
            tbrow.addView(t0v);

            TextView t1v = new TextView(this.getContext());
            t1v.setText(""+maskValue(compras.getven_enero()) +"       ");
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.RIGHT);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this.getContext());
            t2v.setText(""+ maskValue(compras.getven_febrero())+"       ");
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.RIGHT);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this.getContext());
            t3v.setText("" + maskValue(compras.getven_marzo())+"       ");
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.RIGHT);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this.getContext());
            t4v.setText("" + maskValue(compras.getven_abril())+"       ");
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.RIGHT);
            tbrow.addView(t4v);


            TextView t5v = new TextView(this.getContext());
            t5v.setText("" +maskValue( compras.getven_mayo())+"       ");
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.RIGHT);
            tbrow.addView(t5v);

            TextView t6v = new TextView(this.getContext());
            t6v.setText("" + maskValue(compras.getven_junio())+"       ");
            t6v.setTextColor(Color.BLACK);
            t6v.setGravity(Gravity.RIGHT);
            tbrow.addView(t6v);

            TextView t7v = new TextView(this.getContext());
            t7v.setText("" + maskValue(compras.getven_julio())+"       ");
            t7v.setTextColor(Color.BLACK);
            t7v.setGravity(Gravity.RIGHT);
            tbrow.addView(t7v);

            TextView t8v = new TextView(this.getContext());
            t8v.setText("" + maskValue(compras.getven_agosto())+"       ");
            t8v.setTextColor(Color.BLACK);
            t8v.setGravity(Gravity.RIGHT);
            tbrow.addView(t8v);

            TextView t9v= new TextView(this.getContext());
            t9v.setText("" + maskValue(compras.getven_agosto())+"       ");
            t9v.setTextColor(Color.BLACK);
            t9v.setGravity(Gravity.RIGHT);
            tbrow.addView(t9v);

            TextView t10v= new TextView(this.getContext());
            t10v.setText("" +maskValue( compras.getven_octubre())+"       ");
            t10v.setTextColor(Color.BLACK);
            t10v.setGravity(Gravity.RIGHT);
            tbrow.addView(t10v);

            TextView t11v= new TextView(this.getContext());
            t11v.setText("" + maskValue(compras.getven_noviembre())+"       ");
            t11v.setTextColor(Color.BLACK);
            t11v.setGravity(Gravity.RIGHT);
            tbrow.addView(t11v);

            TextView t12v= new TextView(this.getContext());
            t12v.setText("" +maskValue( compras.getven_diciembre()));
            t12v.setTextColor(Color.BLACK);
            t12v.setGravity(Gravity.RIGHT);
            tbrow.addView(t12v);

            tblVentas.addView(tbrow);
        }


    }


    private void showEditScreen() {
        /*Intent intent = new Intent(getActivity(), AddEditLawyerActivity.class);
        intent.putExtra(LawyersActivity.EXTRA_LAWYER_ID, mLawyerId);
        startActivityForResult(intent, LawyersFragment.REQUEST_UPDATE_DELETE_LAWYER);
        */
    }

    private void showLawyersScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar abogado", Toast.LENGTH_SHORT).show();
    }

    private class GetLawyerByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return dbHelper.read(mLawyerId);

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(dbHelper.cursorToObj(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteLawyerTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {

            System.out.println("deleting"+mLawyerId);

            return dbHelper.delete(mLawyerId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showLawyersScreen(integer > 0);
        }

    }

}
