package policiadnfr.gob.itvremolques.Data.Model;

public class ItvID {
    public int status;
    public long id_persona,id_vehiculo;
    public String mensaje;

    public ItvID() {
    }

    public ItvID(int status, long id_persona, long id_vehiculo, String mensaje) {
        this.status = status;
        this.id_persona = id_persona;
        this.id_vehiculo = id_vehiculo;
        this.mensaje = mensaje;
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

    public long getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(long id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
