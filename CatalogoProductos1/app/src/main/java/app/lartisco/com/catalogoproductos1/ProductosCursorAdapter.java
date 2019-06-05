package app.lartisco.com.catalogoproductos1;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Adaptador de abogados
 */
public class ProductosCursorAdapter extends CursorAdapter {


    public ProductosCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_producto, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView nameText = (TextView) view.findViewById(R.id.prod_description);
        TextView codigoText = (TextView) view.findViewById(R.id.prod_codigo);
        TextView referenciaText = (TextView) view.findViewById(R.id.prod_referencia);

        String name = cursor.getString(cursor.getColumnIndex("pro_descripcion"));
        String codigo = cursor.getString(cursor.getColumnIndex("pro_cod"));
        String referencia = cursor.getString(cursor.getColumnIndex("pro_referencia"));
        codigoText.setText(codigo);
        referenciaText.setText(referencia);
        nameText.setText(name);

    }

}
