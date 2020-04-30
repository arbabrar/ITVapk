package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.DetalleItv;
import policiadnfr.gob.itvremolques.Data.Model.ItvID;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosInspeccionActivity extends AppCompatActivity {
    private SessionPreferences prefs;
    private Spinner spifreno,spicilindro,spiluces,spilucesstop,spilucesretro,spilucesparqueo,spiparabrisas,spivolante,spisuspension,spidireccion,spineumatico,spihumo,spiretrovisor,spicinturon,spividrios,spigata,spiruedaaux,spibotiquin,spiextintor;
    private Button btnRegistroDetalleITV;
    private String freno,cilindro,luces,stop,luzretro,luzparqueo,parabrisas,volante,suspension,direccion,neumatico,humo,retrovisor,cinturon,vidrios,gata,ruadaauxiliar,botiquin,extintor;
    private ProgressDialog pdDialog;
    private int id_user;
    private long id_vehiculo;
    ArrayAdapter adapterfreno,adaptercilindro,adapterluces,adapterstop,adapterluzretro,adapterluzparqueo,adapterparabrisas,adaptervolante,adaptersuspension,adapterdireccion,adapterneumatico,adapterhumo,adapterretrovisor,adaptercinturon,adaptervidrios,adaptergata,adapterruedaux,adapterbotiquin,adapterextinror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_inspeccion);
        spifreno=findViewById(R.id.spifrenomano);
        spicilindro=findViewById(R.id.spicilindro);
        spiluces=findViewById(R.id.spiluces);
        spilucesstop=findViewById(R.id.spilucesstop);
        spilucesretro=findViewById(R.id.spilucesretro);
        spilucesparqueo=findViewById(R.id.spilucesparqueo);
        spiparabrisas=findViewById(R.id.spiparabrisas);
        spivolante=findViewById(R.id.spivolante);
        spisuspension=findViewById(R.id.spisuspension);
        spidireccion=findViewById(R.id.spidireccion);
        spineumatico=findViewById(R.id.spineumaticos);
        spihumo=findViewById(R.id.spihumo);
        spiretrovisor=findViewById(R.id.spiretrovisor);
        spividrios=findViewById(R.id.spividrios);
        spigata=findViewById(R.id.spigata);
        spiruedaaux=findViewById(R.id.spiruedaaux);
        spibotiquin=findViewById(R.id.spibotiquin);
        spiextintor=findViewById(R.id.spiextintor);
        spicinturon=findViewById(R.id.spicinturon);
        prefs=new SessionPreferences(getApplicationContext());
        btnRegistroDetalleITV=findViewById(R.id.btnRegistroDetalleITV);
        btnRegistroDetalleITV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                    id_user=prefs.getUsuario().getId();
                    id_vehiculo=prefs.getITV().getId_vehiculo();
                    pdDialog= ProgressDialog.show(DatosInspeccionActivity.this,"Recopilando datos","Espere por favor...",true,false);
                   Log.d("dato","id "+id_vehiculo);
                    registrardetalle(freno,cilindro,luces,stop,luzretro,luzparqueo,parabrisas,volante,suspension,direccion,neumatico,humo,retrovisor,cinturon,vidrios,gata,ruadaauxiliar,botiquin,extintor,id_user,id_vehiculo);
                }
            }
        });
        adapterfreno=new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterfreno.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spifreno.setAdapter(adapterfreno);
        spifreno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                freno= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adaptercilindro=new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adaptercilindro.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spicilindro.setAdapter(adaptercilindro);
        spicilindro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cilindro= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterluces=new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterluces.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spiluces.setAdapter(adapterluces);
        spiluces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                luces= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterstop=new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterstop.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spilucesstop.setAdapter(adapterstop);
        spilucesstop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stop= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterluzretro=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterluzretro.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spilucesretro.setAdapter(adapterluzretro);
        spilucesretro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                luzretro= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterluzparqueo=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterluzparqueo.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spilucesparqueo.setAdapter(adapterluzparqueo);
        spilucesparqueo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                luzparqueo= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterparabrisas=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterparabrisas.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spiparabrisas.setAdapter(adapterparabrisas);
        spiparabrisas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parabrisas= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adaptervolante=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adaptervolante.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spivolante.setAdapter(adaptervolante);
        spivolante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                volante= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adaptersuspension=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adaptersuspension.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spisuspension.setAdapter(adaptersuspension);
        spisuspension.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                suspension= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterdireccion=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterdireccion.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spidireccion.setAdapter(adapterdireccion);
        spidireccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                direccion= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterneumatico=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterneumatico.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spineumatico.setAdapter(adapterneumatico);
        spineumatico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                neumatico= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterhumo=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterhumo.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spihumo.setAdapter(adapterhumo);
        spihumo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                humo= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterretrovisor=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adapterretrovisor.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spiretrovisor.setAdapter(adapterretrovisor);
        spiretrovisor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                retrovisor= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adaptercinturon=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.buenomalo));
        adaptercinturon.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spicinturon.setAdapter(adaptercinturon);
        spicinturon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cinturon= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adaptervidrios=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.tieneono));
        adaptervidrios.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spividrios.setAdapter(adaptervidrios);
        spividrios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vidrios= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adaptergata=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.prenopre));
        adaptergata.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spigata.setAdapter(adaptergata);
        spigata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gata= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterruedaux=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.tieneono));
        adapterruedaux.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spiruedaaux.setAdapter(adapterruedaux);
        spiruedaaux.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ruadaauxiliar= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterbotiquin=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.compincom));
        adapterbotiquin.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spibotiquin.setAdapter(adapterbotiquin);
        spibotiquin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                botiquin= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        adapterextinror=new ArrayAdapter<>(this,R.layout.custom_spinner, getResources().getStringArray(R.array.prenopre));
        adapterextinror.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spiextintor.setAdapter(adapterextinror);
        spiextintor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                extintor= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
    }


    private boolean validar(){
        boolean valido=true;
        if(freno.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de freno de mano");
        }
        if(cilindro.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de los cilindros");
        }
        if(luces.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de las luces");
        }
        if(stop.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de los stops");
        }
        if(luzretro.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de las luces de retro");
        }
        if(luzparqueo.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de las luces de parqueo");
        }
        if(parabrisas.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de las luces de os parabrisas");
        }
        if(volante.equals("Seleccione")){
            valido=false;
            toast("Defina el estado del juego del volante");
        }
        if(suspension.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de la suspension");
        }
        if(direccion.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de la direccion");
        }
        if (neumatico.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de la profundidad del nematico");
        }
        if(humo.equals("Seleccione")){
            valido=false;
            toast("Defina el estado del control de humo del escape");
        }
        if(retrovisor.equals("Seleccione") ){
            valido=false;
            toast("Defina el estado de los espejos retrovisores");
        }

        if(cinturon.equals("Seleccione")){
            valido=false;
            toast("Defina el estado de los cinturones de seguridad");
        }
        if(vidrios.equals("Seleccione")){
            valido=false;
            toast("Defina si el vehiculo porta vidrios polarizados");
        }
        if(gata.equals("Seleccione")){
            valido=false;
            toast("Defina si el propietario presento o no la gata y accesorios");
        }
        if(ruadaauxiliar.equals("Seleccione")){
            valido=false;
            toast("Defina si el vehiculo tiene o no rueda auxiliar");
        }
        if(botiquin.equals("Seleccione")){
            valido=false;
            toast("Defina si el vehiculo tiene o no botiquin");
        }
        if( extintor.equals("Seleccione")){
            valido=false;
            toast("Defina si el vehiculo tiene o no extintor");
        }

        return valido;
    }
    private void registrardetalle(String freno,String cilindro,String luces,String stop,String retro,String parqueo,String parabrisas,String volante,String suspension,String direccion,String neumatico,String humo,String retrovisor,String cinturon,String vidrios,String gata,String ruedaauxiliar,String botiquin,String extintor,int id_user,long id_vehiculo) {
        DetalleItv _objregistrodetalle=new DetalleItv(freno,cilindro,luces,stop,retro,parqueo,parabrisas,volante,suspension,direccion,neumatico,humo,retrovisor,cinturon,vidrios,gata,ruedaauxiliar,botiquin,extintor,id_user,id_vehiculo);

        Call<ItvID> callRegistrodetalleITV= Api.getApi().RegistrodetalleITV(_objregistrodetalle);
        callRegistrodetalleITV.enqueue(new Callback<ItvID>() {
            @Override
            public void onResponse(Call<ItvID> call, Response<ItvID> response) {

                //Log.d("pruebaStatus","mensaje"+response.body().getStatus() );
                if(response.isSuccessful()){

                    if(response.body().getStatus()==605){
                        // toast("Hasts aca llegamos bien");
                        pdDialog.dismiss();
                        Intent intent= new Intent(DatosInspeccionActivity.this,NroCertificadoActivity.class);
                        //prefs.initITV(response.body().getId_vehiculo(),response.body().getId_persona());
                        startActivity(intent);

                    }else {
                        Log.d("prueba","mensaje"+response.body().getMensaje() );
                        pdDialog.dismiss();
                        toast("Falla de conexion al servidor");

                    }
                }


            }

            @Override
            public void onFailure(Call<ItvID> call, Throwable t) {
                pdDialog.dismiss();
                Log.d("ERROR","Falla"+ t);
                toast("Falla de comunicacion con el Servidor");
            }
        });


    }

}
