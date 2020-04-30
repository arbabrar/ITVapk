package policiadnfr.gob.itvremolques.Data.Model;

public class DetalleItv {
    private String freno,cilindro,luces,stop,luzretro,luzparqueo,parabrisas,volante,suspension,direccion,neumatico,humo,retrovisor,cinturon,vidrios,gata,ruadaauxiliar,botiquin,extintor;
    private int id_user;
    private long id_vehiculo;
    public DetalleItv(String freno, String cilindro, String luces, String stop, String luzretro, String luzparqueo, String parabrisas, String volante, String suspension, String direccion, String neumatico, String humo, String retrovisor, String cinturon, String vidrios, String gata, String ruadaauxiliar, String botiquin, String extintor, int id_user, long id_vehiculo) {
        this.freno = freno;
        this.cilindro = cilindro;
        this.luces = luces;
        this.stop = stop;
        this.luzretro = luzretro;
        this.luzparqueo = luzparqueo;
        this.parabrisas = parabrisas;
        this.volante = volante;
        this.suspension = suspension;
        this.direccion = direccion;
        this.neumatico = neumatico;
        this.humo = humo;
        this.retrovisor = retrovisor;
        this.cinturon = cinturon;
        this.vidrios = vidrios;
        this.gata = gata;
        this.ruadaauxiliar = ruadaauxiliar;
        this.botiquin = botiquin;
        this.extintor = extintor;
        this.id_user = id_user;
        this.id_vehiculo = id_vehiculo;
    }

    public long getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(long id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public DetalleItv() {
    }

    public String getFreno() {
        return freno;
    }

    public void setFreno(String freno) {
        this.freno = freno;
    }

    public String getCilindro() {
        return cilindro;
    }

    public void setCilindro(String cilindro) {
        this.cilindro = cilindro;
    }

    public String getLuces() {
        return luces;
    }

    public void setLuces(String luces) {
        this.luces = luces;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getLuzretro() {
        return luzretro;
    }

    public void setLuzretro(String luzretro) {
        this.luzretro = luzretro;
    }

    public String getLuzparqueo() {
        return luzparqueo;
    }

    public void setLuzparqueo(String luzparqueo) {
        this.luzparqueo = luzparqueo;
    }

    public String getParabrisas() {
        return parabrisas;
    }

    public void setParabrisas(String parabrisas) {
        this.parabrisas = parabrisas;
    }

    public String getVolante() {
        return volante;
    }

    public void setVolante(String volante) {
        this.volante = volante;
    }

    public String getSuspension() {
        return suspension;
    }

    public void setSuspension(String suspension) {
        this.suspension = suspension;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNeumatico() {
        return neumatico;
    }

    public void setNeumatico(String neumatico) {
        this.neumatico = neumatico;
    }

    public String getHumo() {
        return humo;
    }

    public void setHumo(String humo) {
        this.humo = humo;
    }

    public String getRetrovisor() {
        return retrovisor;
    }

    public void setRetrovisor(String retrovisor) {
        this.retrovisor = retrovisor;
    }

    public String getCinturon() {
        return cinturon;
    }

    public void setCinturon(String cinturon) {
        this.cinturon = cinturon;
    }

    public String getVidrios() {
        return vidrios;
    }

    public void setVidrios(String vidrios) {
        this.vidrios = vidrios;
    }

    public String getGata() {
        return gata;
    }

    public void setGata(String gata) {
        this.gata = gata;
    }

    public String getRuadaauxiliar() {
        return ruadaauxiliar;
    }

    public void setRuadaauxiliar(String ruadaauxiliar) {
        this.ruadaauxiliar = ruadaauxiliar;
    }

    public String getBotiquin() {
        return botiquin;
    }

    public void setBotiquin(String botiquin) {
        this.botiquin = botiquin;
    }

    public String getExtintor() {
        return extintor;
    }

    public void setExtintor(String extintor) {
        this.extintor = extintor;
    }
}
