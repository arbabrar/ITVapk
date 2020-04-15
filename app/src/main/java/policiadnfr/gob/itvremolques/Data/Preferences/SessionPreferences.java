package policiadnfr.gob.itvremolques.Data.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import policiadnfr.gob.itvremolques.Data.Model.ItvID;
import policiadnfr.gob.itvremolques.Data.Model.User;
import policiadnfr.gob.itvremolques.Data.Model.datosReserva;
import policiadnfr.gob.itvremolques.Data.Model.puntoItv;


public class SessionPreferences {
    private static final String PREFS_NAME="SESSION";

    //Constantes de Sesion
    private static final String PREF_NOMBRE="PREF_NOMBRE";
    private static final String PREF_USERNAME="PREF_USERNAME";
    private static final String PREF_EMAIL="PREF_EMAIL";
    private static final String PREF_IDUSER="PREF_IDUSER";
    private static final String PREF_SESSION="PREF_SESSION";
    private static final String PREF_PUNTO="PREF_PUNTO";
    private static final String PREF_NOMBREPUNTO="PREF_NOMBREPUNTO";
    private static final String PREF_IDPUNTO="PREF_IDPUNTO";
    private static final String PREF_MUNICIPIO="PREF_MUNICIPIO";
    private static final String DATO_IDVEHICULO="DATO_IDVEHICULO";
    private static final String DATO_IDPERSONA="DATO_IDPERSONA";
    private static final String RESERVA="RESERVA";
    private static final String R_NOMBRE="R_NOMBRE";
    private static final String R_PATERNO="R_PATERNO";
    private static final String R_MATERNO="R_MATERNO";
    private static final String R_OCUPACION="R_OCUPACION";
    private static final String R_DOCUMENTO="R_DOCUMENTO";
    private static final String R_DOMICILIO="R_DOMUCILIO";
    private static final String R_LOCALIDAD="R_LOCALIDAD";
    private static final String R_CELULAR="R_CELULAR";
    private static final String R_EMAIL="R_EMAIL";
    private static final String R_COMP_DOC="R_COMP_DOC";


    private final SharedPreferences prefs;

