package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import androidx.appcompat.widget.SearchView;

import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.puntoItv;
import policiadnfr.gob.itvremolques.Data.Preferences.SessionPreferences;
import policiadnfr.gob.itvremolques.Data.Views.Adapter.PuntoItvAdapter;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PuntosActivity extends AppCompatActivity implements PuntoItvAdapter.PuntoItvAdapterListener{
    private SessionPreferences prefs;
    private ProgressBar pbpuntosinspeccion;
    private RecyclerView puntoRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<puntoItv> puntoItvs;
    private SearchView searchView;
    PuntoItvAdapter puntotoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);
        prefs=new SessionPreferences(getApplicationContext());
        pbpuntosinspeccion=findViewById(R.id.pbpuntosinspeccion);
        Intent intent=getIntent();
        final int id_departamento=intent.getIntExtra("id",0);
        setUpRecyclerView(id_departamento);
    }
    private void setUpRecyclerView(int iddepartamento) {
        Call<List<puntoItv>> callpuntoitv = Api.getApi().getpuntoItv(iddepartamento);
        callpuntoitv.enqueue(new Callback<List<puntoItv>>() {
            @Override
            public void onResponse(Call<List<puntoItv>> call, Response<List<puntoItv>> response) {
                Log.d("TSE", response.body().get(0).getDireccion());
                puntoItvs = response.body();
                pbpuntosinspeccion.setVisibility(View.GONE);
                puntoRecycler=findViewById(R.id.RecyclerPuntosItv);
                puntoRecycler.setHasFixedSize(true);
                layoutManager=new LinearLayoutManager(PuntosActivity.this);
                puntotoAdapter= new PuntoItvAdapter(puntoItvs,PuntosActivity.this);
                puntoRecycler.setLayoutManager(layoutManager);
                puntoRecycler.setAdapter(puntotoAdapter);
                DividerItemDecoration divider=new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
                divider.setDrawable(getResources().getDrawable(R.drawable.divider));
                puntoRecycler.addItemDecoration(divider);

                /*adapter = new PuntoItvAdapter(puntoItvs,PuntosActivity.this);
                puntoRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/

            }

            @Override
            public void onFailure(Call<List<puntoItv>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Falla de conexion",Toast.LENGTH_LONG).show();
            }
        });





    }
    @Override
    public void OnItemClicked(int id, String direccion, String municipio) {
        prefs.guardarpunto(id,direccion,municipio);
        Intent intent=new Intent(PuntosActivity.this,PrincipalActivity.class);

        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.busqueda,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        searchView=(SearchView)searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                puntotoAdapter.getFilter().filter(s);
                return false;
            }
        });



        return true;
    }
}
