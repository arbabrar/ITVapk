package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.BD.BDHelper;
import policiadnfr.gob.itvremolques.Data.Model.categorialicencia;
import policiadnfr.gob.itvremolques.Data.Model.datosPersona;
import policiadnfr.gob.itvremolques.Data.Model.departamento;
import policiadnfr.gob.itvremolques.Data.Model.estado_civil;
import policiadnfr.gob.itvremolques.Data.Model.expedido;
import policiadnfr.gob.itvremolques.Data.Model.municipio;
import policiadnfr.gob.itvremolques.Data.Model.pais;
import policiadnfr.gob.itvremolques.Data.Model.persona;
import policiadnfr.gob.itvremolques.Data.Model.provincia;
import policiadnfr.gob.itvremolques.Data.Model.tipodocumento;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.Data.Utils.Utilidades;
import policiadnfr.gob.itvremolques.Data.Views.Fragment.DatePickerFragment;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroPersona extends AppCompatActivity implements View.OnClickListener {
    private SessionPreferences prefs;
    private Spinner spisexo,spipais,spidepartamento,spiprovincia,spimunicipio,spitipodocumento,spiexpedido,spilicencia,spiestadocivil;
    private EditText etnombre,etapellidopaterno,etapellidomaterno,etocupacion,etlocalidad,etnrodocumento,etcomplemento,etdomicilio,etfechanacimiento,etcelular,etemail;
    private Button btnRegistroPer;
    private String departamentos,provincia,sexo,paises,municipio,tipodocumento,expedido,licencia,estadocivil;
    BDHelper conn;
    policiadnfr.gob.itvremolques.Data.Model.persona persona;
    private long Intentid_persona,id_user,id_vehiculo;
    private int idpais,idmunicipio,idestadocivil,idcatlic,idtipdoc,idexpedido;
    private ProgressDialog pdDialog;
    ArrayList<estado_civil> listaestadocivil;
    ArrayList<categorialicencia> listacategorialicencia;
    ArrayList<policiadnfr.gob.itvremolques.Data.Model.tipodocumento> listatipodocumento;
    ArrayList<pais> listapaises;
    ArrayList<policiadnfr.gob.itvremolques.Data.Model.provincia> listaprovincia;
    ArrayList<policiadnfr.gob.itvremolques.Data.Model.municipio> listamunicipio;
    ArrayList<policiadnfr.gob.itvremolques.Data.Model.expedido> listaexpedido;
    ArrayList<departamento>listadepartamento;
    ArrayList<String> PaisesSpinner,provinciaSpinner,municipioSpinner, expedidoSpinner,departamentoSpinner,estadocivilSpinner,tipodocSpinner,catlicSpinner;
    ArrayAdapter adaptersexo,adapterpais,adapterdepartamento,adapterprovincia,adaptermunicipio,adaptertipodocumento,adapterexpedido,adapterlicencia,adapterestadocivil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);
        conn=new BDHelper(getApplicationContext(),"bdbrifavio",null,1);
        prefs=new SessionPreferences(getApplicationContext());
        id_user=prefs.getUsuario().getId();
        spisexo=findViewById(R.id.spisexo);
        spipais=findViewById(R.id.spipais);
        spidepartamento=findViewById(R.id.spidepartamento);
        spiprovincia=findViewById(R.id.spiprovincia);
        spimunicipio=findViewById(R.id.spimunicipio);
        spitipodocumento=findViewById(R.id.spitipodocumento);
        spiexpedido= findViewById(R.id.spiexpedido);
        spilicencia=findViewById(R.id.spilicencia);
        spiestadocivil=findViewById(R.id.spiestadocivil);
        etnombre=findViewById(R.id.etnombre);
        etapellidopaterno=findViewById(R.id.etapellidopaterno);
        etapellidomaterno=findViewById(R.id.etapellidomaterno);
        etocupacion=findViewById(R.id.etocupacion);
        etlocalidad=findViewById(R.id.etlocalidad);
        etnrodocumento=findViewById(R.id.etnrodocumento);
        etcomplemento=findViewById(R.id.etcomplemento);
        etdomicilio=findViewById(R.id.etdomicilio);
        btnRegistroPer=findViewById(R.id.btnRegistroPer);
        etfechanacimiento=findViewById(R.id.etfechanacimiento);
        etemail=findViewById(R.id.etemail);
        etcelular=findViewById(R.id.etcelular);
        adaptersexo= new ArrayAdapter<>(this,R.layout.custom_spinner,getResources().getStringArray(R.array.sexo));
        adaptersexo.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spisexo.setAdapter(adaptersexo);
        spisexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexo= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etfechanacimiento.setOnClickListener(this);


        pdDialog=ProgressDialog.show(RegistroPersona.this,"Recopilando datos","Espere por favor...",true,false);

        llenarpaises();
        llenaexpedido();
        llenadepartamento();
        llenadestcivil();
        llenatipodoc();
        llenalicencia();
        Intent intent=getIntent();
        Intentid_persona=intent.getLongExtra("id_persona",0);
        Log.d("tagpersona","id_perona="+Intentid_persona);

        if(Intentid_persona!=0){
            toast("Persona registrada ");
            pdDialog=ProgressDialog.show(RegistroPersona.this,"Obteniendo datos","Espere por favor...",true,false);

           ObtenerdatosPersona(Intentid_persona);
        }else{
            toast("Ingrese los datos de la persona");
        }

        spipais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                paises=parent.getItemAtPosition(position).toString();
                if (!paises.equals("Seleccione")){
                    SQLiteDatabase db = conn.getReadableDatabase();
                    String[] parametro = {paises};
                    String[] campo = {Utilidades.CAMPO_IDPAIS};
                    Cursor cursor = db.query(Utilidades.TABLA_PAIS, campo, Utilidades.CAMPO_NOMBRE_PAIS + "=?", parametro, null, null, null);
                    cursor.moveToFirst();
                    idpais = cursor.getInt(0);
                    cursor.close();


                    //Log.d("ipais","hola "+ idpais);
                }

                String cadena="BOLIVIA";
                if(idpais==25){
                    spidepartamento.setEnabled(true);
                    spimunicipio.setEnabled(true);
                    spiprovincia.setEnabled(true);
                    etcomplemento.setEnabled(true);
                    spiexpedido.setEnabled(true);
                    Log.d("datopdjo","hola a todos");

                }else{
                    spidepartamento.setEnabled(false);
                    spimunicipio.setEnabled(false);
                    spiprovincia.setEnabled(false);
                    etcomplemento.setEnabled(false);
                    spiexpedido.setEnabled(false);
                    Log.d("datopdjo","msg"+paises);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spiexpedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expedido=parent.getItemAtPosition(position).toString();
                if (!expedido.equals("Seleccione") && !expedido.equals("")){
                    SQLiteDatabase db = conn.getReadableDatabase();
                    String[] parametro = {expedido};
                    String[] campo = {Utilidades.CAMPO_IDEXPEDIDO};
                    Cursor cursor = db.query(Utilidades.TABLA_EXPEDIDO, campo, Utilidades.CAMPO_NOMBRE_EXPEDIDO + "=?", parametro, null, null, null);
                    cursor.moveToFirst();
                    idexpedido = cursor.getInt(0);
                    cursor.close();
                    //Log.d("idmunicipio","hola "+ idmunicipio);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spilicencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                licencia=parent.getItemAtPosition(position).toString();
                if (!licencia.equals("Seleccione") && !licencia.equals("")){
                    SQLiteDatabase db = conn.getReadableDatabase();
                    String[] parametro = {licencia};
                    String[] campo = {Utilidades.CAMPO_IDCATLIC};
                    Cursor cursor = db.query(Utilidades.TABLA_CATLIC, campo, Utilidades.CAMPO_NOMBRE_CATLIC + "=?", parametro, null, null, null);
                    cursor.moveToFirst();
                    idcatlic = cursor.getInt(0);
                    cursor.close();
                    //Log.d("idmunicipio","hola "+ idmunicipio);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spitipodocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipodocumento=parent.getItemAtPosition(position).toString();
                if (!tipodocumento.equals("Seleccione") && !tipodocumento.equals("")){
                    SQLiteDatabase db = conn.getReadableDatabase();
                    String[] parametro = {tipodocumento};
                    String[] campo = {Utilidades.CAMPO_IDTIPODOC};
                    Cursor cursor = db.query(Utilidades.TABLA_TIPODOCUMENTO, campo, Utilidades.CAMPO_NOMBRE_DOC + "=?", parametro, null, null, null);
                    cursor.moveToFirst();
                    idtipdoc = cursor.getInt(0);
                    cursor.close();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spiestadocivil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadocivil=parent.getItemAtPosition(position).toString();
                if (!estadocivil.equals("Seleccione") && !estadocivil.equals("")){
                    SQLiteDatabase db = conn.getReadableDatabase();
                    String[] parametro = {estadocivil};
                    String[] campo = {Utilidades.CAMPO_IDESTCIVIL};
                    Cursor cursor = db.query(Utilidades.TABLA_ESTADOCIVIL, campo, Utilidades.CAMPO_NOMBRE_ESTCIVIL + "=?", parametro, null, null, null);
                    cursor.moveToFirst();
                    idestadocivil = cursor.getInt(0);
                    cursor.close();
                    //Log.d("idmunicipio","hola "+ idmunicipio);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spidepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    llenaprovincia(departamentos);
                    //Log.d("DPTO","Mira " +departamentos);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spiprovincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String aux=parent.getItemAtPosition(position).toString();
                Log.d("datooo","msg"+aux);
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
        spimunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    //Log.d("idmunicipio","hola "+ idmunicipio);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnRegistroPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                    pdDialog=ProgressDialog.show(RegistroPersona.this,"Recopilando datos","Espere por favor...",true,false);
                    registrarpersona(etnombre.getText().toString(),etapellidopaterno.getText().toString(),etapellidomaterno.getText().toString(),sexo,etocupacion.getText().toString(),idpais,idmunicipio,etlocalidad.getText().toString(),idtipdoc,etnrodocumento.getText().toString(),etcomplemento.getText().toString(),idexpedido,idcatlic,etdomicilio.getText().toString(),etfechanacimiento.getText().toString(),idestadocivil,etcelular.getText().toString(),etemail.getText().toString(),Intentid_persona,id_user);
                }
            }
        });
    }

    private void ObtenerdatosPersona(long id_persona) {
        Call<datosPersona> calldatopersona = Api.getApi().getDatoPersona(id_persona);
        calldatopersona.enqueue(new Callback<datosPersona>() {
            @Override
            public void onResponse(Call<datosPersona> call, Response<datosPersona> response) {
                if(response.isSuccessful()){
                    persona=response.body().getPersona();
                    etnombre.setText(persona.getNombre());
                    etapellidopaterno.setText(persona.getApellido_paterno());
                    etapellidomaterno.setText(persona.getApellido_materno());
                    etocupacion.setText(persona.getOcupacion());
                    etlocalidad.setText(persona.getLocalidad());
                    etnrodocumento.setText(persona.getNro_documento());
                    etcomplemento.setText(persona.getDocumento_complemento());
                    etdomicilio.setText(persona.getDomicilio());
                    etcelular.setText(persona.getCelular());
                    etemail.setText(persona.getEmail());
                    pdDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<datosPersona> call, Throwable t) {
                toast("Falla de conexion al servidor");
                pdDialog.dismiss();
            }
        });
    }


    private void llenamunicipio(String provincia) {
       // Log.d("provi","datos: "+provincia);
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
        adaptermunicipio = new ArrayAdapter<String>(RegistroPersona.this,R.layout.custom_spinner, municipioSpinner);
        adaptermunicipio.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spimunicipio.setAdapter( adaptermunicipio);
    }
    private void llenaprovincia(String dpto) {
        Log.d("provi1","datos: "+dpto);
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
        adapterprovincia = new ArrayAdapter<String>(RegistroPersona.this,R.layout.custom_spinner,provinciaSpinner);
        adapterprovincia.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spiprovincia.setAdapter(adapterprovincia);

    }
    private void llenarpaises() {
        SQLiteDatabase db=conn.getReadableDatabase();
        pais pais=null;
        listapaises=new ArrayList<pais>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_PAIS,null);
        while (cursor.moveToNext()){
            pais =new pais();
            pais.setId_pais(cursor.getInt(0));
            pais.setNombre(cursor.getString(1));
            listapaises.add(pais);
        }
        cursor.close();
        PaisesSpinner= new ArrayList<>();
        PaisesSpinner.add("Seleccione");
        for (int i=0; i<listapaises.size() ;i++){
            PaisesSpinner.add(listapaises.get(i).getNombre());
        }
        adapterpais = new ArrayAdapter<String>(RegistroPersona.this,R.layout.custom_spinner,PaisesSpinner);
        adapterpais.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spipais.setAdapter(adapterpais);

    }

    private void llenaexpedido() {
        SQLiteDatabase db=conn.getReadableDatabase();
        expedido expedido=null;
        listaexpedido=new ArrayList<expedido>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_EXPEDIDO,null);
        while (cursor.moveToNext()){
            expedido =new expedido();
            expedido.setId_expedido(cursor.getInt(0));
            expedido.setNombre(cursor.getString(1));
            listaexpedido.add(expedido);
        }
        cursor.close();
        expedidoSpinner= new ArrayList<>();
        expedidoSpinner.add("Seleccione");
        for (int i=0; i<listaexpedido.size() ;i++){
            expedidoSpinner.add(listaexpedido.get(i).getNombre());
        }
        adapterexpedido = new ArrayAdapter<String>(RegistroPersona.this,R.layout.custom_spinner,expedidoSpinner);
        adapterexpedido.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spiexpedido.setAdapter(adapterexpedido);
        // pdDialog.dismiss();
    }

    private void llenadepartamento() {
        SQLiteDatabase db=conn.getReadableDatabase();
        departamento departamento =null;
        listadepartamento=new ArrayList<departamento>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_DEPARTAMENTO,null);
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
        adapterdepartamento = new ArrayAdapter<String>(RegistroPersona.this,R.layout.custom_spinner,departamentoSpinner);
        adapterdepartamento.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spidepartamento.setAdapter(adapterdepartamento);

    }
    private void llenadestcivil() {
        SQLiteDatabase db=conn.getReadableDatabase();
        estado_civil estadocivil =null;
        listaestadocivil=new ArrayList<estado_civil>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_ESTADOCIVIL,null);
        while (cursor.moveToNext()){
            estadocivil =new  estado_civil();
            estadocivil.setId_estado_civil(cursor.getInt(0));
            estadocivil.setNombre(cursor.getString(1));
            listaestadocivil.add(estadocivil);
        }
        cursor.close();
        estadocivilSpinner= new ArrayList<>();
        estadocivilSpinner.add("Seleccione");
        for (int i=0; i<listaestadocivil.size() ;i++){
            estadocivilSpinner.add(listaestadocivil.get(i).getNombre());
        }
        adapterestadocivil = new ArrayAdapter<String>(RegistroPersona.this,R.layout.custom_spinner,estadocivilSpinner);
        adapterestadocivil.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spiestadocivil.setAdapter(adapterestadocivil);

    }

    private void llenatipodoc() {
        SQLiteDatabase db=conn.getReadableDatabase();
        tipodocumento tipodocumento =null;
        listatipodocumento=new ArrayList<tipodocumento>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_TIPODOCUMENTO,null);
        while (cursor.moveToNext()){
            tipodocumento =new tipodocumento();
            tipodocumento.setId_tipo_documento(cursor.getInt(0));
            tipodocumento.setNombre(cursor.getString(1));
            listatipodocumento.add(tipodocumento);
        }
        cursor.close();
        tipodocSpinner= new ArrayList<>();
        tipodocSpinner.add("Seleccione");
        for (int i=0; i<listatipodocumento.size() ;i++){
            tipodocSpinner.add(listatipodocumento.get(i).getNombre());
        }
        adaptertipodocumento = new ArrayAdapter<String>(RegistroPersona.this,R.layout.custom_spinner,tipodocSpinner);
        adaptertipodocumento.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spitipodocumento.setAdapter(adaptertipodocumento);

    }

    private void llenalicencia() {
        SQLiteDatabase db=conn.getReadableDatabase();
        categorialicencia categorialicencia =null;
        listacategorialicencia=new ArrayList<categorialicencia>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_CATLIC,null);
        while (cursor.moveToNext()){
            categorialicencia =new categorialicencia();
            categorialicencia.setId_categoria_licencia(cursor.getInt(0));
            categorialicencia.setNombre(cursor.getString(1));
            listacategorialicencia.add(categorialicencia);
        }
        cursor.close();
        catlicSpinner= new ArrayList<>();
        catlicSpinner.add("Seleccione");
        for (int i=0; i< listacategorialicencia.size() ;i++){
            catlicSpinner.add( listacategorialicencia.get(i).getNombre());
        }
        adapterlicencia = new ArrayAdapter<String>(RegistroPersona.this,R.layout.custom_spinner,catlicSpinner);
        adapterlicencia.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spilicencia.setAdapter(adapterlicencia);
        pdDialog.dismiss();
    }
    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
    }
    private boolean validar(){
        boolean valido=true;
        String nombre=etnombre.getText().toString();
        String apellidopaterno=etapellidopaterno.getText().toString();
        String ocupacion=etocupacion.getText().toString();
        String localidad=etlocalidad.getText().toString();
        String documento=etnrodocumento.getText().toString();
        String domicilio=etdomicilio.getText().toString();
        String fechanacimiento=etfechanacimiento.getText().toString();
        String nrofooo=etcelular.getText().toString();
        String email=etemail.getText().toString();

        if(!email.isEmpty()){
            if(!validarEmail(email)){
                valido=false;
                etemail.setError("introdce un correo electronico valido");
            }else{
                etemail.setError(null);
            }
        }


        if(fechanacimiento.isEmpty()){
            valido=false;
            etfechanacimiento.setError("La fecha de nacimieno es requerida");
        }else{
            etfechanacimiento.setError(null);
        }
        if(nrofooo.isEmpty()){
            valido=false;
            etcelular.setError("El numero de telefono es requerido");
        }else{
            etcelular.setError(null);
        }
        if(nombre.isEmpty()){
            valido=false;
            etnombre.setError("El nombre es requerido");
        }else{
            etnombre.setError(null);
        }
        if(domicilio.isEmpty()){
            valido=false;
            etdomicilio.setError("El domicilio es requerido");
        }else if(domicilio.length()<8){
            valido=false;
            etdomicilio.setError("El domicilio debe tener mas de 8 caracteres");
        }else{
            etdomicilio.setError(null);
        }
        if(documento.isEmpty()){
            valido=false;
            etnrodocumento.setError("El numero de documento es requerido");
        }else{
            etnrodocumento.setError(null);
        }
        if(localidad.isEmpty()){
            valido=false;
            etlocalidad.setError("Ingrese el lugar de nacimiento");
        }else{
            etlocalidad.setError(null);
        }
        if(apellidopaterno.isEmpty()){
            valido=false;
            etapellidopaterno.setError("El apellido paterno es requerido");
        }else{
            etapellidopaterno.setError(null);
        }
        if(sexo.equals("")){
            valido=false;
            toast("Escoja el sexo de la persona");

        }
        if(ocupacion.isEmpty()){
            valido=false;
            etocupacion.setError("La ocupacion es requerido");
        }else{
            etocupacion.setError(null);
        }
        if (paises.equals("")){
            valido=false;
            toast("Debe elegir la nacionalidad");
        }
        if (idpais==25){
            if(municipio.equals("Seleccione") || municipio.equals("")){
                valido=false;
                toast("Debe elegir la municipio");
            }
            if(expedido.equals("Seleccione") || expedido.equals("")){
                valido=false;
                toast("Debe elegir la expedidion del doumento");
            }
        }
        if(idpais==0){
            valido=false;
            toast("Debe elegir el pala  de la nacionaliad");
        }
        if(tipodocumento.equals("Seleccione") || tipodocumento.equals("")){
            valido=false;
            toast("Debe elegir el tipo de documento");
        }
        if(licencia.equals("Seleccione") || licencia.equals("")){
            valido=false;
            toast("Debe elegir la categoria de licencia");
        }
        if(estadocivil.equals("Seleccione") || estadocivil.equals("")){
            valido=false;
            toast("Debe elegir el estado civil");
        }

        return valido;
    }
    public boolean validarEmail(String email){
        Pattern pattern= Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etfechanacimiento:
                showDatePickerDialog();
                break;
        }
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = twoDigits(day) + "-" + twoDigits(month+1) + "-" + year;
                etfechanacimiento.setText(selectedDate);
            }
        });
        newFragment.show(RegistroPersona.this.getSupportFragmentManager(), "datePicker");
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
    private void registrarpersona(String nombre,String apellido_paterno,String apellido_materno,String sexo,String ocupacion,int id_pais,int id_municipio,String localidad,int id_tipo_documento,String nro_documento,String complemento,int id_expedido,int id_categoria_licencia,String domicilio,String fecha_nacimiento,int id_estado_civil,String celular,String email,long id_persona,long id_user){
        persona _objregistropersona=new persona(0,id_user,id_persona,0,id_pais,id_municipio,id_tipo_documento,id_expedido,id_categoria_licencia,id_estado_civil,"",nombre,apellido_paterno,apellido_materno,sexo,ocupacion,nro_documento,complemento,localidad,domicilio,celular,email,fecha_nacimiento,"");

        Call<persona> callRegistropersona= Api.getApi().Registropersona(_objregistropersona);
        callRegistropersona.enqueue(new Callback<persona>() {
            @Override
            public void onResponse(Call<persona> call, Response<persona> response) {

                // Log.d("pruebaStatus","mensaje"+response.body().getStatus() );
                if(response.isSuccessful()){

                    if(response.body().getStatus()==200){
                        pdDialog.dismiss();
                        //toast("Hasts aca llegamos bien");
                        Intent intent= new Intent(RegistroPersona.this,FotografiaPersonaActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("fotografia",response.body().getFotografia_per());
                        //intent.putExtra("id_vehiculo",response.body().getId_vehiculo());
                        id_vehiculo=prefs.getITV().getId_vehiculo();
                        prefs.initITV(id_vehiculo,response.body().getId_persona());
                        startActivity(intent);

                    }else {
                        Log.d("prueba","mensaje"+response.body().getMensaje() );
                        toast(response.body().getMensaje());
                        etnombre.setText("");
                        etapellidopaterno.setText("");
                        etapellidomaterno.setText("");
                        etnrodocumento.setText("");
                        etdomicilio.setText("");
                        etlocalidad.setText("");
                    }
                }


            }

            @Override
            public void onFailure(Call<persona> call, Throwable t) {
                pdDialog.dismiss();
                Log.d("ERROR","Falla"+ t);
                toast("Falla de comunicacion con el Servidor");
            }
        });
    }

}


