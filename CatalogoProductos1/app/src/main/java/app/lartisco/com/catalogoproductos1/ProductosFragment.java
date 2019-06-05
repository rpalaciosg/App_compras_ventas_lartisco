package app.lartisco.com.catalogoproductos1;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import app.lartisco.com.catalogoproductos1.data.dao.ProductoDAO;



/**
 * Vista para la lista de abogados del gabinete
 */
public class ProductosFragment extends Fragment {
    public static final int REQUEST_UPDATE_DELETE_LAWYER = 2;

    private ProductoDAO dbHelper;

    private ListView mLawyersList;
    private ProductosCursorAdapter mLawyersAdapter;
    private FloatingActionButton mAddButton;


    public ProductosFragment() {
        // Required empty public constructor
    }

    public static ProductosFragment newInstance() {
        return new ProductosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_producto, container, false);

        // Referencias UI
        mLawyersList = (ListView) root.findViewById(R.id.lawyers_list);
        mLawyersAdapter = new ProductosCursorAdapter(getActivity(), null);
       // mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        mLawyersList.setAdapter(mLawyersAdapter);

        // Eventos
        mLawyersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Cursor currentItem = (Cursor) mLawyersAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex("_id"));

                showDetailScreen(currentLawyerId);


            }
        });


     //   getActivity().deleteDatabase(LawyersDbHelper.DATABASE_NAME);

        dbHelper = new ProductoDAO(getActivity());
        // Carga de datos
        loadLawyers();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void loadLawyers() {
        new ProductosLoadData().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {

    }

    private void showDetailScreen(String lawyerId) {
       Intent intent = new Intent(getActivity(), ProductoDetailActivity.class);
        intent.putExtra(ListarProductos.EXTRA_LAWYER_ID, lawyerId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_LAWYER);

    }

    private class ProductosLoadData extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            System.out.println("cargando productos");
            String criteria= "upper(pro_descripcion) like upper(?) and upper(pro_cod) like upper(?)and upper(pro_referencia) like upper(?)";

            String searchCodigo=ListarProductos.busquedaCodigoValue.isEmpty()?"%":"%"+ListarProductos.busquedaCodigoValue+"%";
            String searchDescripcion=ListarProductos.busquedaDescripcionValue.isEmpty()?"%":"%"+ListarProductos.busquedaDescripcionValue+"%";
            String searchReferencia=ListarProductos.busquedaReferenciaValue.isEmpty()?"%":"%"+ListarProductos.busquedaReferenciaValue+"%";
            String []params =new String[]{searchDescripcion,searchCodigo,searchReferencia};
            return dbHelper.listByCriteria(criteria,params);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            mLawyersAdapter.swapCursor(cursor);
            /*if (cursor != null && cursor.getCount() > 0) {
                mLawyersAdapter.swapCursor(cursor);
            } else {

            }*/
        }
    }

}
