package policiadnfr.gob.itvremolques.Data.Model;

public class expedido {
    private int id_expedido;
    private String nombre;

    public expedido(int id_expedido, String nombre) {
        this.id_expedido = id_expedido;
        this.nombre = nombre;
    }

    public expedido() {
    }

    public int getId_expedido() {
        return id_expedido;
    }

    public void setId_expedido(int id_expedido) {
        this.id_expedido = id_expedido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
