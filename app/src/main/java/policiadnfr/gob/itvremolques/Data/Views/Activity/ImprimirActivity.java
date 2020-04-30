package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.R;

public class ImprimirActivity extends AppCompatActivity {
    ImageButton iBimprimir;
    Button btnfinalizar;
    String direccionURL;
    String KeyID;
    private SessionPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imprimir);
        iBimprimir=findViewById(R.id.iBimprimir);
        btnfinalizar=findViewById(R.id.btnfinalizar);
        prefs=new SessionPreferences(getApplicationContext());
        Intent intent=getIntent();
        KeyID=intent.getStringExtra("key");
        iBimprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direccionURL="http://android.policiadnfr.gob.bo/api/CertificadoPDF/"+KeyID+"/"+prefs.getUsuario().getId();
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse( direccionURL));
                startActivity(intent);

            }
        });
        btnfinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.cleanReserva();
                prefs.finishITV();
                Intent intent = new Intent(ImprimirActivity.this, PrincipalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
