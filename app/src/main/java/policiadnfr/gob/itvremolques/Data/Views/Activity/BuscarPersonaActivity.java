package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.datosPersona;
import policiadnfr.gob.itvremolques.Data.Model.persona;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarPersonaActivity extends AppCompatActivity {
    Button btnbuscarPersona;
    EditText etnrocedula,etcomplemento;
    String complemento;
    long id_persona;
    private ProgressDialog pdDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_persona);
        btnbuscarPersona=findViewById(R.id.btnBuscarPersona);
        etnrocedula=findViewById(R.id.etnrocedula);
        etcomplemento=findViewById(R.id.etcomplementocedula);
        btnbuscarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                    pdDialog=ProgressDialog.show(BuscarPersonaActivity.this,"Consultando Registros","Espere por favor...",true,false);
                    buscarPersona(etnrocedula.getText().toString(),complemento);
                }
            }
        });

    }
    private boolean validar(){
        boolean valido=true;
        String nrocedula=etnrocedula.getText().toString();
        complemento=etcomplemento.getText().toString();
        if(nrocedula.isEmpty()){
            valido=false;
            etnrocedula.setError("El numero de docunento es requerido");
        }else if (nrocedula.length()<3){
            valido=false;
            etnrocedula.setError("El numero de docunento debe ser mayor a tres digitos");
        }
        if(complemento.length()>3){
            valido=false;
            etcomplemento.setError("El complemento no debe ser mayor a tres digitos");;
        }
        if(complemento.isEmpty()){
            complemento="1";
        }
       return valido;
    }
     private void toast(String mensaje) {
         Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
     }
     private void buscarPersona(String nrocedula,String complemento){
         Call<datosPersona> calldatopersona = Api.getApi().getPersona(nrocedula,complemento);
         calldatopersona.enqueue(new Callback<datosPersona>() {
             @Override
             public void onResponse(Call<datosPersona> call, Response<datosPersona> response) {

                                if(response.body().getStatus()==200){
                                    pdDialog.dismiss();
                                    id_persona=response.body().getId_persona();
                                    Log.d("tagid","el id es "+id_persona);
                                    Intent intent= new Intent(BuscarPersonaActivity.this,RegistroPersona.class);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("id_persona",id_persona);
                                    //intent.putExtra("id_vehiculo",response.body().getId_vehiculo());
                                    //prefs.initITV(response.body().getId_vehiculo(),response.body().getId_persona());
                                    startActivity(intent);
                                }
             }

             @Override
             public void onFailure(Call<datosPersona> call, Throwable t) {
                   toast("Falla de conexion al servidor");
             }
         });
    }
}

