package app.lartisco.com.catalogoproductos1.data.modelo;

public class Compras {
    private Long uid;
    private double com_anio;
    private double com_enero;
    private double com_febrero;
    private double com_marzo;
    private double com_abril;
    private double com_mayo;
    private double com_junio;
    private double com_julio;
    private double com_agosto;
    private double com_septiembre;
    private double com_octubre;
    private double com_noviembre;
    private double com_diciembre;
    private String  com_pro_cod;




    public double getCom_anio() {
        return com_anio;
    }

    public void setCom_anio(double com_anio) {
        this.com_anio = com_anio;
    }

    public double getCom_enero() {
        return com_enero;
    }

    public void setCom_enero(double com_enero) {
        this.com_enero = com_enero;
    }

    public double getCom_febrero() {
        return com_febrero;
    }

    public void setCom_febrero(double com_febrero) {
        this.com_febrero = com_febrero;
    }

    public double getCom_marzo() {
        return com_marzo;
    }

    public void setCom_marzo(double com_marzo) {
        this.com_marzo = com_marzo;
    }

    public double getCom_abril() {
        return com_abril;
    }

    public void setCom_abril(double com_abril) {
        this.com_abril = com_abril;
    }

    public double getCom_mayo() {
        return com_mayo;
    }

    public void setCom_mayo(double com_mayo) {
        this.com_mayo = com_mayo;
    }

    public double getCom_junio() {
        return com_junio;
    }

    public void setCom_junio(double com_junio) {
        this.com_junio = com_junio;
    }

    public double getCom_julio() {
        return com_julio;
    }

    public void setCom_julio(double com_julio) {
        this.com_julio = com_julio;
    }

    public double getCom_agosto() {
        return com_agosto;
    }

    public void setCom_agosto(double com_agosto) {
        this.com_agosto = com_agosto;
    }

    public double getCom_septiembre() {
        return com_septiembre;
    }

    public void setCom_septiembre(double com_septiembre) {
        this.com_septiembre = com_septiembre;
    }

    public double getCom_octubre() {
        return com_octubre;
    }

    public void setCom_octubre(double com_octubre) {
        this.com_octubre = com_octubre;
    }

    public double getCom_noviembre() {
        return com_noviembre;
    }

    public void setCom_noviembre(double com_noviembre) {
        this.com_noviembre = com_noviembre;
    }

    public double getCom_diciembre() {
        return com_diciembre;
    }

    public void setCom_diciembre(double com_diciembre) {
        this.com_diciembre = com_diciembre;
    }

    public String getCom_pro_cod() {
        return com_pro_cod;
    }

    public void setCom_pro_cod(String com_pro_cod) {
        this.com_pro_cod = com_pro_cod;
    }

    @Override
    public String toString() {
        return "Compras{" +
                "Id=" + uid +
                ", com_anio=" + com_anio +
                ", com_enero=" + com_enero +
                ", com_febrero=" + com_febrero +
                ", com_marzo=" + com_marzo +
                ", com_abril=" + com_abril +
                ", com_mayo=" + com_mayo +
                ", com_junio=" + com_junio +
                ", com_julio=" + com_julio +
                ", com_agosto=" + com_agosto +
                ", com_septiembre=" + com_septiembre +
                ", com_octubre=" + com_octubre +
                ", com_noviembre=" + com_noviembre +
                ", com_diciembre=" + com_diciembre +
                ", com_pro_cod='" + com_pro_cod + '\'' +
                '}';
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }


}
