package policiadnfr.gob.itvremolques.Data.Model;

public class persona {
    private long id_vehiculo, id_user,id_persona;
    private int  id_pais, id_municipio, tipo_documento, id_expedido, id_categoria_licencia, id_estado_civil,status;
    private String mensaje, nombre, apellido_paterno, apellido_materno, sexo, ocupacion, nro_documento, documento_complemento, localidad, domicilio, celular, email,fecha_nacimiento,fotografia_per;

    public persona(long id_vehiculo, long id_user, long id_persona, int status,int id_pais, int id_municipio, int tipo_documento, int id_expedido, int id_categoria_licencia, int id_estado_civil, String mensaje, String nombre, String apellido_paterno, String apellido_materno, String sexo, String ocupacion, String nro_documento, String documento_complemento, String localidad, String domicilio, String celular, String email, String fecha_nacimiento, String fotografia_per) {
        this.id_vehiculo = id_vehiculo;
        this.id_user = id_user;
        this.id_persona = id_persona;
        this.status = status;
        this.id_pais = id_pais;
        this.id_municipio = id_municipio;
        this.tipo_documento = tipo_documento;
        this.id_expedido = id_expedido;
        this.id_categoria_licencia = id_categoria_licencia;
        this.id_estado_civil = id_estado_civil;
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.sexo = sexo;
        this.ocupacion = ocupacion;
        this.nro_documento = nro_documento;
        this.documento_complemento = documento_complemento;
        this.localidad = localidad;
        this.domicilio = domicilio;
        this.celular = celular;
        this.email = email;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fotografia_per = fotografia_per;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(long id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_persona() {
        return id_persona;
    }

    public void setId_persona(long id_persona) {
        this.id_persona = id_persona;
    }

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }

    public int getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    public int getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(int tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public int getId_expedido() {
        return id_expedido;
    }

    public void setId_expedido(int id_expedido) {
        this.id_expedido = id_expedido;
    }

    public int getId_categoria_licencia() {
        return id_categoria_licencia;
    }

    public void setId_categoria_licencia(int id_categoria_licencia) {
        this.id_categoria_licencia = id_categoria_licencia;
    }

    public int getId_estado_civil() {
        return id_estado_civil;
    }

    public void setId_estado_civil(int id_estado_civil) {
        this.id_estado_civil = id_estado_civil;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public String getDocumento_complemento() {
        return documento_complemento;
    }

    public void setDocumento_complemento(String documento_complemento) {
        this.documento_complemento = documento_complemento;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
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

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getFotografia_per() {
        return fotografia_per;
    }

    public void setFotografia_per(String fotografia_per) {
        this.fotografia_per = fotografia_per;
    }
}
