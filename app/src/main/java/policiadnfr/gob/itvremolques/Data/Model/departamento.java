package policiadnfr.gob.itvremolques.Data.Model;

public class departamento {
    private int id_departamento;
    private String nombre,imagen;


    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getImageURL(){
        return"http://android.policiadnfr.gob.bo/imagenes/"+this.id_departamento+".png";
    }
}
