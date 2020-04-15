package policiadnfr.gob.itvremolques.Data.Views.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import policiadnfr.gob.itvremolques.Data.Model.puntoItv;
import policiadnfr.gob.itvremolques.R;

public class PuntoItvAdapter extends RecyclerView.Adapter<PuntoItvAdapter.MyViewHolder> implements Filterable {
    private List<policiadnfr.gob.itvremolques.Data.Model.puntoItv> puntoItv;
    private List<puntoItv> puntoItvsfull;
    private PuntoItvAdapterListener listener;

    public PuntoItvAdapter(List<puntoItv> puntoItv, PuntoItvAdapterListener listener) {
        this.puntoItv = puntoItv;
        this.listener=listener;
        this.puntoItvsfull=new ArrayList<>(puntoItv);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_puntoitv,parent,false);
        return new MyViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final puntoItv _objpunto=puntoItv.get(i);
        myViewHolder.tvdireccion.setText(_objpunto.getDireccion());
        myViewHolder.tvmunicipio.setText(_objpunto.getMunicipio());

    }

    @Override
    public int getItemCount() {
        int i=0;
        if (puntoItv.size()>0){
            i=puntoItv.size();
        }
        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvdireccion,tvmunicipio;

        public MyViewHolder(View view){
            super(view);
            tvdireccion=view.findViewById(R.id.tvdireccion);
            tvmunicipio=view.findViewById(R.id.tvmunicipio);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listener.OnItemClicked(puntoItv.get(getAdapterPosition()).getId_punto_inspeccion(),puntoItv.get(getAdapterPosition()).getDireccion(),puntoItv.get(getAdapterPosition()).getMunicipio());

        }
    }
    public interface PuntoItvAdapterListener{
        void OnItemClicked(int id, String direccion, String municipio);
    }

    @Override
    public Filter getFilter() {
        return puntosFilter;
    }
    private Filter puntosFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<puntoItv> filteredList=new ArrayList<>();
           if (constraint==null || constraint.length()==0){
               filteredList.addAll(puntoItvsfull);
           }else{
               String filterPattern =constraint.toString().toLowerCase().trim();
               for(puntoItv punto:puntoItvsfull){
                   if(punto.getDireccion().toLowerCase().contains(filterPattern)){
                       filteredList.add(punto);
                   }
               }
           }
           FilterResults results=new FilterResults();
           results.values=filteredList;
           return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            puntoItv.clear();
            puntoItv.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


}
