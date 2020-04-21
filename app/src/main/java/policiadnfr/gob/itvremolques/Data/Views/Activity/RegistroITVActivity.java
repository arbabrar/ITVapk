package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.RegistroITV;
import policiadnfr.gob.itvremolques.Data.Model.Vehiculo;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.Data.Views.Fragment.dialogRobo;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroITVActivity extends AppCompatActivity {
    private SessionPreferences prefs;
    private Spinner spitipoplaca;
    private EditText etplaca, etcopia,etcrpva;
    private Button btnRegistroITV;
    private ProgressDialog pdDialog;
    ArrayAdapter adapter;
    private int id_user;
    private String tipo_placa,version;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroitv);
        spitipoplaca=findViewById(R.id.spitipoplaca);
        etplaca=findViewById(R.id.etplaca);
        etcrpva=findViewById(R.id.etcrpva);
        etcopia=findViewById(R.id.etcopia);
        context = this;
        prefs=new SessionPreferences(getApplicationContext());
        id_user=prefs.getUsuario().getId();
        adapter = new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.tipoplaca));
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spitipoplaca.setAdapter(adapter);
        spitipoplaca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo_placa= parent.getItemAtPosition(position).toString();
                toast(tipo_placa);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnRegistroITV=findViewById(R.id.btnRegistroITV);
        btnRegistroITV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialog= ProgressDialog.show(RegistroITVActivity.this,"Enviando Solicitud","Espere por favor...",true,false);
                if(validar()){
                    version=ObtenerVersionApk();
                    registrarITV(etplaca.getText().toString(),etcrpva.getText().toString(),tipo_placa,etcopia.getText().toString(),version, prefs.getUsuario().getNombres(),prefs.getpuntoItv().getDireccion(),prefs.getpuntoItv().getMunicipio());
                    //Log.d("tipoplaca","dato"+tipo_placa);
                }else{
                    pdDialog.dismiss();
                }
            }
        });
    }
    private boolean validar(){
        boolean valido=true;
        String placa=etplaca.getText().toString();
        if(placa.isEmpty()){
            valido=false;
            etplaca.setError("La placa es requerida");
        }else{
            etplaca.setError(null);
        }
        if(tipo_placa=="RUAT"){
            String crpva=etcrpva.getText().toString();
            String nro_copia=etcopia.getText().toString();
            if(crpva.isEmpty()){
                valido=false;
                etcrpva.setError("El numero CRPVA es requerido");
            }else{
                etcrpva.setError(null);
            }
            if(nro_copia.isEmpty()){
                valido=false;
                etcopia.setError("Ingrese el numero de copia");
            }else{
                etcopia.setError(null);
            }
        }


        return valido;
    }
    private void registrarITV(String placa,String crpva,String tipo_placa, String copia,String version,String nombre_fun,String punto_itv,String municipio){
        RegistroITV _objregistroITV=new RegistroITV(placa,crpva,tipo_placa,copia,version,nombre_fun,punto_itv,municipio,id_user);
        Call<Vehiculo> callRegistroITV= Api.getApi().getDatosTecnicos(_objregistroITV);
        callRegistroITV.enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                pdDialog.dismiss();
                // Log.d("pruebaStatus","mensaje"+response.body().getStatus() );
                if(response.isSuccessful()){
                    ;
                    if(response.body().getStatus()==605){
                        Intent intent= new Intent(RegistroITVActivity.this,RegistroVLActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("placa",response.body().getPlaca());
                        intent.putExtra("motor",response.body().getMotor());
                        intent.putExtra("servicio",response.body().getServicio());
                        intent.putExtra("chasis",response.body().getChasis());
                        intent.putExtra("fotografia",response.body().getFotografia());
                        intent.putExtra("id_vehiculo",response.body().getId_vehiculo());
                        startActivity(intent);
                    }else if(response.body().getStatus()==603){
                        new dialogRobo(RegistroITVActivity.this);


                    }else {
                        Log.d("prueba","mensaje"+response.body().getMensaje() );
                        toast(response.body().getMensaje());
                        etplaca.setText("");
                        etcrpva.setText("");
                        etcopia.setText("");
                    }
                }


            }

            @Override
            public void onFailure(Call<Vehiculo> call, Throwable t) {
                pdDialog.dismiss();
                Log.d("ERROR","Falla"+ t);
                toast("Falla de comunicacion con el Servidor");
            }
        });
    }
    private String ObtenerVersionApk()
    {
        PackageManager m =context.getPackageManager();
        String app_ver = "";
        try {
            app_ver = m.getPackageInfo(context.getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            throw new AssertionError();
        }
        return app_ver;
    }
    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

    }
}
