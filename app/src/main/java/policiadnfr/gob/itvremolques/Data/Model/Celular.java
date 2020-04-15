package policiadnfr.gob.itvremolques.Data.Model;

public class Celular {
    private String NroCelular;
    private String versionAPK;

    public Celular(String nroCelular, String versionAPK) {
        NroCelular = nroCelular;
        this.versionAPK = versionAPK;
    }

    public String getNroCelular() {
        return NroCelular;
    }

    public void setNroCelular(String nroCelular) {
        NroCelular = nroCelular;
    }

    public String getVersionAPK() {
        return versionAPK;
    }

    public void setVersionAPK(String versionAPK) {
        this.versionAPK = versionAPK;
    }
}
