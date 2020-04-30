package policiadnfr.gob.itvremolques.Data.Model;

public class Historial {
    public long id_inspeccion;
    public String placa,nro_certificado,hora,propietario,nro_documento;

    public Historial(long id_inspeccion, String placa, String nro_certificado, String hora, String propietario, String nro_documento) {
        this.id_inspeccion = id_inspeccion;
        this.placa = placa;
        this.nro_certificado = nro_certificado;
        this.hora = hora;
        this.propietario = propietario;
        this.nro_documento = nro_documento;
    }

    public long getId_inspeccion() {
        return id_inspeccion;
    }

    public void setId_inspeccion(long id_inspeccion) {
        this.id_inspeccion = id_inspeccion;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNro_certificado() {
        return nro_certificado;
    }

    public void setNro_certificado(String nro_certificado) {
        this.nro_certificado = nro_certificado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getNro_documento() {
        return nro_documento;
    }

    public void setNro_documento(String nro_documento) {
        this.nro_documento = nro_documento;
    }
}