    public SessionPreferences(Context ctx) {
      prefs=ctx.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public boolean estalogueado()
    {
        return prefs.getBoolean(PREF_SESSION,false);
    }
    public void guardarUsuario(User user){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(PREF_NOMBRE,user.getNombres());
        editor.putInt(PREF_IDUSER,user.getId());
        editor.putString(PREF_USERNAME,user.getUsername());
        editor.putString(PREF_EMAIL,user.getEmail());
        editor.putBoolean(PREF_SESSION,true);
        editor.apply();
    }
    public boolean tienepunto(){
        return prefs.getBoolean(PREF_PUNTO,false);
    }
    public void guardarpunto(int idpunto, String direccionpunto, String municipio){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(PREF_NOMBREPUNTO,direccionpunto);
        editor.putInt(PREF_IDPUNTO,idpunto);
        editor.putString(PREF_MUNICIPIO,municipio);
        editor.putBoolean(PREF_PUNTO,true);
        editor.apply();
    }
    public void SaveReserva(datosReserva datosReserva){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(R_NOMBRE,datosReserva.getNombrePersona());
        editor.putString(R_PATERNO,datosReserva.getApellido_paterno());
        editor.putString(R_MATERNO,datosReserva.getApellido_materno());
        editor.putString(R_OCUPACION,datosReserva.getOcupacion());
        editor.putString(R_DOCUMENTO,datosReserva.getNro_documento());
        editor.putString(R_DOMICILIO,datosReserva.getDomicilio());
        editor.putString(R_LOCALIDAD,datosReserva.getLocalidad());
        editor.putString(R_CELULAR,datosReserva.getCelular());
        editor.putString(R_EMAIL,datosReserva.getEmail());
        editor.putString(R_COMP_DOC,datosReserva.getDocumento_complemento());
        editor.putBoolean(RESERVA,true);
        editor.apply();
    }
    public void cleanReserva(){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(R_NOMBRE,null);
        editor.putInt(R_PATERNO,0);
        editor.putString(R_MATERNO,null);
        editor.putString(R_OCUPACION,null);
        editor.putString(R_DOCUMENTO,null);
        editor.putString(R_DOMICILIO,null);
        editor.putString(R_LOCALIDAD,null);
        editor.putString(R_CELULAR,null);
        editor.putString(R_EMAIL,null);
        editor.putString(R_COMP_DOC,null);
        editor.putBoolean(RESERVA,false);
        editor.apply();


    }

    public boolean esReserva(){
        return prefs.getBoolean(RESERVA,false);
    }

    public void initITV(long id_vehiculo,long id_persona){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putLong(DATO_IDPERSONA,id_persona);
        editor.putLong(DATO_IDVEHICULO,id_vehiculo);
        editor.apply();
    }
    public void finishITV(){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putLong(DATO_IDPERSONA,0);
        editor.putLong(DATO_IDVEHICULO,0);
        editor.apply();
    }
    public void cerrarSesion(){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(PREF_NOMBRE,null);
        editor.putInt(PREF_IDUSER,0);
        editor.putString(PREF_NOMBREPUNTO,null);
        editor.putString(PREF_MUNICIPIO,null);
        editor.putInt(PREF_IDPUNTO,0);
        editor.putString(PREF_USERNAME,null);
        editor.putString(PREF_EMAIL,null);
        editor.putBoolean(PREF_SESSION,false);
        editor.putBoolean(PREF_PUNTO,false);
        editor.apply();
    }
    public void cambiarPuntoITV(){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(PREF_NOMBREPUNTO,null);
        editor.putString(PREF_MUNICIPIO,null);
        editor.putInt(PREF_IDPUNTO,0);
        editor.putBoolean(PREF_PUNTO,false);
        editor.apply();
    }
    public puntoItv getpuntoItv(){
        puntoItv _objpunto=new puntoItv();
        _objpunto.setDireccion(prefs.getString(PREF_NOMBREPUNTO,""));
        _objpunto.setMunicipio(prefs.getString(PREF_MUNICIPIO,""));
        _objpunto.setId_punto_inspeccion(prefs.getInt(PREF_IDPUNTO,0));
        return _objpunto;

    }
    public User getUsuario(){
        User _objUser=new User();
        _objUser.setUsername(prefs.getString(PREF_USERNAME,""));
        _objUser.setNombres(prefs.getString(PREF_NOMBRE,""));
        _objUser.setEmail(prefs.getString(PREF_EMAIL,""));
        _objUser.setId(prefs.getInt(PREF_IDUSER,0));
        return _objUser;

    }
    public datosReserva getReserva(){
        datosReserva _objReserva=new datosReserva();
        _objReserva.setNombrePersona(   prefs.getString(R_NOMBRE,""));
        _objReserva.setApellido_paterno(prefs.getString(R_PATERNO,""));
        _objReserva.setApellido_materno(prefs.getString(R_MATERNO,""));
        _objReserva.setOcupacion(prefs.getString(R_OCUPACION,""));
        _objReserva.setDomicilio(prefs.getString(R_DOMICILIO,""));
        _objReserva.setNro_documento(prefs.getString(R_DOCUMENTO,""));
        _objReserva.setLocalidad(prefs.getString(R_LOCALIDAD,""));
        _objReserva.setCelular(prefs.getString(R_CELULAR,""));
        _objReserva.setEmail(prefs.getString(R_EMAIL,""));
        _objReserva.setDocumento_complemento(prefs.getString(R_COMP_DOC,""));


        return _objReserva;

    }
    public ItvID getITV(){
        ItvID _objITV=new ItvID();
        _objITV.setId_vehiculo(prefs.getLong(DATO_IDVEHICULO,0));
        _objITV.setId_persona(prefs.getLong(DATO_IDPERSONA,0));
       return _objITV;

    }
}
