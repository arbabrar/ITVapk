package policiadnfr.gob.itvremolques.Data.Model;

public class RegistroVehiculo {
    private String tipovehiculo,tipocombustible;
    private int id_user, id_vehiculo,id_municipio,id_radicatoria;

    public RegistroVehiculo() {
    }

    public RegistroVehiculo(String tipovehiculo, String tipocombustible, int id_user, int id_vehiculo, int id_municipio, int id_radicatoria) {
        this.tipovehiculo = tipovehiculo;
        this.tipocombustible = tipocombustible;
        this.id_user = id_user;
        this.id_vehiculo = id_vehiculo;
        this.id_municipio = id_municipio;
        this.id_radicatoria = id_radicatoria;
    }

    public String getTipovehiculo() {
        return tipovehiculo;
    }

    public void setTipovehiculo(String tipovehiculo) {
        this.tipovehiculo = tipovehiculo;
    }

    public String getTipocombustible() {
        return tipocombustible;
    }

    public void setTipocombustible(String tipocombustible) {
        this.tipocombustible = tipocombustible;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public int getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    public int getId_radicatoria() {
        return id_radicatoria;
    }

    public void setId_radicatoria(int id_radicatoria) {
        this.id_radicatoria = id_radicatoria;
    }
}
