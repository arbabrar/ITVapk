package policiadnfr.gob.itvremolques.Data.Views.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import policiadnfr.gob.itvremolques.Data.Model.departamento;
import policiadnfr.gob.itvremolques.R;


public class DepartamentosAdapter extends RecyclerView.Adapter<DepartamentosAdapter.MyViewHolder> {
    private List<policiadnfr.gob.itvremolques.Data.Model.departamento> departamento;
    private DepartamentosAdapterListener listener;

    public DepartamentosAdapter(List<departamento> departamento, DepartamentosAdapterListener listener) {
        this.departamento = departamento;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_departamento,parent,false);
        return new MyViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final departamento _objdepartamento=departamento.get(i);
        myViewHolder.tvnombredepartamento.setText(_objdepartamento.getNombre());
        Picasso.get().load(_objdepartamento.getImageURL()).fit().into(myViewHolder.imicono);

       /* myViewHolder.imicono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* Intent intent =new Intent(context,PuntosActivity.class);
                intent.putExtra("id_departamento", _objdepartamento.getId_departamento());
                context.startActivity(intent);*//*
            }
        });*/
    }

    @Override
    public int getItemCount() {
        int i=0;
        if (departamento.size()>0){
            i=departamento.size();
        }
        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imicono;
        public TextView tvnombredepartamento;

        public MyViewHolder(View view){
            super(view);
            imicono=view.findViewById(R.id.ivdepartamento);
            tvnombredepartamento=view.findViewById(R.id.tvNombredepartamento);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listener.OnItemClicked(departamento.get(getAdapterPosition()).getId_departamento());

        }
    }
    public interface DepartamentosAdapterListener{
        void OnItemClicked(int id);
    }

}
