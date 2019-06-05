package app.lartisco.com.catalogoproductos1.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.csvreader.CsvReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.lartisco.com.catalogoproductos1.data.modelo.Compras;
import app.lartisco.com.catalogoproductos1.data.modelo.Producto;
import app.lartisco.com.catalogoproductos1.data.modelo.Ventas;


public class                                                    DAO<T> extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "base_catalogo2.db";
    private Context context;

    List<Class> classes;


    public DAO(Context context,Class<T>classObj) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        classes=new ArrayList<Class>();
        classes.add(Producto.class);
        classes.add(Compras.class);
        classes.add(Ventas.class);
        this.context=context;
        setClass(classObj);
    }


    private Class<T>classObj;

    public void setClass(Class<T>classObj)
    {
        this.classObj=classObj;
    }

    public long insert( T obj)
    {
        return getWritableDatabase().insert(
                classObj.getSimpleName(),
                null,
                toContentValues(obj));

    }

    /*public long inserBulk(T obj){
        //SQLiteDatabase db = this.getWritableDatabase();
        //db.beginTransaction();
        //classObj.getSimpleName();
       // return getWritableDatabase().insert(classObj.getSimpleName(),null,toContentValues(obj));

    }*/




    @Override
    public void onCreate(SQLiteDatabase db) {

        for(Class claseTabla:classes) {

            String sql = "CREATE TABLE  " + claseTabla.getSimpleName() + "( ";


            Field[] fields = claseTabla.getDeclaredFields();

            int cont = 0;
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);
                if (f.getName().equals("$change") || f.getName().equals("serialVersionUID"))
                    continue;

                boolean isEnd = false;
                if (cont == fields.length - 3)
                    isEnd = true;
                cont++;
                if (f.getName().equals("uid"))
                    sql = sql + "_id  INTEGER PRIMARY KEY AUTOINCREMENT,";
                else
                    sql = sql + f.getName() + " TEXT,";

                //if (!isEnd) sql = sql + ",";


            }

            if(sql.endsWith(","))
                sql=sql.substring(0,sql.length()-1);
            sql = sql + ")";

            System.out.println("CREATE:+++++++++++++++++++++++++:"+sql);
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    private ContentValues toContentValues(T obj) {
        ContentValues values = new ContentValues();
        Field [] fields=classObj.getDeclaredFields();
        for(int i=0;i<fields.length;i++) {
            Field f = fields[i];
            f.setAccessible(true);
            if(f.getName().equals("$change")||f.getName().equals("serialVersionUID")||f.getName().equals("uid"))
                continue;
            Object valobj=null;
            try {

                 valobj=f.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            String valStr=valobj==null?"":valobj.toString();
            values.put(f.getName(), valStr);
        }



        return values;
    }

    public T cursorToObj(Cursor cursor) {

        T obj=null;
        try {
        String []names=cursor.getColumnNames();


       obj = (T) classObj.newInstance();
        cursor.moveToLast();
            for(String cn:names)
            {
                Field field=null;
                if(cn.equals("_id")) {
                    long id = cursor.getLong(cursor.getColumnIndex("_id"));
                    field=classObj.getDeclaredField("uid");
                    field.setAccessible(true);
                    field.set(obj,id);
                }
                else {

                    String val = cursor.getString(cursor.getColumnIndex(cn));
                    try {
                        field = classObj.getDeclaredField(cn);
                        field.setAccessible(true);
                        if(field.getType().equals(Double.class)||field.getType().getSimpleName().equals("double"))
                            field.set(obj, Double.valueOf(val));
                           else

                            field.set(obj, val);
                    }catch (Exception w){w.printStackTrace();}
                }

            }


        }catch (Exception e){e.printStackTrace();}

        return obj;
    }



    public T cursorToObj2(Cursor cursor) {

        T obj=null;
        try {
            String []names=cursor.getColumnNames();


            obj = (T) classObj.newInstance();
            for(String cn:names)
            {
                Field field=null;
                if(cn.equals("_id")) {
                    long id = cursor.getLong(cursor.getColumnIndex("_id"));
                    field=classObj.getDeclaredField("uid");
                    field.setAccessible(true);
                    field.set(obj,id);
                }
                else {

                    String val = cursor.getString(cursor.getColumnIndex(cn));
                    try {
                        field = classObj.getDeclaredField(cn);
                        field.setAccessible(true);
                        if(field.getType().equals(Double.class)||field.getType().getSimpleName().equals("double"))
                            field.set(obj, Double.valueOf(val));
                        else

                            field.set(obj, val);
                    }catch (Exception w){w.printStackTrace();}
                }

            }


        }catch (Exception e){e.printStackTrace();}

        return obj;
    }


    public Cursor listAll() {
        Cursor c =


        getReadableDatabase()
                .query(
                        classObj.getSimpleName(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        return c;
    }


    public Cursor read(String id) {
        Cursor c = getReadableDatabase().query(
                classObj.getSimpleName(),
                null,
                "_id" + " = ?",
                new String[]{id},
                null,
                null,
                null);
        return c;
    }



    public Cursor listByCriteria(String criteria,String[] values,String... orderBy) {


        String order=null;
        if (orderBy!=null)
        {
            order="";
            if(orderBy.length>1)
                    for (String forder : orderBy)
                        order = order +forder+" ,";
            else if(orderBy.length==1)
                    order = orderBy[0];

             if(order.endsWith(","))order=order.substring(0,order.length()-1);

        }
        System.out.println("ORDER:"+order==null?"NULL":order);

        System.out.println("CRITERIA:"+criteria==null?"NULL":criteria);
        if(values!=null)
            for(String val:values)
                System.out.println("Value Criteria:  "+val);


        Cursor c = getReadableDatabase().query(
                classObj.getSimpleName(),
                null,
               criteria,
                values,
                null,
                null,
                order
                );

        return c;
    }

    public int delete(String id) {
        return getWritableDatabase().delete(
                classObj.getSimpleName(),
                 "_id = ?",
                new String[]{id});
    }


  /*  public void loadFromCsv2(String path){

        System.out.println("load from:"+path);


        try {
            AssetManager assetManager = context.getAssets();
            InputStream ims = assetManager.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ims));

                String line;

                int j=0;
                String[] cabecera=null;

                while ((line = reader.readLine()) != null) {
                    if(j==0)
                        cabecera = line.split(";");
                    else {
                        String[] RowData = line.split(";");
                        int rc=0;
                        T  obj = (T) classObj.newInstance();
                        for(String cn:cabecera)
                        {
                            String val=RowData[rc];
                          Field field = classObj.getDeclaredField(cn);
                            field.setAccessible(true);
                            if(field.getType().equals(Double.class)||field.getType().getSimpleName().equals("double"))
                                field.set(obj, Double.valueOf(val));
                            else

                                field.set(obj, val);

                            rc++;

                        }
                        System.out.println("importing:"+obj.toString());
                        insert(obj);

                    }
                    j++;
                    System.out.println(line);
                }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }


*/




    public void loadFromCsv(String path) throws  Exception{


        int row = 0;
        int rc = 0;
        int j = 0;
        String line=null;
        String[] cabecera = null;

        try {
            System.out.println("load from:" + path);



//if(true)return ;


            File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File file = new File(sdcard, path);

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedReader reader2 = new BufferedReader(new FileReader(file));

            CsvReader importa=new CsvReader(reader2);
            importa.setDelimiter(';');
        if((line = reader.readLine()) != null)
            cabecera = line.split(";");

           importa.readHeaders();
            while (importa.readRecord()) {
                     rc = 0;
                    T obj = (T) classObj.newInstance();
                    for (String cn : cabecera)
                    {
                        String val =importa.get(rc) ;

                        Field field = classObj.getDeclaredField(cn);
                        field.setAccessible(true);

                   //     System.out.println(field.getName()+"===="+val);
                        if (field.getType().equals(Double.class) || field.getType().getSimpleName().equals("double"))
                            field.set(obj, Double.valueOf(val));
                        else

                            field.set(obj, val);

                        rc++;

                    }
                    System.out.println("importing:"+row+"  "+ obj.toString());
                    insert(obj);
                row++;
//                if(row==1000)  return;
                System.out.println(line);
            }


        }catch (Exception e)
        {


            String cab="";
            try
            {
                cab=cabecera[rc];
            }catch (Exception es)
            {
                cab="";
            }
            throw  new Exception(e.getMessage()+" campo:"+ cab+ " fila:"+row+" column:"+rc,e);
        }


    }





    /*

    public void loadFromCsv(String path) throws  Exception{


        int row = 0;
        int rc = 0;
        int j = 0;
        String line=null;
        String[] cabecera = null;

        try {
            System.out.println("load from:" + path);



//if(true)return ;


            File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File file = new File(sdcard, path);

            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {


                System.out.println("import soource Original:  "+line);
                String regexTxt="((\").*);(.*(\"))";
                Pattern pattern = Pattern.compile(regexTxt);

                Matcher matcher = pattern.matcher(line);
                int init=0;

                boolean continuar=matcher.find(init);


                while(continuar)
                {


                    init=matcher.start();
                    int fin=matcher.end();

                    String finded=line.substring(init,fin);
                   line= line.replace(finded,finded.replace(";",","));
                    matcher = pattern.matcher(line);
                    continuar=matcher.find(0);


                }


                System.out.println("import soource:  "+line);

                if (row == 0)
                    cabecera = line.split(";");
                else {
                    String[] RowData = line.split(";");
                     rc = 0;
                    T obj = (T) classObj.newInstance();
                    for (String cn : cabecera) {
                        String val = RowData[rc];
                        Field field = classObj.getDeclaredField(cn);
                        field.setAccessible(true);
                        if (field.getType().equals(Double.class) || field.getType().getSimpleName().equals("double"))
                            field.set(obj, Double.valueOf(val));
                        else

                            field.set(obj, val);

                        rc++;

                    }

                    System.out.println("importing:"+row+"  "+ obj.toString());

                    //obj.in
                    insert(obj);




                }
                row++;
                System.out.println(line);
            }


        }catch (Exception e)
        {


            String cab="";
            try
            {
                cab=cabecera[rc];
            }catch (Exception es)
            {
                cab="";
            }
            throw  new Exception(e.getMessage()+" campo:"+ cab+ " fila:"+row+" column:"+rc+"<br>"+line,e);
        }


    }


    * */
}
