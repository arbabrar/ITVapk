package policiadnfr.gob.itvremolques.Data.Model;

public class puntoItv {
    private int id_punto_inspeccion;
    private String direccion,municipio;

    public int getId_punto_inspeccion() {
        return id_punto_inspeccion;
    }

    public void setId_punto_inspeccion(int id_punto_inspeccion) {
        this.id_punto_inspeccion = id_punto_inspeccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}
