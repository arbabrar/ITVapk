package policiadnfr.gob.itvremolques.Data.Model;

public class tipodocumento {
    private int id_tipo_documento;
    private String nombre;

    public tipodocumento(int id_tipo_documento, String nombre) {
        this.id_tipo_documento = id_tipo_documento;
        this.nombre = nombre;
    }

    public tipodocumento() {
    }

    public int getId_tipo_documento() {
        return id_tipo_documento;
    }

    public void setId_tipo_documento(int id_tipo_documento) {
        this.id_tipo_documento = id_tipo_documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
