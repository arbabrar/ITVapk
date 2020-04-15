package policiadnfr.gob.itvremolques.Data.Views.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import policiadnfr.gob.itvremolques.Data.Model.reservas;
import policiadnfr.gob.itvremolques.R;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.MyViewHolder> {
    private List<policiadnfr.gob.itvremolques.Data.Model.reservas> reservas;
    private ReservaAdapterListener listener;

    public ReservaAdapter(List<reservas> reservas, ReservaAdapterListener listener) {
        this.reservas = reservas;
        this.listener=listener;
    }

    public ReservaAdapter(List<reservas> reservas) {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reserva,parent,false);
        return new MyViewHolder(itemview);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final reservas _objreserva= reservas.get(i);
        String fullName=_objreserva.getNombreP()+" "+_objreserva.getApellido_paterno()+" "+_objreserva.getApellido_materno();
        myViewHolder.tvplacareserva.setText(_objreserva.getPlaca());
        myViewHolder.tvhora.setText(_objreserva.getHora_reserva());
        myViewHolder.tvfullName.setText(fullName);

    }

    @Override
    public int getItemCount() {
        int i=0;
        if (reservas.size()>0){
            i=reservas.size();
        }
        return i;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvplacareserva,tvhora,tvfullName;

        public MyViewHolder(View view){
            super(view);
            tvplacareserva=view.findViewById(R.id.tvplacareseerva);
            tvhora=view.findViewById(R.id.tvhora);
            tvfullName=view.findViewById(R.id.tvfullName);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listener.OnItemClicked(reservas.get(getAdapterPosition()).getId_reserva());

        }
    }

    public interface ReservaAdapterListener{
        void OnItemClicked(int id);
    }

}
