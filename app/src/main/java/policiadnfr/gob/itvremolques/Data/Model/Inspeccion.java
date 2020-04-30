package policiadnfr.gob.itvremolques.Data.Model;

public class Inspeccion {

    public int status,id_user,id_punto_inspeccion;
    public long id_persona,id_vehiculo;
    public String mensaje,nro_certificado,key_inspeccion;
    public double latitud,longitud;

    public Inspeccion() {
    }

    public Inspeccion(int status, int id_user, int id_punto_inspeccion, long id_persona, long id_vehiculo, String mensaje, String nro_certificado, String key_inspeccion, double latitud, double longitud) {
        this.status = status;
        this.id_user = id_user;
        this.id_punto_inspeccion = id_punto_inspeccion;
        this.id_persona = id_persona;
        this.id_vehiculo = id_vehiculo;
        this.mensaje = mensaje;
        this.nro_certificado = nro_certificado;
        this.key_inspeccion = key_inspeccion;
        this.latitud = latitud;
        this.longitud = longitud;

    }


    public String getKey_inspeccion() {
        return key_inspeccion;
    }

    public void setKey_inspeccion(String key_inspeccion) {
        this.key_inspeccion = key_inspeccion;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_punto_inspeccion() {
        return id_punto_inspeccion;
    }

    public void setId_punto_inspeccion(int id_punto_inspeccion) {
        this.id_punto_inspeccion = id_punto_inspeccion;
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

    public String getNro_certificado() {
        return nro_certificado;
    }

    public void setNro_certificado(String nro_certificado) {
        this.nro_certificado = nro_certificado;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
