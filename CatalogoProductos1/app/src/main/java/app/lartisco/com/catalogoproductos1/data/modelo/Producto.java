package app.lartisco.com.catalogoproductos1.data.modelo;

import android.content.ContentValues;

public class Producto {

    private Long uid;
    private String pro_cod;
    private String pro_referencia;
    private String pro_descripcion;
    private String pro_descrip_ing;
    private String pro_embalaje;
    private double pro_precio_lista;
    private double pro_stock;
    private String pro_fecha;
    private String pro_cantidad;
    private String pro_valor_compra;


    public String getPro_cod() {
        return pro_cod;
    }

    public void setPro_cod(String pro_cod) {
        this.pro_cod = pro_cod;
    }

    public String getPro_referencia() {
        return pro_referencia;
    }

    public void setPro_referencia(String pro_referencia) {
        this.pro_referencia = pro_referencia;
    }

    public String getPro_descripcion() {
        return pro_descripcion;
    }

    public void setPro_descripcion(String pro_descripcion) {
        this.pro_descripcion = pro_descripcion;
    }

    public String getPro_descrip_ing() {
        return pro_descrip_ing;
    }

    public void setPro_descrip_ing(String pro_descrip_ing) {
        this.pro_descrip_ing = pro_descrip_ing;
    }

    public String getPro_embalaje() {
        return pro_embalaje;
    }

    public void setPro_embalaje(String pro_embalaje) {
        this.pro_embalaje = pro_embalaje;
    }

    public double getPro_precio_lista() {
        return pro_precio_lista;
    }

    public void setPro_precio_lista(double pro_precio_lista) {
        this.pro_precio_lista = pro_precio_lista;
    }

    public double getPro_stock() {
        return pro_stock;
    }

    public void setPro_stock(double pro_stock) {
        this.pro_stock = pro_stock;
    }

    public String getPro_fecha() {
        return pro_fecha;
    }

    public void setPro_fecha(String pro_fecha) {
        this.pro_fecha = pro_fecha;
    }

    public String getPro_cantidad() {
        return pro_cantidad;
    }

    public void setPro_cantidad(String pro_cantidad) {
        this.pro_cantidad = pro_cantidad;
    }

    public String getPro_valor_compra() {
        return pro_valor_compra;
    }

    public void setPro_valor_compra(String pro_valor_compra) {
        this.pro_valor_compra = pro_valor_compra;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "pro_cod='" + pro_cod + '\'' +
                ", pro_referencia='" + pro_referencia + '\'' +
                ", pro_descripcion='" + pro_descripcion + '\'' +
                ", pro_descrip_ing='" + pro_descrip_ing + '\'' +
                ", pro_embalaje='" + pro_embalaje + '\'' +
                ", pro_precio_lista=" + pro_precio_lista +
                ", pro_stock=" + pro_stock +
                ", pro_fecha='" + pro_fecha + '\'' +
                ", pro_cantidad='" + pro_cantidad + '\'' +
                ", pro_valor_compra='" + pro_valor_compra + '\'' +
                '}';
    }


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
