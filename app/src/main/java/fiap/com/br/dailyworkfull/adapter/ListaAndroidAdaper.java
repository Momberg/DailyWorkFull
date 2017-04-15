package fiap.com.br.dailyworkfull.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fiap.com.br.dailyworkfull.R;
import fiap.com.br.dailyworkfull.model.Tarefa;


/**
 * Created by Momberg on 23/02/2017.
 */

public class ListaAndroidAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Tarefa> tarefas;

    public ListaAndroidAdaper(Context context, List<Tarefa> tarefas){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.tarefas = tarefas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new AndroidItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AndroidItemHolder androidItemHolder = (AndroidItemHolder) holder;
        androidItemHolder.tvNome.setText(tarefas.get(position).getNome());
        androidItemHolder.tvTarefa.setText(tarefas.get(position).getTipo().getNome());
        androidItemHolder.tvData.setText(String.valueOf(tarefas.get(position).getData()));
        androidItemHolder.id.setText(String.valueOf(tarefas.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    private class AndroidItemHolder extends RecyclerView.ViewHolder{

        TextView tvNome, tvTarefa, tvData, id;

        public AndroidItemHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.id);
            tvNome = (TextView) itemView.findViewById(R.id.tvNome);
            tvTarefa = (TextView) itemView.findViewById(R.id.tvTarefa);
            tvData = (TextView) itemView.findViewById(R.id.tvData);

        }
    }

}
