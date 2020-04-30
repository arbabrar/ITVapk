package policiadnfr.gob.itvremolques.Data.Views.Activity.ui.gallery;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.Historial;
import policiadnfr.gob.itvremolques.Data.Model.reservas;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.Data.Views.Activity.PrincipalActivity;
import policiadnfr.gob.itvremolques.Data.Views.Activity.PuntosActivity;
import policiadnfr.gob.itvremolques.Data.Views.Activity.RegistroPersona;
import policiadnfr.gob.itvremolques.Data.Views.Adapter.HistorialAdapter;
import policiadnfr.gob.itvremolques.Data.Views.Adapter.PuntoItvAdapter;
import policiadnfr.gob.itvremolques.Data.Views.Adapter.ReservaAdapter;
import policiadnfr.gob.itvremolques.Data.Views.Fragment.DatePickerFragment;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialFragment extends Fragment implements HistorialAdapter.HistorialAdapterListener{

    RecyclerView recyclerHistorial;
    List<Historial> listHistorial;
    private RecyclerView.Adapter adapter;
    private ProgressBar pbhistorial;
    private SessionPreferences prefs;
    long id_user;
    EditText etfechahistorial;
    ImageButton iBSearchHistorial;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog pdDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*historialViewModel =
                ViewModelProviders.of(this).get(HistorialViewModel.class);*/

        View root = inflater.inflate(R.layout.fragment_historial, container, false);
        prefs=new SessionPreferences(getContext());
        pbhistorial=root.findViewById(R.id.pbhistorial);
        pbhistorial.setVisibility(View.VISIBLE);
        etfechahistorial=root.findViewById(R.id.etfechahistorial);
        recyclerHistorial=root.findViewById(R.id.RecyclerHistorial);
        iBSearchHistorial=root.findViewById(R.id.iBSearchHistorial);
       /* recyclerHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration divider=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerHistorial.addItemDecoration(divider);*/
        iBSearchHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar()){
                    id_user=prefs.getUsuario().getId();
                    //pdDialog= ProgressDialog.show(getActivity(),"Recopilando datos","Espere por favor...",true,false);

                    BuscarInspecciones(etfechahistorial.getText().toString(),id_user);

                }
            }
        });
        etfechahistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        return root;
    }

    private void BuscarInspecciones(String fechainspeccion,long id_user){
        Call<List<Historial>> callhistorias = Api.getApi().getInspecciones(fechainspeccion,id_user);
        pbhistorial.setVisibility(View.GONE);
        callhistorias.enqueue(new Callback<List<Historial>>() {
            @Override
            public void onResponse(Call<List<Historial>> call, Response<List<Historial>> response) {
                Log.d("TSE","ResponseServe:"+ response.body().size()           );
                if(response.body().size()<=0){
                    Toast.makeText(getContext(),"No se encontraron inspecciones para la fecha",Toast.LENGTH_LONG).show();
                }else{

                    listHistorial = response.body();
                    recyclerHistorial.setHasFixedSize(true);
                    layoutManager=new LinearLayoutManager(getActivity());
                    adapter= new HistorialAdapter(listHistorial,HistorialFragment.this);
                    recyclerHistorial.setLayoutManager(layoutManager);
                    recyclerHistorial.setAdapter(adapter);
                    DividerItemDecoration divider=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
                    divider.setDrawable(getResources().getDrawable(R.drawable.divider));
                    recyclerHistorial.addItemDecoration(divider);
                    adapter.notifyDataSetChanged();

                }

               // pbreservas.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<Historial>> call, Throwable t) {
                Log.d("Falla","Errorxd"+t);
                Toast.makeText(getContext(),"Falla de conexion",Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean validar(){
        boolean valido=true;

        String fechahistorial=etfechahistorial.getText().toString();
         if(fechahistorial.isEmpty()){
            valido=false;
             etfechahistorial.setError("Elija la fecha de sus inspecciones");
        }else{
             etfechahistorial.setError(null);
        }

        return valido;
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = twoDigits(day) + "-" + twoDigits(month+1) + "-" + year;
                etfechahistorial.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    @Override
    public void OnItemClicked(long id) {
        Toast.makeText(getActivity(), "Escogiste algo", Toast.LENGTH_SHORT).show();
    }
}
