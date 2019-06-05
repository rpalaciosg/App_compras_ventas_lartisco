package app.lartisco.com.catalogoproductos1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import app.lartisco.com.catalogoproductos1.R;
import app.lartisco.com.catalogoproductos1.generico.ActivityTemplate;
import app.lartisco.com.catalogoproductos1.generico.Controller;

public class Importar extends ActivityTemplate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Button continueB=(Button)findViewById(R.id.continuImport);
        Button cancelB=(Button)findViewById(R.id.cancelImport);

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(ListarProductos.class);
            }
        });



        continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.getInstance().cargarBase();
            }
        });
    }

}
