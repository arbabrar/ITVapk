package policiadnfr.gob.itvremolques.Data.Model;

public class municipio {
    private int id_municipio;
    private String nombre;

    public municipio() {
    }

    public municipio(int id_municipio, String nombre) {
        this.id_municipio = id_municipio;
        this.nombre = nombre;
    }

    public int getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
