package policiadnfr.gob.itvremolques.Data.Views.Activity.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.reservas;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.Data.Views.Adapter.ReservaAdapter;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReservaFragment extends Fragment {
    //private OnFragmentInteactionListener mListener;
    RecyclerView recyclerReserva;
    List<reservas> listReserve;
    private RecyclerView.Adapter adapter;
    private ProgressBar pbreservas;
    private SessionPreferences prefs;
    private ReservaViewModel reservaViewModel;
    private TextView tvdireccion,tvmunicipio;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // homeViewModel =ViewModelProviders.of(this).get(HomeViewModel.class);
        prefs=new SessionPreferences(getContext());
        View vista = inflater.inflate(R.layout.fragment_reservas, container, false);
        tvdireccion=vista.findViewById(R.id.tvdireccionitv);
        tvmunicipio=vista.findViewById(R.id.tvmun);
        pbreservas=vista.findViewById(R.id.pbreservas);
        tvmunicipio.setText(prefs.getpuntoItv().getMunicipio());
        tvdireccion.setText(prefs.getpuntoItv().getDireccion());
        getreserva(prefs.getpuntoItv().getId_punto_inspeccion());
        recyclerReserva=vista.findViewById(R.id.RecyclerReserva);
        recyclerReserva.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration divider=new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerReserva.addItemDecoration(divider);

       // listReserve=new List;

        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return vista;
    }
    private void getreserva(int idpuntoitv){
        Call<List<reservas>> callreservas = Api.getApi().getReservas(idpuntoitv);
        callreservas.enqueue(new Callback<List<reservas>>() {
            @Override
            public void onResponse(Call<List<reservas>> call, Response<List<reservas>> response) {
                Log.d("TSE","ResponseServe:"+ response.body().size()           );
                if(response.body().size()<=0){
                    Toast.makeText(getContext(),"No se encontraron reservas para la fecha",Toast.LENGTH_LONG).show();
                }else if(response.body().get(0).getId_reserva()==0){
                    Toast.makeText(getContext(),"Jornada sin reservas",Toast.LENGTH_LONG).show();

                }else{

                    listReserve = response.body();
                    adapter = new ReservaAdapter(listReserve);
                    recyclerReserva.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

                pbreservas.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<reservas>> call, Throwable t) {
                Log.d("Falla","Errorxd"+t);
                Toast.makeText(getContext(),"Falla de conexion",Toast.LENGTH_LONG).show();
            }
        });
    }

}
