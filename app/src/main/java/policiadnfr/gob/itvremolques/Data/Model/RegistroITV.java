package policiadnfr.gob.itvremolques.Data.Model;

public class RegistroITV {
    private String placa,crpva,tipo_placa,copia,version,nombre_fun,punto_itv,municipio;
    private int id_user;

    public RegistroITV(String placa, String crpva, String tipo_placa, String copia, String version, String nombre_fun, String punto_itv, String municipio, int id_user) {
        this.placa = placa;
        this.crpva = crpva;
        this.tipo_placa = tipo_placa;
        this.copia = copia;
        this.version = version;
        this.nombre_fun = nombre_fun;
        this.punto_itv = punto_itv;
        this.municipio = municipio;
        this.id_user = id_user;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCrpva() {
        return crpva;
    }

    public void setCrpva(String crpva) {
        this.crpva = crpva;
    }

    public String getTipo_placa() {
        return tipo_placa;
    }

    public void setTipo_placa(String tipo_placa) {
        this.tipo_placa = tipo_placa;
    }

    public String getCopia() {
        return copia;
    }

    public void setCopia(String copia) {
        this.copia = copia;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNombre_fun() {
        return nombre_fun;
    }

    public void setNombre_fun(String nombre_fun) {
        this.nombre_fun = nombre_fun;
    }

    public String getPunto_itv() {
        return punto_itv;
    }

    public void setPunto_itv(String punto_itv) {
        this.punto_itv = punto_itv;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
