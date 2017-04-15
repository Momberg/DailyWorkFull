package fiap.com.br.dailyworkfull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import fiap.com.br.dailyworkfull.dao.TarefaDAO;
import fiap.com.br.dailyworkfull.dao.Tipo_TarefaDAO;
import fiap.com.br.dailyworkfull.model.Tarefa;
import fiap.com.br.dailyworkfull.model.Tipo_Tarefa;

public class NovaTarefa extends AppCompatActivity {

    public final static int CODE_NOVA_TAREFA = 666;
    public final static int CODE_EDITA_TAREFA = 333;
    private TextInputLayout tilNomeTarefa;
    private TextView txNomeTarefa;
    private Spinner spTarefa;
    private List<Tipo_Tarefa> tarefas;
    private CalendarView cvData;
    private String data;
    private SharedPreferences id_tarefa;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);
        tilNomeTarefa = (TextInputLayout) findViewById(R.id.tilNomeTarefa);
        txNomeTarefa = (TextView) findViewById(R.id.txNomeTarefa);
        spTarefa = (Spinner) findViewById(R.id.spTarefa);
        cvData = (CalendarView) findViewById(R.id.cvData);
        id_tarefa = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        id = id_tarefa.getInt("ID",0);
        Tipo_TarefaDAO Tipo_TarefaDAO = new Tipo_TarefaDAO(this);
        tarefas = Tipo_TarefaDAO.getAll();
        ArrayAdapter<Tipo_Tarefa> adapter =
                new ArrayAdapter<Tipo_Tarefa>(getApplicationContext(),
                        R.layout.tarefa_spinner_item, tarefas);
        adapter.setDropDownViewResource(R.layout.tarefa_spinner_item);
        spTarefa.setAdapter(adapter);
        Calendar c = Calendar.getInstance();
        data = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
        cvData.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                data = dayOfMonth + "/" + month + "/" + year;
            }
        });
        if(id != 0){
            editar();
        }
    }

    public void cadastrar(View v) {
        TarefaDAO tarefaDAO = new TarefaDAO(this);
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(String.valueOf(tilNomeTarefa.getEditText().getText()));
        tarefa.setTipo((Tipo_Tarefa) spTarefa.getSelectedItem());
        tarefa.setData(data);
        tarefa.setId(id);
        if(id == 0){
            tarefaDAO.add(tarefa);
            id_tarefa.edit().putInt("ID", 0).apply();
            retornaParaTelaAnteriorPosEditar();
        } else {
            tarefaDAO.editByID(tarefa);
            retornaParaTelaAnterior();
        }
    }

    //retorna para tela de lista de tarefaes
    public void retornaParaTelaAnterior() {
        Intent intentMessage = new Intent();
        setResult(CODE_NOVA_TAREFA, intentMessage);
        finish();
    }

    public void retornaParaTelaAnteriorPosEditar() {
        Intent intentMessage = new Intent();
        setResult(CODE_EDITA_TAREFA, intentMessage);
        finish();
    }

    public void editar(){
        TarefaDAO tarefaDAO = new TarefaDAO(this);
        Tarefa tarefa;
        tarefa = tarefaDAO.getByID(id);
        txNomeTarefa.setText(String.valueOf(tarefa.getNome()));
        spTarefa.setSelection(tarefa.getTipo().getId() - 1);
        id_tarefa.edit().putInt("ID", 0).apply();
    }
}
