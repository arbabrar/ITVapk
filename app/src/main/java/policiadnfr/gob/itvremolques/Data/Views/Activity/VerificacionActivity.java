package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.Celular;
import policiadnfr.gob.itvremolques.Data.Model.ResponseCheck;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificacionActivity extends AppCompatActivity {


    private EditText etTelefono;
    private Button btnVerificar;
    private Context context;
    private ProgressDialog pdDialog;
    private FirebaseAuth mAuth;
    String version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        context = this;
        etTelefono = findViewById(R.id.etTelefono);
        btnVerificar = findViewById(R.id.btnVerificar);
        mAuth = FirebaseAuth.getInstance();
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialog = ProgressDialog.show(VerificacionActivity.this, "Verificando informacion...", "Estamos verificando las credenciales de su equipo...", true, false);
                if (validar()) {
                    version = ObtenerVersionApk();
                    VerificaCelular(etTelefono.getText().toString(), version);
                } else {
                    pdDialog.dismiss();
                }
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
    private  void VerificaCelular(String Nrotelefono, String versionAPK){
        Celular _objCelular=new Celular(Nrotelefono,versionAPK);
        Call<ResponseCheck> callCheckPhone= Api.getApi().CheckPhone(_objCelular);
        callCheckPhone.enqueue(new Callback<ResponseCheck>() {
            @Override
            public void onResponse(Call<ResponseCheck> call, Response<ResponseCheck> response) {
                pdDialog.dismiss();
                if(response.isSuccessful()){
                    if(response.body().getStatus()==200){
                        Intent intent= new Intent(VerificacionActivity.this,CodigoVerificaActivity.class);
                        String Phone="+591"+response.body().getPhoneNumber();
                       // Log.d("Celular","El numero a verificar es  "+ codePhone);
                        intent.putExtra("nrotelefono",Phone);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }else if(response.body().getStatus()==201){
                        toast("El numero de telefono y el equipo no estan habilitados para el uso de esta aplicacicn");
                        etTelefono.setText("");

                    }else if(response.body().getStatus()==202){
                        toast(response.body().getMensaje());
                        etTelefono.setText("");

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseCheck> call, Throwable t) {
                pdDialog.dismiss();
                Log.d("ERROR","Falla  "+ t);
                toast("Falla de comunicacion con el Servidor");
            }
        });

    }
    private boolean validar(){
        boolean valido=true;
        String NroTelefono=etTelefono.getText().toString();
        if(NroTelefono.isEmpty()){
            valido=false;
            etTelefono.setError("El dato es requerido");
        }else if(NroTelefono.length()<8){
            valido=false;
            etTelefono.setError("El numero no debe tener menos de 8 numeros");
        }
        return valido;
    }
    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

    }

}
