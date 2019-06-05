package app.lartisco.com.catalogoproductos1.exception;

/**
 * Created by user on 04/10/17.
 */

public class ErrorValidacion extends  Exception{

    public ErrorValidacion(String msg,Exception e)
    {
        super(msg,e);
    }

    public ErrorValidacion(String msg)
    {
        super(msg);
    }
}
