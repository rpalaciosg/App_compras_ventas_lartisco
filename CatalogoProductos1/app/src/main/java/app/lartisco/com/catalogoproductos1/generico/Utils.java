package app.lartisco.com.catalogoproductos1.generico;

/**
 * Created by user on 09/01/17.
 */

public class Utils {


    public static String rellenarCaracteresDerecha(String val,int tam)
    {
        if(val.length()>tam)
            return val.substring(0,tam);
        while(val.length()<tam)
            val=val+" ";
        return val;
    }
    public static String rellenarCaracteresIzquierda(String val,int tam)
    {
        if(val.length()>tam)
            return val.substring(0,tam);
        while(val.length()<tam)
            val=" "+val;
        return val;
    }
}
