package policiadnfr.gob.itvremolques.Data.Api;

import java.util.List;

import policiadnfr.gob.itvremolques.Data.Model.Celular;
import policiadnfr.gob.itvremolques.Data.Model.LoginBody;
import policiadnfr.gob.itvremolques.Data.Model.RegistroITV;
import policiadnfr.gob.itvremolques.Data.Model.ResponseCheck;
import policiadnfr.gob.itvremolques.Data.Model.User;
import policiadnfr.gob.itvremolques.Data.Model.Vehiculo;
import policiadnfr.gob.itvremolques.Data.Model.departamento;
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
    /*@POST("registroVehiculo")
    Call<Vehiculo> RegistroVehiculo(@Body RegistroVehiculo RegistroVehiculo);
    @GET("paises")
    Call<List<pais>> getpais();
    @GET("provincia/{id}")
    Call<List<provincia>> getprovincia(@Path("id") String departamento);
    @POST("municipio")
    Call<List<municipio>> getmunicipio(@Body BodygetMunicipio BodygetMunicipio);
    @GET("expedido")
    Call<List<expedido>> getexpedido();
    @POST("registroPersona")
    Call<persona> Registropersona(@Body persona persona);
    @POST("detalleITV")
    Call<ItvID> RegistrodetalleITV(@Body DetalleItv DetalleItv);
    @POST("fotoVehiculo")
    Call<Vehiculo> subirVehiculo(@Body FotoVehiculo FotoVehiculo);
    @POST("fotoPersona")
    Call<persona> subirPersona(@Body FotoPersona FotoPersona);
    @POST("InspeccionRegistro")
    Call<Inspeccion> RegistrarInspeccion(@Body Inspeccion Inspeccion);
    @GET("Certificado/{certificado}")
    Call<datocertificado> getdatoscertificado(@Path("certificado") String certificado);
    @POST("buscaritv")
    Call<datobusquedaITV> BuscarITV(@Body datoItv datoitv);
    @GET("getReserva/{id}")
    Call<datosReserva> getreserva(@Path("id") int id_reserva);*/
}
