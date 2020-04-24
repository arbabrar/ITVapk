package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.FotoVehiculo;
import policiadnfr.gob.itvremolques.Data.Model.Vehiculo;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FotografiaVehiculoActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback  {
    public int id_user;
    public long id_vehiculo;
    public String imagen, fotografia, urlFoto;
    private ImageView imfotovl;
    public static String fotobitmap;
    Bitmap imageBitmap;
    Button btnenviarfoto;
    Boolean existe_foto,foto_capture;
    ImageButton iBtomarfoto;
    private SessionPreferences prefs;
    private ProgressDialog pdDialog;
    private View mLayout;
    private static final int REQUEST_CAMERA = 0;
    private final static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static String[] PERMISOS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs=new SessionPreferences(getApplicationContext());
        setContentView(R.layout.activity_fotografia_vehiculo);
        iBtomarfoto=findViewById(R.id.iBFotoVl);
        imfotovl=findViewById(R.id.ivFotoVl);
        btnenviarfoto=findViewById(R.id.btnenviarfoto);
        iBtomarfoto=findViewById(R.id.iBFotoVl);
        mLayout = findViewById(R.id.sample_main_layout);
        Intent intent=getIntent();
        id_vehiculo=intent.getIntExtra("id_vehiculo",0);
        fotografia=intent.getStringExtra("fotografia");
        existe_foto=false;
        foto_capture=false;
        if(!fotografia.equals("0x0")){
            urlFoto="http://192.168.1.104/ApiAndroid/public/inspeccion/vehiculo/"+fotografia;
            Picasso.get().load(urlFoto).fit().into(imfotovl);
            existe_foto=true;
        }/*else{
            urlFoto="http://192.168.1.104/ApiAndroid/public/imagenes/imag.jpg";
        }*/
        iBtomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(FotografiaVehiculoActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has not been granted.
                    requestCameraPermission();

                } else {
                    tomarfoto(v);
                }


            }
        });

        btnenviarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validar()==0){
                   id_user=prefs.getUsuario().getId();
                   SubirFoto(id_user,id_vehiculo,fotobitmap);
               }else if(validar()==1){
                   tomarfoto(v);
               }else if(validar()==2){
                   Intent intent= new Intent(FotografiaVehiculoActivity.this,DatoPersonaActivity.class);
                   //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
               }
            }
        });


    }
    private int validar(){
        int valido = 0;
        if (existe_foto) {
            if (!foto_capture) {
                toast("Enviamos la foto ahora");
                //enviamos fotografia actualizando la anterior
                valido = 0;
            } else {
                //continuamos sin sacar fotografia puesto que esta ya exite
                valido = 2;
            }
        } else {
            if (!foto_capture) {
                //no se saco fotografia y esta no tiene una imagen valida
                toast("Saque la fotografia del vehiculo");
                valido = 1;
            } else {
                //continuamos y enviamos la fotografia
                toast("Enviamos la foto");
                valido = 0;
            }

        }
        return valido;
    }

    private void requestCameraPermission() {
        //Log.i(TAG, "CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
       // For example if the user has previously denied the permission.

            Snackbar.make(mLayout, R.string.permission_camera_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(FotografiaVehiculoActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
        // END_INCLUDE(camera_permission_request)
    }
    static final int REQUEST_TAKE_PHOTO = 1;

    private void tomarfoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "policiadnfr.gob.itvremolques.fileprovider",
                        photoFile);


                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Bkup" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            fotobitmap=convertirImagetoString(imageBitmap);
            imfotovl.setImageBitmap(imageBitmap);
            foto_capture=true;

        }
    }
    private String convertirImagetoString(Bitmap bitmap){
        float anchoNuevo=600;
        float altoNuevo=400;
        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();
        if(ancho>anchoNuevo || alto>altoNuevo){
            float escalaAncho=anchoNuevo/ancho;
            float escalaAlto=altoNuevo/alto;
            Matrix matriz = new Matrix();
            matriz.postScale(escalaAncho,escalaAlto);
            bitmap=Bitmap.createBitmap(bitmap,0,0,ancho,alto,matriz,false);

        }

        ByteArrayOutputStream array= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String cadena= Base64.encodeToString(imagenByte,Base64.DEFAULT);
        return cadena;

    }
    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

    }
    private void SubirFoto(int id_user,long id_vehiculo,String imagen){
        FotoVehiculo _objrSubirFoto=new FotoVehiculo(id_user,id_vehiculo,imagen);
        Call<Vehiculo> callUploadVehiculo= Api.getApi().subirVehiculo(_objrSubirFoto);
        callUploadVehiculo.enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                pdDialog.dismiss();
                // Log.d("pruebaStatus","mensaje"+response.body().getStatus() );
                if(response.isSuccessful()){
                    ;
                    if(response.body().getStatus()==605){
                        Intent intent= new Intent(FotografiaVehiculoActivity.this,DatoPersonaActivity.class);
                        startActivity(intent);
                    }else {
                        Log.d("prueba","mensaje"+response.body().getMensaje() );
                        toast(response.body().getMensaje());

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
