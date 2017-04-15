package fiap.com.br.dailyworkfull.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import fiap.com.br.dailyworkfull.model.Tipo_Tarefa;


/**
 * Created by gabri on 02/03/2017.
 */

public class Tipo_TarefaDAO {

    private DBOpenHelper banco;
    public Tipo_TarefaDAO(Context context) {
        banco = new DBOpenHelper(context);
    }
    public static final String TABELA_TAREFA_TIPO = "tarefa_tipo";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public List<Tipo_Tarefa> getAll() {
        List<Tipo_Tarefa> tarefas = new LinkedList<>();
        String query = "SELECT * FROM " + TABELA_TAREFA_TIPO;
        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Tipo_Tarefa tarefa = null;
        if (cursor.moveToFirst()) {
            do {
                tarefa = new Tipo_Tarefa();
                tarefa.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
                tarefa.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                tarefas.add(tarefa);
            } while (cursor.moveToNext());
        }
        return tarefas;
    }
    public Tipo_Tarefa getBy(int id) {
        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_NOME};
        String where = "id = " + id;
        Cursor cursor = db.query(true, TABELA_TAREFA_TIPO, colunas, where, null, null,
                null, null, null);
        Tipo_Tarefa tarefa = null;
        if(cursor != null)
        {
            cursor.moveToFirst();
            tarefa = new Tipo_Tarefa();
            tarefa.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
            tarefa.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
        }
        return tarefa;
    }
}
