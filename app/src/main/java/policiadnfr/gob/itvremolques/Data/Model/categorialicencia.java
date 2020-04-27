package policiadnfr.gob.itvremolques.Data.Model;

public class categorialicencia {
    private int id_categoria_licencia;
    private String nombre;

    public categorialicencia(int id_categoria_licencia, String nombre) {
        this.id_categoria_licencia = id_categoria_licencia;
        this.nombre = nombre;
    }

    public categorialicencia() {
    }

    public int getId_categoria_licencia() {
        return id_categoria_licencia;
    }

    public void setId_categoria_licencia(int id_categoria_licencia) {
        this.id_categoria_licencia = id_categoria_licencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
