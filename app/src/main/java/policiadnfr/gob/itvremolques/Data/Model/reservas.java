package policiadnfr.gob.itvremolques.Data.Model;

public class reservas {
    private int id_reserva;
    private String hora_reserva,NombreP,apellido_paterno,apellido_materno,placa;

    public reservas(int id_reserva, String hora_reserva, String NombreP, String apellido_paterno, String apellido_materno, String placa) {
        this.id_reserva = id_reserva;
        this.hora_reserva = hora_reserva;
        this.NombreP = NombreP;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.placa = placa;
    }

    public reservas() {
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getHora_reserva() {
        return hora_reserva;
    }

    public void setHora_reserva(String hora_reserva) {
        this.hora_reserva = hora_reserva;
    }

    public String getNombreP() {
        return NombreP;
    }

    public void setNombreP(String nombreP) {
        this.NombreP = nombreP;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
