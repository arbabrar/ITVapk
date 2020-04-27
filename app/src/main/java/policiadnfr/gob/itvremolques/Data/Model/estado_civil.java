package policiadnfr.gob.itvremolques.Data.Model;

public class estado_civil {
    private int id_estado_civil;
    private String nombre;

    public estado_civil() {
    }

    public estado_civil(int id_estado_civil, String nombre) {
        this.id_estado_civil = id_estado_civil;
        this.nombre = nombre;
    }

    public int getId_estado_civil() {
        return id_estado_civil;
    }

    public void setId_estado_civil(int id_estado_civil) {
        this.id_estado_civil = id_estado_civil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
