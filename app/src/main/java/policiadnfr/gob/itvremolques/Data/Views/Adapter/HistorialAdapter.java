package policiadnfr.gob.itvremolques.Data.Views.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import policiadnfr.gob.itvremolques.Data.Model.Historial;
import policiadnfr.gob.itvremolques.R;


public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.MyViewHolder> {
    private List<Historial> historial;
    private HistorialAdapterListener listener;

    public HistorialAdapter(List<Historial> historial, HistorialAdapterListener listener) {
        this.historial = historial;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_historico,parent,false);
        return new MyViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Historial _objhistorial=historial.get(i);
        myViewHolder.tvnrodocumentohistorico.setText(_objhistorial.getNro_documento());
        myViewHolder.tvpropietariohistorico.setText(_objhistorial.getPropietario());
        myViewHolder.tvplacahistorico.setText(_objhistorial.getPlaca());
        myViewHolder.tvhorahistorico.setText(_objhistorial.getHora());
        myViewHolder.tvcertficiadohistorico.setText(_objhistorial.getNro_certificado());



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
        if (historial.size()>0){
            i=historial.size();
        }
        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvplacahistorico,tvhorahistorico,tvcertficiadohistorico,tvpropietariohistorico,tvnrodocumentohistorico;

        public MyViewHolder(View view){
            super(view);

            tvplacahistorico=view.findViewById(R.id.tvplacahistorial);
            tvhorahistorico=view.findViewById(R.id.tvhorahistoria);
            tvcertficiadohistorico=view.findViewById(R.id.tvcertificadohistoria);
            tvpropietariohistorico=view.findViewById(R.id.tvpropietario);
            tvnrodocumentohistorico=view.findViewById(R.id.tvcedulapropietario);

            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listener.OnItemClicked(historial.get(getAdapterPosition()).getId_inspeccion());

        }
    }
    public interface  HistorialAdapterListener{
        void OnItemClicked(long id);
    }

}
