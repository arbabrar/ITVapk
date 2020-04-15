package policiadnfr.gob.itvremolques.Data.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import policiadnfr.gob.itvremolques.Data.Api.Api;
import policiadnfr.gob.itvremolques.Data.Model.departamento;
import policiadnfr.gob.itvremolques.Data.Views.Adapter.DepartamentosAdapter;
import policiadnfr.gob.itvremolques.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartamentoActivity extends AppCompatActivity  implements DepartamentosAdapter.DepartamentosAdapterListener{

    private ProgressBar pbdepartamento;
    private RecyclerView dptoRecycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List <policiadnfr.gob.itvremolques.Data.Model.departamento> departamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamento);
        pbdepartamento=findViewById(R.id.pbdepartamento);
        getdepartamentos();
        dptoRecycler=findViewById(R.id.RecyclerDpto);
        dptoRecycler.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(this,3);
        dptoRecycler.setLayoutManager(layoutManager);
    }

    private void getdepartamentos() {
        Call<List<departamento>> calldepartamento = Api.getApi().getDepartamento();
        calldepartamento.enqueue(new Callback<List<departamento>>() {
            @Override
            public void onResponse(Call<List<departamento>> call, Response<List<departamento>> response) {
                Log.d("TSE", response.body().get(0).getNombre());
                departamento = response.body();
                pbdepartamento.setVisibility(View.GONE);
                adapter = new DepartamentosAdapter(departamento,DepartamentoActivity.this);
                dptoRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<departamento>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Falla de conexion",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnItemClicked(int id) {
        Intent intent=new Intent(DepartamentoActivity.this,PuntosActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
