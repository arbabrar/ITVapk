package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
import policiadnfr.gob.itvremolques.R;

public class CodigoVerificaActivity extends AppCompatActivity {
    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar pgBar;
    private EditText etCodigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_verifica);
        pgBar=findViewById(R.id.pBCodigo);
        etCodigo= findViewById(R.id.etCodigo);
        String Nrotelefono=getIntent().getStringExtra("nrotelefono");
        mAuth=FirebaseAuth.getInstance();

        sendVerificationCode(Nrotelefono);
    }
    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

    }
    private void verifyCode(String code){

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(CodigoVerificaActivity.this,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            Log.d("mensajeError","error "+ task.getException().getMessage().toString());
                            toast(task.getException().getMessage());
                        }
                    }
                });
    }

    private void sendVerificationCode(String numero){
        pgBar.setVisibility(View.VISIBLE);
        toast("Codigo de verificacion enviado al numero "+ numero);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                numero,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;
            toast("el codigo ha sido enviado "+ verificationId);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null){
                etCodigo.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            pgBar.setVisibility(View.GONE);
            Log.d("mensajeError11","error "+ e.getMessage());
            toast(e.getMessage());

        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            toast("OnCodeAutoRetrievalTimeOUt "+ s);
        }
    };

}
