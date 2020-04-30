package policiadnfr.gob.itvremolques.Data.Api;

import java.util.List;

import policiadnfr.gob.itvremolques.Data.Model.Celular;
import policiadnfr.gob.itvremolques.Data.Model.DetalleItv;
import policiadnfr.gob.itvremolques.Data.Model.FotoPersona;
import policiadnfr.gob.itvremolques.Data.Model.FotoVehiculo;
import policiadnfr.gob.itvremolques.Data.Model.Historial;
import policiadnfr.gob.itvremolques.Data.Model.Inspeccion;
import policiadnfr.gob.itvremolques.Data.Model.ItvID;
import policiadnfr.gob.itvremolques.Data.Model.LoginBody;
import policiadnfr.gob.itvremolques.Data.Model.RegistroITV;
import policiadnfr.gob.itvremolques.Data.Model.RegistroVehiculo;
import policiadnfr.gob.itvremolques.Data.Model.ResponseCheck;
import policiadnfr.gob.itvremolques.Data.Model.User;
import policiadnfr.gob.itvremolques.Data.Model.Vehiculo;
import policiadnfr.gob.itvremolques.Data.Model.datocertificado;
import policiadnfr.gob.itvremolques.Data.Model.datosPersona;
import policiadnfr.gob.itvremolques.Data.Model.departamento;
import policiadnfr.gob.itvremolques.Data.Model.persona;
import policiadnfr.gob.itvremolques.Data.Model.puntoItv;
import policiadnfr.gob.itvremolques.Data.Model.reservas;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRoutes {
    @POST("Newlogin")
    Call<User> login(@Body LoginBody loginBody);
    @POST("VerificacionMovil")
    Call<ResponseCheck> CheckPhone(@Body Celular celular);
    @GET("departamento")
    Call<List<departamento>> getDepartamento();
    @GET("puntosITV/{id}")
    Call<List<puntoItv>> getpuntoItv(@Path("id") int id_departamento);
    @GET("reservas/{id}")
    Call<List<reservas>> getReservas(@Path("id") int id_punto_inspeccion);
    @POST("DatosTecnicos")
    Call<Vehiculo> getDatosTecnicos(@Body RegistroITV RegistroITV);
    @POST("CompletaregistroVehiculo")
    Call<Vehiculo> RegistroVehiculo(@Body RegistroVehiculo RegistroVehiculo);
    @POST("SetfotoVehiculo")
    Call<Vehiculo> subirVehiculo(@Body FotoVehiculo FotoVehiculo);
    @GET("getPersona/{cedula}/{complemento}")
    Call<datosPersona> getPersona(@Path("cedula") String nrodcocumento, @Path("complemento") String complemento);
    @GET("getDatoPersona/{id}")
    Call<datosPersona> getDatoPersona(@Path("id") long id_persona);
    @POST("SavePersona")
    Call<persona> Registropersona(@Body persona persona);
    @POST("SetfotoPersona")
    Call<persona> subirPersona(@Body FotoPersona FotoPersona);
    @POST("DatosDetalleITV")
    Call<ItvID> RegistrodetalleITV(@Body DetalleItv DetalleItv);
    @GET("SearchCertificado/{certificado}")
    Call<datocertificado> getdatoscertificado(@Path("certificado") String certificado);
    @POST("RegistroInspeccion")
    Call<Inspeccion> RegistrarInspeccion(@Body Inspeccion Inspeccion);
    @GET("SearchInspecccion/{fecha}/{id_user})")
    Call<List<Historial>> getInspecciones(@Path("fecha") String fecha,@Path("id_user") long id_user);
   /* @GET("paises")
    Call<List<pais>> getpais();
    @GET("provincia/{id}")
    Call<List<provincia>> getprovincia(@Path("id") String departamento);
    @POST("municipio")
    Call<List<municipio>> getmunicipio(@Body BodygetMunicipio BodygetMunicipio);
    @GET("expedido")
    Call<List<expedido>> getexpedido();

    @POST("detalleITV")
    Call<ItvID> RegistrodetalleITV(@Body DetalleItv DetalleItv);


    @POST("InspeccionRegistro")
    Call<Inspeccion> RegistrarInspeccion(@Body Inspeccion Inspeccion);
    @GET("Certificado/{certificado}")
    Call<datocertificado> getdatoscertificado(@Path("certificado") String certificado);
    @POST("buscaritv")
    Call<datobusquedaITV> BuscarITV(@Body datoItv datoitv);
    @GET("getReserva/{id}")
    Call<datosReserva> getreserva(@Path("id") int id_reserva);*/
}
