package policiadnfr.gob.itvremolques.Data.Model;

public class FotoPersona {
    public int id_user;
    public long id_persona;
    public String imagen;

    public FotoPersona() {
    }

    public FotoPersona(int id_user, long id_persona, String imagen) {
        this.id_user = id_user;
        this.id_persona = id_persona;
        this.imagen = imagen;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public long getId_persona() {
        return id_persona;
    }

    public void setId_persona(long id_persona) {
        this.id_persona = id_persona;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
