package policiadnfr.gob.itvremolques.Data.Model;

public class ResponseCheck {
    private int status;
    private String mensaje;
    private String PhoneNumber;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public ResponseCheck(int status, String mensaje, String phoneNumber) {
        this.status = status;
        this.mensaje = mensaje;
        PhoneNumber = phoneNumber;
    }
}
