package policiadnfr.gob.itvremolques.Data.Views.Activity.Dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.datocertificado;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarCertificadoDialog extends AppCompatDialogFragment {
    TextView tvplaca,tvfecha,tvhora,tvfuncionario,tvcelular,tvcertificado,tvSindatos;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =getActivity().getLayoutInflater();
        View view= inflater.inflate(R.layout.alert_certificado,null);
        tvcelular=view.findViewById(R.id.tvcelular);
        tvcertificado=view.findViewById(R.id.tvcertificado);
        tvfecha=view.findViewById(R.id.tvfecha);
        tvfuncionario=view.findViewById(R.id.tvfuncionario);
        tvhora=view.findViewById(R.id.tvhora);
        tvplaca=view.findViewById(R.id.tvplaca);
        tvcelular.setText(getArguments().getString("celular"));
        tvplaca.setText(getArguments().getString("placa"));
        tvfuncionario.setText(getArguments().getString("funcionario"));
        tvhora.setText(getArguments().getString("hora"));
        tvfecha.setText(getArguments().getString("fecha"));
        tvcertificado.setText(getArguments().getString("nrocertificado"));
        builder.setView(view)
                .setTitle("Nro de Certificado No Disponible")
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });



        return builder.create();
    }

    public static BuscarCertificadoDialog newInstance(String placa,String celular,String fecha,String nrocertificado,String funcionario,String hora) {
        BuscarCertificadoDialog frag = new BuscarCertificadoDialog();
        Bundle args = new Bundle();
        args.putString("placa", placa);
        args.putString("hora", hora);
        args.putString("funcionario", funcionario);
        args.putString("fecha", fecha);
        args.putString("celular", celular);
        args.putString("nrocertificado", nrocertificado);
        frag.setArguments(args);
        return frag;
    }
   /*private boolean validar(){

        boolean valido=true;
        String certificado=etbuscarcertificado.getText().toString();
        if(certificado.isEmpty()){
            valido=false;
            etbuscarcertificado.setError("El numero de certificado es requerido");

        }else{
            if(certificado.length()<7){
                valido=false;
                etbuscarcertificado.setError("ingrese los siete digitos del certificado");
            }else{
                etbuscarcertificado.setError(null);
            }

        }
        return valido;
    }*/
   /* private void burcarCertficado(String nroCertificado){

        Call<datocertificado> callgetdatoscertificado= Api.getApi().getdatoscertificado(nroCertificado);
        callgetdatoscertificado.enqueue(new Callback<datocertificado>() {
            @Override
            public void onResponse(Call<datocertificado> call, Response<datocertificado> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus()==605){
                        iddatocertificado.setVisibility(View.VISIBLE);
                        tvplaca.setText(response.body().getPlaca());
                        tvfecha.setText(response.body().getFecha());
                        tvhora.setText(response.body().getHora());
                        tvcertificado.setText(response.body().getNro_certificado());
                        tvcelular.setText(response.body().getCelular());
                        tvfuncionario.setText(response.body().getFuncionario());
                    }else {
                        tvSindatos.setVisibility(View.VISIBLE);

                    }
                }


            }

            @Override
            public void onFailure(Call<datocertificado> call, Throwable t) {
                 Log.d("ERROR","Falla"+ t);

            }
        });
    }*/

}
