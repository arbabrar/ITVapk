package policiadnfr.gob.itvremolques.Data.Model;

public class datocertificado {
    public String fecha,hora,nro_certificado,funcionario,celular,placa,mensaje;
    public int status;

    public datocertificado() {
    }

    public datocertificado(String fecha, String hora, String nro_certificado, String funcionario, String celular, String placa, String mensaje, int status) {
        this.fecha = fecha;
        this.hora = hora;
        this.nro_certificado = nro_certificado;
        this.funcionario = funcionario;
        this.celular = celular;
        this.placa = placa;
        this.mensaje = mensaje;
        this.status = status;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNro_certificado() {
        return nro_certificado;
    }

    public void setNro_certificado(String nro_certificado) {
        this.nro_certificado = nro_certificado;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
