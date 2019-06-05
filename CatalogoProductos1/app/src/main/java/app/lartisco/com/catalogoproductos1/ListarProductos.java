package app.lartisco.com.catalogoproductos1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.lartisco.com.catalogoproductos1.data.dao.CompraDAO;
import app.lartisco.com.catalogoproductos1.data.dao.DAO;
import app.lartisco.com.catalogoproductos1.data.dao.ProductoDAO;
import app.lartisco.com.catalogoproductos1.data.dao.VentaDAO;
import app.lartisco.com.catalogoproductos1.data.modelo.Producto;
import app.lartisco.com.catalogoproductos1.generico.ActivityTemplate;
import app.lartisco.com.catalogoproductos1.generico.Controller;

public class ListarProductos extends ActivityTemplate {
    public static final String EXTRA_LAWYER_ID = "extra_lawyer_id";
    public static String busquedaCodigoValue="";
    public static String busquedaDescripcionValue="";
    public static String busquedaReferenciaValue="";
    EditText inputCodigo,inputDescipcion,inputReferencia;

    ProductosFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listar_productos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fav= (FloatingActionButton) findViewById(R.id.fab);
    fav.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            goToActivity(Importar.class);
            fragment.loadLawyers();

        }
    });

        Button buttonBuscar=(Button )findViewById(R.id.buttonBuscar);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busquedaCodigoValue=inputCodigo.getText().toString();
                busquedaDescripcionValue=inputDescipcion.getText().toString();
                busquedaReferenciaValue=inputReferencia.getText().toString();
                fragment.loadLawyers();
            }
        });


        inputCodigo= (EditText) findViewById(R.id.buscarCodigo);
        inputDescipcion= (EditText) findViewById(R.id.buscarDescripcion);
        inputReferencia= (EditText) findViewById(R.id.buscarReferencia);

         fragment = (ProductosFragment )
                getSupportFragmentManager().findFragmentById(R.id.content_listar_productos);

        if (fragment == null) {
            fragment = ProductosFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_listar_productos, fragment)
                    .commit();
        }





    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(fragment!=null)
            fragment.loadLawyers();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(fragment!=null)
            fragment.loadLawyers();
    }
}
