package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.BD.BDHelper;
import policiadnfr.gob.itvremolques.Data.Model.LoginBody;
import policiadnfr.gob.itvremolques.Data.Model.User;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {
    private long startTime=50000;
    private final long interval = 1000;
    private CountDownTimer countDownTimer;
    private EditText etPassword, etusername;
    private Button btnlogin;
    private ProgressDialog pdDialog;
    private SessionPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        prefs = new SessionPreferences(getApplicationContext());
        etusername = findViewById(R.id.etusername);
        etPassword = findViewById(R.id.etPassword);
        btnlogin = findViewById(R.id.btnlogin);
        validaPermisos();
        Inicializar();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdDialog = ProgressDialog.show(LoginActivity.this, "Iniciando Sesion", "Verificando credenciales", true, false);
                if (validar()) {
                   login(etusername.getText().toString(), etPassword.getText().toString());
                } else {
                    pdDialog.dismiss();
                }


            }
        });

    }
    private void Inicializar(){
        pdDialog=ProgressDialog.show(LoginActivity.this,"Iniciando servicios","Espere por favor",true,false);
        if(RevisarBD()){
            llenarpaises();
            llenardepartamentos();
            llenarprovincias();
            llenarmunicipios();
            llenarexpedidos();
            llenarcategorialicencia();
            llenarestadocivil();
            llenartipodoc();
        }else{
            toast("Sistemas inicializado correctamente");
        }
        pdDialog.dismiss();
    }
    private boolean validaPermisos() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
           // toast("aca llegue");
            return true;

        }
        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

            return true;
        } else {

            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) || shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                cargarDilogoRecomendacion();
            } else {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 100);
            }
        }
        //toast("aca llegue");
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                btnlogin.setEnabled(true);
            } else {
                solicitarPermisosMAnual();
            }
        }
    }

    private void solicitarPermisosMAnual() {
        final CharSequence[] opciones = {"Si", "No"};
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(LoginActivity.this);
        dialogo.setTitle("Establecer los permisosos manualmente?");
        dialogo.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("Si")) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } else {
                    dialog.dismiss();
                    toast("Permisos denegados");
                }
            }
        });

    }

    private void cargarDilogoRecomendacion() {
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(LoginActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para continuar trabajando con la App");
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION}, 100);
            }
            // dialogo.show();
        });
    }
    private void llenarpaises(){
        String[] textopais=leerpaises();
        BDHelper baseHelper=new BDHelper(this,"bdbrifavio",null,1);
        SQLiteDatabase db=baseHelper.getReadableDatabase();
        db.beginTransaction();

        for(int i=0;i<textopais.length;i++){
            String[] linea = textopais[i].split(";");
            ContentValues contentValues =new ContentValues();
            contentValues.put("id_pais",linea[0]);
            contentValues.put("nombre",linea[1]);
            db.insert("pais",null,contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }
    private void llenardepartamentos(){
        String[] texto=leerdepartamentos();
        BDHelper baseHelper=new BDHelper(this,"bdbrifavio",null,1);
        SQLiteDatabase db=baseHelper.getReadableDatabase();
        db.beginTransaction();

        for(int i=0;i<texto.length;i++){
            String[] linea = texto[i].split(";");
            ContentValues contentValues =new ContentValues();
            contentValues.put("id_departamento",linea[0]);
            contentValues.put("nombre",linea[1]);
            contentValues.put("abreviacion",linea[2]);
            db.insert("departamento",null,contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }
    private void llenarprovincias(){
        String[] texto=leerprovincias();
        BDHelper baseHelper=new BDHelper(this,"bdbrifavio",null,1);
        SQLiteDatabase db=baseHelper.getReadableDatabase();
        db.beginTransaction();

        for(int i=0;i<texto.length;i++){
            String[] linea = texto[i].split(";");
            ContentValues contentValues =new ContentValues();
            contentValues.put("id_provincia",linea[0]);
            contentValues.put("id_departamento",linea[1]);
            contentValues.put("nombre",linea[2]);
            db.insert("provincia",null,contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }
    private void llenarmunicipios(){
        String[] texto=leermunicipios();
        BDHelper baseHelper=new BDHelper(this,"bdbrifavio",null,1);
        SQLiteDatabase db=baseHelper.getReadableDatabase();
        db.beginTransaction();

        for(int i=0;i<texto.length;i++){
            String[] linea = texto[i].split(";");
            ContentValues contentValues =new ContentValues();
            contentValues.put("id_municipio",linea[0]);
            contentValues.put("id_provincia",linea[1]);
            contentValues.put("nombre",linea[2]);
            db.insert("municipio",null,contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }
    private void llenarexpedidos(){
        String[] texto=leerexpedidos();
        BDHelper baseHelper=new BDHelper(this,"bdbrifavio",null,1);
        SQLiteDatabase db=baseHelper.getReadableDatabase();
        db.beginTransaction();

        for(int i=0;i<texto.length;i++){
            String[] linea = texto[i].split(";");
            ContentValues contentValues =new ContentValues();
            contentValues.put("id_expedido",linea[0]);
            contentValues.put("nombre",linea[1]);
            db.insert("expedido",null,contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }
    private void llenarcategorialicencia(){
        String[] texto=leercategorialicencia();
        BDHelper baseHelper=new BDHelper(this,"bdbrifavio",null,1);
        SQLiteDatabase db=baseHelper.getReadableDatabase();
        db.beginTransaction();

        for(int i=0;i<texto.length;i++){
            String[] linea = texto[i].split(";");
            ContentValues contentValues =new ContentValues();
            contentValues.put("id_categoria_licencia",linea[0]);
            contentValues.put("nombre",linea[1]);
            db.insert("categoria_licencia",null,contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }
    private void llenarestadocivil(){
        String[] texto=leerestadocivil();
        BDHelper baseHelper=new BDHelper(this,"bdbrifavio",null,1);
        SQLiteDatabase db=baseHelper.getReadableDatabase();
        db.beginTransaction();

        for(int i=0;i<texto.length;i++){
            String[] linea = texto[i].split(";");
            ContentValues contentValues =new ContentValues();
            contentValues.put("id_estado_civil",linea[0]);
            contentValues.put("nombre",linea[1]);
            db.insert("estado_civil",null,contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }
    private void llenartipodoc(){
        String[] texto=leertipodocumento();
        BDHelper baseHelper=new BDHelper(this,"bdbrifavio",null,1);
        SQLiteDatabase db=baseHelper.getReadableDatabase();
        db.beginTransaction();

        for(int i=0;i<texto.length;i++){
            String[] linea = texto[i].split(";");
            ContentValues contentValues =new ContentValues();
            contentValues.put("id_tipo_documento",linea[0]);
            contentValues.put("nombre",linea[2]);
            db.insert("tipo_documento",null,contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    private boolean RevisarBD(){
        boolean revisor=false;
        BDHelper baseHelper=new BDHelper(this,"bdbrifavio",null,1);
        SQLiteDatabase db=baseHelper.getReadableDatabase();
        long paises = DatabaseUtils.queryNumEntries(db,"pais");
        long provincias = DatabaseUtils.queryNumEntries(db,"provincia");
        long departamentos = DatabaseUtils.queryNumEntries(db,"departamento");
        long municipios = DatabaseUtils.queryNumEntries(db,"municipio");
        long expedido = DatabaseUtils.queryNumEntries(db,"expedido");
        db.close();
        if((paises==0)|| (departamentos==0) || (provincias==0) || (municipios==0) || (expedido==0)){
            // Log.d("datos","Las tablas estan vacias");
            revisor=true;
        }
        return revisor;
    }
    private String[]leerpaises(){

        InputStream inputStream=getResources().openRawResource(R.raw.pais);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try{
            int i =inputStream.read();
            while(i!=-1){
                byteArrayOutputStream.write(i);
                i=inputStream.read();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");

    }
    private String[]leerdepartamentos(){

        InputStream inputStream=getResources().openRawResource(R.raw.departamentos);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try{
            int i =inputStream.read();
            while(i!=-1){
                byteArrayOutputStream.write(i);
                i=inputStream.read();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");

    }
    private String[]leerprovincias(){

        InputStream inputStream=getResources().openRawResource(R.raw.provincias);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try{
            int i =inputStream.read();
            while(i!=-1){
                byteArrayOutputStream.write(i);
                i=inputStream.read();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");

    }
    private String[]leermunicipios(){

        InputStream inputStream=getResources().openRawResource(R.raw.municipios);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try{
            int i =inputStream.read();
            while(i!=-1){
                byteArrayOutputStream.write(i);
                i=inputStream.read();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");

    }
    private String[]leerexpedidos(){

        InputStream inputStream=getResources().openRawResource(R.raw.expedidos);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try{
            int i =inputStream.read();
            while(i!=-1){
                byteArrayOutputStream.write(i);
                i=inputStream.read();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");

    }
    private String[]leerestadocivil(){

        InputStream inputStream=getResources().openRawResource(R.raw.estado_civils);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try{
            int i =inputStream.read();
            while(i!=-1){
                byteArrayOutputStream.write(i);
                i=inputStream.read();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");

    }
    private String[]leercategorialicencia(){

        InputStream inputStream=getResources().openRawResource(R.raw.categoria_licencias);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try{
            int i =inputStream.read();
            while(i!=-1){
                byteArrayOutputStream.write(i);
                i=inputStream.read();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");

    }
    private String[]leertipodocumento(){

        InputStream inputStream=getResources().openRawResource(R.raw.tipo_documentos);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try{
            int i =inputStream.read();
            while(i!=-1){
                byteArrayOutputStream.write(i);
                i=inputStream.read();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");

    }
    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            if(!prefs.estalogueado()){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(LoginActivity.this,VerificacionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // toast("APlicacion ondestroy");
        if(FirebaseAuth.getInstance().getCurrentUser()!=null ){
            FirebaseAuth.getInstance().signOut();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!prefs.estalogueado()){
            FirebaseAuth.getInstance().signOut();
            //toast("Las credenciales han caducado...");
        }

    }

    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

    }
    private boolean validar(){
        boolean valido=true;
        String username=etusername.getText().toString();
        String password=etPassword.getText().toString();
        if(username.isEmpty()){
            valido=false;
            etusername.setError("El dato es requerido");
        }else{
            etusername.setError(null);
        }
        if(password.isEmpty()){
            valido=false;
            etPassword.setError("El password es requerido");
        }else{
            etPassword.setError(null);
        }
        return valido;
    }
    private void login(String username,String password){

        LoginBody _objLogin=new LoginBody(username,password);
        Call<User> callLogin= Api.getApi().login(_objLogin);
        callLogin.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                pdDialog.dismiss();
                if(response.isSuccessful()){
                    if(response.body().getMensaje()==200){
                        toast("Bienvenido al Sistema");
                        prefs.guardarUsuario(response.body());
                        Intent intent= new Intent(LoginActivity.this,DepartamentoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else if(response.body().getMensaje()==800){
                        toast("Usuario y/o contraseña incorrecto");
                        etusername.setText("");
                        etPassword.setText("");

                    }else if(response.body().getMensaje()==400){
                        toast("password erroneo" );
                        etPassword.setText("");
                    }else if(response.body().getMensaje()==402){
                        toast("Su usuario no tiene los permisos suficientes para el uso de esta aplicacion");
                        etPassword.setText("");
                        etusername.setText("");
                    }else if(response.body().getMensaje()==413){
                        toast("Su usuario de encuentra deshabilitado");
                        etPassword.setText("");
                        etusername.setText("");
                    }
                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pdDialog.dismiss();
                Log.d("ERROR","Falla  "+ t);
                toast("Falla de comunicacion con el Servidor");
            }
        });
    }
}
