package policiadnfr.gob.itvremolques.Data.Model;

public class FotoVehiculo {
    public int id_user;
    public long id_vehiculo;
    public String imagen;

    public FotoVehiculo(int id_user, long id_vehiculo, String imagen) {
        this.id_user = id_user;
        this.id_vehiculo = id_vehiculo;
        this.imagen = imagen;
    }

    public FotoVehiculo() {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public long getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(long id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
