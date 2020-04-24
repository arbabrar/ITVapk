package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.BD.BDHelper;
import policiadnfr.gob.itvremolques.Data.Model.RegistroVehiculo;
import policiadnfr.gob.itvremolques.Data.Model.Vehiculo;
import policiadnfr.gob.itvremolques.Data.Model.departamento;
import policiadnfr.gob.itvremolques.Data.Model.municipio;
import policiadnfr.gob.itvremolques.Data.Model.provincia;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.Data.Utils.Utilidades;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class RegistroVLActivity extends AppCompatActivity {
    private SessionPreferences prefs;
    private Spinner spidptorad,spiprovrad,spimunrad,spicombustible;
    private EditText etservicio,ettipovl,etmotor,etchasis,etplacaconfim;
    private Button btnRegistroVL;
    private String Intentplaca,Intentchasis,Intentmotor,radicatoria,combustible;
    private String departamentos,provincia,municipio,IntentFotografia;
    private int Intentid_vehiculo,id_user,idmunicipio,id_radicatoria;
    private ProgressDialog pdDialog;
    ArrayList<provincia> listaprovincia;
    ArrayList<municipio> listamunicipio;
    ArrayList<departamento>listadepartamento;
    BDHelper conn;
    ArrayAdapter adapterdepartamento,adaptercombustible,adapterprovincia,adaptermunicipio;
    ArrayList<String> departamentoSpinner,provinciaSpinner,municipioSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conn=new BDHelper(getApplicationContext(),"bdbrifavio",null,1);
        setContentView(R.layout.activity_registrovl);
        spicombustible=findViewById(R.id.spitipocombustible);
        spidptorad=findViewById(R.id.spiraddpto);
        spiprovrad=findViewById(R.id.spiradprov);
        spimunrad=findViewById(R.id.spiradmun);
        etservicio=findViewById(R.id.etservicio);
        ettipovl=findViewById(R.id.tipovl);
        etmotor=findViewById(R.id.etmotor);
        etchasis=findViewById(R.id.etchasis);
        etplacaconfim=findViewById(R.id.etplacaconfim);
        btnRegistroVL=findViewById(R.id.btnRegistroVL);
        adaptercombustible= new ArrayAdapter<>(this,R.layout.custom_spinner,getResources().getStringArray(R.array.tipocombustible));
        adaptercombustible.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spicombustible.setAdapter(adaptercombustible);
        Intent intent=getIntent();
        Intentid_vehiculo=intent.getIntExtra("id_vehiculo",0);
        Intentplaca=intent.getStringExtra("placa");
        Intentchasis=intent.getStringExtra("chasis");
        Intentmotor=intent.getStringExtra("motor");
        final String Intentservicio=intent.getStringExtra("servicio");
        IntentFotografia=intent.getStringExtra("fotografia");
        etservicio.setText(Intentservicio);
        etservicio.setEnabled(false);
        etchasis.setText(Intentchasis);
        etmotor.setText(Intentmotor);
        etmotor.setEnabled(false);
        prefs=new SessionPreferences(getApplicationContext());
        prefs.cleanReserva();
        id_user=prefs.getUsuario().getId();
        llenadepartamento();
        spicombustible.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                combustible= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spidptorad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //departamento=parent.getItemAtPosition(position).toString();
                String aux=parent.getItemAtPosition(position).toString();

                if(!aux.equals("Seleccione")){
                    SQLiteDatabase db=conn.getReadableDatabase();
                    String[] parametrodpto={parent.getItemAtPosition(position).toString()};
                    //String[] parametrodpto={"TARIJA"};
                    String[] campo={Utilidades.CAMPO_ID_DEPARTAMENTO};
                    Cursor cursor =db.query(Utilidades.TABLA_DEPARTAMENTO,campo,Utilidades.CAMPO_NOMBRE_DEPARTAMENTO+"=?",parametrodpto,null,null,null);
                    cursor.moveToFirst();
                    departamentos=cursor.getString(0);
                    cursor.close();
                    id_radicatoria=parseInt(departamentos);
                    llenaprovincia(departamentos);
                    Log.d("DPTO","Mira " +departamentos);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnRegistroVL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialog=ProgressDialog.show(RegistroVLActivity.this,"Enviando los datos","Espere por favor...",true,false);
                if(validar()){
                   registrarVehiculo(idmunicipio,id_radicatoria,ettipovl.getText().toString(),Intentid_vehiculo,id_user,combustible);
                }else{
                    pdDialog.dismiss();
                }
            }
        });
        spiprovrad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String aux=parent.getItemAtPosition(position).toString();
                //Log.d("datooo","msg"+aux);
                if(!aux.equals("") && !aux.equals("Seleccione")) {
                    SQLiteDatabase db = conn.getReadableDatabase();
                    String[] parametroprov = {parent.getItemAtPosition(position).toString()};
                    String[] campo = {Utilidades.CAMPO_ID_PROVINCIA};
                    Cursor cursor = db.query(Utilidades.TABLA_PROVINCIA, campo, Utilidades.CAMPO_NOMBRE_PROVINCIA + "=?", parametroprov, null, null, null);
                    cursor.moveToFirst();
                    provincia = cursor.getString(0);
                    cursor.close();
                    llenamunicipio(provincia);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spimunrad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                municipio=parent.getItemAtPosition(position).toString();
                if (!municipio.equals("Seleccione") && !municipio.equals("")){
                    SQLiteDatabase db = conn.getReadableDatabase();
                    String[] parametro = {municipio};
                    String[] campo = {Utilidades.CAMPO_ID_MUNICIPIO};
                    Cursor cursor = db.query(Utilidades.TABLA_MUNICIPIO, campo, Utilidades.CAMPO_NOMBRE_MUNICIPIO + "=?", parametro, null, null, null);
                    cursor.moveToFirst();
                    idmunicipio = cursor.getInt(0);
                    cursor.close();
                    Log.d("idmunicipio","hola "+ idmunicipio);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private boolean validar(){
        boolean valido=true;
        String placa=etplacaconfim.getText().toString();
        int id_municipio=idmunicipio;
        String chasis=etchasis.getText().toString();
        String tipovehiculo=ettipovl.getText().toString();


        if(placa.isEmpty()){
            valido=false;
            etplacaconfim.setError("La placa es requerida");
        }else{
            if (placa.equals(Intentplaca)){
                etplacaconfim.setError(null);
            }else{
                valido=false;
                etplacaconfim.setError("La placa ingresada es erronea");
            }

        }
        if(id_municipio==0){
            valido=false;
            toast("Seleccione el municipio de radicatoria");;
        }
        if(chasis.isEmpty()){
            valido=false;
            etchasis.setError("El Nro de chasis es requerido");
        }else{
            if (chasis.equals(Intentchasis)){
                etchasis.setError(null);
            }else{
                valido=false;
                etchasis.setError("Nro de chasis incorrecto");
            }

        }
        if(tipovehiculo.isEmpty()){
            valido=false;
            ettipovl.setError("El tipo de vehiculo es requerido");
        }else{
            ettipovl.setError(null);
        }

        if (combustible.equals("---Seleccione---")){
            valido=false;
            toast("Debe elegir el tipo de combustible");
        }

        return valido;
    }
    private void llenadepartamento() {
        SQLiteDatabase db=conn.getReadableDatabase();
        departamento departamento =null;
        listadepartamento=new ArrayList<departamento>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_DEPARTAMENTO,null);
        while (cursor.moveToNext()){
            departamento =new  departamento();
            departamento.setId_departamento(cursor.getInt(0));
            departamento.setNombre(cursor.getString(1));
            listadepartamento.add(departamento);
        }
        cursor.close();
        departamentoSpinner= new ArrayList<>();
        departamentoSpinner.add("Seleccione");
        for (int i=0; i<listadepartamento.size() ;i++){
            departamentoSpinner.add(listadepartamento.get(i).getNombre());
        }
        adapterdepartamento = new ArrayAdapter<String>(RegistroVLActivity.this,R.layout.custom_spinner,departamentoSpinner);
        adapterdepartamento.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spidptorad.setAdapter(adapterdepartamento);

    }
    private void llenaprovincia(String dpto) {
       // Log.d("provi1","datos: "+dpto);
        SQLiteDatabase db=conn.getReadableDatabase();
        provincia provincia=null;
        listaprovincia=new ArrayList<provincia>();
        String[] parametrodpto={dpto};
        String[] campo={Utilidades.CAMPO_NOMBRE_PROVINCIA};
        Cursor cursor =db.query(Utilidades.TABLA_PROVINCIA,campo,Utilidades.CAMPO_ID_DEPARTAMENTO+"=?",parametrodpto,null,null,null);
        while (cursor.moveToNext()){
            provincia =new provincia();
            provincia.setId_provincia(cursor.getInt(0));
            provincia.setNombre(cursor.getString(0));

            listaprovincia.add(provincia);
        }
        cursor.close();
        provinciaSpinner= new ArrayList<>();
        provinciaSpinner.add("Seleccione");
        // Log.d("prov","provicias"+ listaprovincia.size());
        for (int i=0; i<listaprovincia.size() ;i++){
            provinciaSpinner.add(listaprovincia.get(i).getNombre());
        }
        adapterprovincia = new ArrayAdapter<String>(RegistroVLActivity.this,R.layout.custom_spinner,provinciaSpinner);
        adapterprovincia.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spiprovrad.setAdapter(adapterprovincia);

    }
    private void llenamunicipio(String provincia) {
        //Log.d("provi","datos: "+provincia);
        SQLiteDatabase db=conn.getReadableDatabase();
        municipio municipio=null;
        listamunicipio=new ArrayList<municipio>();
        String[] parametroprov={provincia};
        String[] campo={Utilidades.CAMPO_NOMBRE_MUNICIPIO};
        Cursor cursor =db.query(Utilidades.TABLA_MUNICIPIO,campo,Utilidades.CAMPO_ID_PROVINCIA+"=?",parametroprov,null,null,null);

        while (cursor.moveToNext()){
            municipio =new municipio();
            municipio.setNombre(cursor.getString(0));
            listamunicipio.add(municipio);
        }
        cursor.close();
        municipioSpinner= new ArrayList<>();
        municipioSpinner.add("Seleccione");
        for (int i=0; i<listamunicipio.size() ;i++){
            municipioSpinner.add(listamunicipio.get(i).getNombre());
        }
        adaptermunicipio = new ArrayAdapter<String>(RegistroVLActivity.this,R.layout.custom_spinner, municipioSpinner);
        adaptermunicipio.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spimunrad.setAdapter( adaptermunicipio);
    }
    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

    }
    private void registrarVehiculo(int idmuni,int idradicatoria,String tipovehiculo,int id_vehiculo, int id_user, String combustible){
        RegistroVehiculo _objregistroVehiculo=new RegistroVehiculo(tipovehiculo,combustible,id_user,id_vehiculo,idmuni,idradicatoria);
        Log.d("Datos","pedjo "+ _objregistroVehiculo.getId_vehiculo());
        Call<Vehiculo> callRegistroVehiculo= Api.getApi().RegistroVehiculo(_objregistroVehiculo);
        callRegistroVehiculo.enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                pdDialog.dismiss();
                // Log.d("pruebaStatus","mensaje"+response.body().getStatus() );
                if(response.isSuccessful()){

                    if(response.body().getStatus()==605){
                        Intent intent= new Intent(RegistroVLActivity.this,FotografiaVehiculoActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("id_vehiculo",response.body().getId_vehiculo());
                        intent.putExtra("fotografia",IntentFotografia);
                        startActivity(intent);
                    }else {
                        Log.d("prueba","mensaje"+response.body().getMensaje() );
                        toast(response.body().getMensaje());
                        etchasis.setText("");
                        etmotor.setText("");
                        etplacaconfim.setText("");
                        ettipovl.setText("");
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

}
