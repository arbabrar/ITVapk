package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import policiadnfr.gob.itvremolques.R;

public class RegistroITVActivity extends AppCompatActivity {
    private Spinner spitipoplaca;
    ArrayAdapter adapter;
    private String tipo_placa,version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroitv);
        spitipoplaca=findViewById(R.id.spitipoplaca);
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
    }
    private void toast(String mensaje){
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();

    }
}
