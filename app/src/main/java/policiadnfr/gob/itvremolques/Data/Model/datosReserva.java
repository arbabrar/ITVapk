package policiadnfr.gob.itvremolques.Data.Model;

public class datosReserva {
    public int id_vehiculo,id_persona;
    public String chasis,motor,placa,ocupacion,nro_documento,domicilio,localidad,celular,email,documento_complemento,nombrePersona,apellido_paterno,apellido_materno,servicio;

    public datosReserva(int id_vehiculo, int id_persona, String chasis, String motor, String placa, String ocupacion, String nro_documento, String domicilio, String localidad, String celular, String email, String documento_complemento, String nombrePersona, String apellido_paterno, String apellido_materno, String servicio) {
        this.id_vehiculo = id_vehiculo;
        this.id_persona = id_persona;
        this.chasis = chasis;
        this.motor = motor;
        this.placa = placa;
        this.ocupacion = ocupacion;
        this.nro_documento = nro_documento;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.celular = celular;
        this.email = email;
        this.documento_complemento = documento_complemento;
        this.nombrePersona = nombrePersona;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.servicio = servicio;
    }

    public datosReserva() {
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNro_documento() {
        return nro_documento;
    }

    public void setNro_documento(String nro_documento) {
        this.nro_documento = nro_documento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento_complemento() {
        return documento_complemento;
    }

    public void setDocumento_complemento(String documento_complemento) {
        this.documento_complemento = documento_complemento;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
}
