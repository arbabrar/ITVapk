package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.Inspeccion;
import policiadnfr.gob.itvremolques.Data.Model.datocertificado;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.Data.Views.Activity.Dialog.BuscarCertificadoDialog;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NroCertificadoActivity extends AppCompatActivity implements LocationListener {
Button btncertificado;
ProgressDialog pdDialog;
private EditText etcertificado;
public double latitud, longitud;
int id_user, id_municipio, id_punto_inspeccion;
long id_persona, id_vehiculo;
private SessionPreferences prefs;
public LocationManager locManager;
public Criteria criteria;
AlertDialog alert = null;
public String bestProvider ,certificado;
private View mLayout;
int MY_PERMISSIONS_REQUEST_lOCATION=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nro_certificado);
        btncertificado=findViewById(R.id.btncertificado);
        etcertificado = findViewById(R.id.etcertificado);
        prefs = new SessionPreferences(getApplicationContext());
        mLayout = findViewById(R.id.main_layout_certificado);
        btncertificado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(NroCertificadoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has not been granted.
                    requestLocationPermission();

                }else{
                    pdDialog = ProgressDialog.show(NroCertificadoActivity.this, "Enviando los datos", "Espere por favor...", true, false);
                    if (validar()) {
                        LocationManager locationManager = (LocationManager) NroCertificadoActivity.this.getSystemService(Context.LOCATION_SERVICE);
                        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            getLocation();
                            id_user = prefs.getUsuario().getId();
                            id_persona = prefs.getITV().getId_persona();
                            id_vehiculo = prefs.getITV().getId_vehiculo();
                            id_punto_inspeccion = prefs.getpuntoItv().getId_punto_inspeccion();
                            //pdDialog.dismiss();
                            Log.d("localizacion", "coordenadsa pendejo" + latitud +" "+ longitud);
                            certificado=etcertificado.getText().toString();
                            EnviarITV(0, id_user, id_punto_inspeccion, id_persona, id_vehiculo, "",certificado, "", latitud, longitud);
                        } else {
                            pdDialog.dismiss();
                            AlertGPSApagado();
                        }



                    } else {
                        pdDialog.dismiss();
                    }

                }

            }
        });


    }
    private void EnviarITV(int status, int id_user, int id_punto_inspeccion, long id_persona, long id_vehiculo, String mensaje, String nro_certificado, String key_inspeccion, double latitud, double longitud) {
        Inspeccion _objregistroITV = new Inspeccion(status, id_user, id_punto_inspeccion, id_persona, id_vehiculo, mensaje, nro_certificado, key_inspeccion, latitud, longitud);
        Call<Inspeccion> callRegistroITV = Api.getApi().RegistrarInspeccion(_objregistroITV);
        callRegistroITV.enqueue(new Callback<Inspeccion>() {
            @Override
            public void onResponse(Call<Inspeccion> call, Response<Inspeccion> response) {
                pdDialog.dismiss();
                Log.d("pruebaStatus", "mensaje" + response.body().getStatus());
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        toast(response.body().getMensaje());
                        prefs.finishITV();
                        Intent intent = new Intent(NroCertificadoActivity.this, ImprimirActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("key", response.body().getKey_inspeccion());
                        startActivity(intent);
                    } else if (response.body().getStatus() == 601) {
                        toast("El numero de certificado ya fue utilizado");
                       pdDialog = ProgressDialog.show(NroCertificadoActivity.this, "Obteniendo Datos de Certificado registrado", "Espere por favor...", true, false);
                       buscarCertificado(certificado);
                    } else if (response.body().getStatus() == 604) {
                        toast("El Servicio Web del RUAT ha fallado intente de nuevo");

                    }
                }

            }

            @Override
            public void onFailure(Call<Inspeccion> call, Throwable t) {
                pdDialog.dismiss();
                Log.d("ERROR", "Falla" + t);
                toast("Falla de comunicacion con el Servidor");
            }
        });
    }
    private void buscarCertificado(String nrocertificado){
        Call<datocertificado> callgetdatoscertificado= Api.getApi().getdatoscertificado(nrocertificado);
        pdDialog.dismiss();
        callgetdatoscertificado.enqueue(new Callback<datocertificado>() {
            @Override
            public void onResponse(Call<datocertificado> call, Response<datocertificado> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus()==605){
                        BuscarCertificadoDialog buscarCertificadoDialog=new BuscarCertificadoDialog().newInstance(response.body().getPlaca(),response.body().getCelular(),response.body().getFecha(),response.body().getNro_certificado(),response.body().getFuncionario(),response.body().getHora());
                        buscarCertificadoDialog.show(getSupportFragmentManager(),"Dialog Buscar");

                    }
                }


            }

            @Override
            public void onFailure(Call<datocertificado> call, Throwable t) {
                Log.d("ERROR","Falla"+ t);
                toast("Falla al obtener datos de certificado");
            }
        });
    }

    private void toast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();

    }
    private boolean validar() {
        boolean valido = true;
        String certificado = etcertificado.getText().toString();
        if (certificado.isEmpty()) {
            valido = false;
            etcertificado.setError("El numero de certificado es requerido");
        } else {
            if (certificado.length() < 7) {
                valido = false;
                etcertificado.setError("ingrese los siete digitos del certificado");
            } else {
                etcertificado.setError(null);
            }

        }
        return valido;
    }
    private void requestLocationPermission() {
        //Log.i(TAG, "CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.

            Snackbar.make(mLayout, R.string.permission_gps_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(NroCertificadoActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_lOCATION);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_lOCATION);
        }
        // END_INCLUDE(camera_permission_request)
    }
    protected void getLocation() {

        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        bestProvider = String.valueOf(locManager.getBestProvider(criteria, true)).toString();

        //You can still do this if you like, you might get lucky:
        int permissionCheck = ContextCompat.checkSelfPermission(NroCertificadoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        Location location = locManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            Log.e("TAG", "GPS is on");
            latitud = location.getLatitude();
            longitud = location.getLongitude();


            //Toast.makeText(MainActivity.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
            //searchNearestPlace(voice2text);
        } else {
            //This is what you need:
            locManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            //locManager.requestLocationUpdates(bestProvider,0,1000,);
        }

    }
    private void  AlertGPSApagado(){
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Debe activar el GPS para continuar con el registro")
                .setCancelable(false)
                .setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                })
                .setNegativeButton("Cerrar App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prefs.finishITV();
                        prefs.cerrarSesion();
                        Intent intent = new Intent(NroCertificadoActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
        alert=builder.create();
        alert.show();

    }

    @Override
    public void onLocationChanged(Location location) {
        locManager.removeUpdates(this);

        //open the map:
        latitud = location.getLatitude();
        longitud = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
