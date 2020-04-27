package policiadnfr.gob.itvremolques.Data.Model;

public class datosPersona {
    private int status;
    private long id_persona;
    private String fotografia;
    private persona persona;

    public datosPersona(int status, long id_persona, String fotografia, policiadnfr.gob.itvremolques.Data.Model.persona persona) {
        this.status = status;
        this.id_persona = id_persona;
        this.fotografia = fotografia;
        this.persona = persona;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getId_persona() {
        return id_persona;
    }

    public void setId_persona(long id_persona) {
        this.id_persona = id_persona;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public policiadnfr.gob.itvremolques.Data.Model.persona getPersona() {
        return persona;
    }

    public void setPersona(policiadnfr.gob.itvremolques.Data.Model.persona persona) {
        this.persona = persona;
    }
}
