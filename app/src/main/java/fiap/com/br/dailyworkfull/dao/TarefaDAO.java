package fiap.com.br.dailyworkfull.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import fiap.com.br.dailyworkfull.model.Tarefa;
import fiap.com.br.dailyworkfull.model.Tipo_Tarefa;


/**
 * Created by gabri on 02/03/2017.
 */

public class TarefaDAO {

    private SQLiteDatabase db;
    private DBOpenHelper banco;

    public TarefaDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    private static final String TABELA_TAREFA = "tarefa";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_TAREFA_TIPO_ID = "tarefa_tipo_id";
    private static final String COLUNA_DATA = "data";

    //private static final String[] COLUMNS = {COLUNA_ID, COLUNA_NOME,COLUNA_TAREFA_TIPO_ID};
    public String add(Tarefa tarefa) {
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, tarefa.getNome());
        values.put(COLUNA_TAREFA_TIPO_ID, tarefa.getTipo().getId());
        values.put(COLUNA_DATA, String.valueOf(tarefa.getData()));
        resultado = db.insert(TABELA_TAREFA,
                null,
                values);
        db.close();
        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public List<Tarefa> getAll() {
        List<Tarefa> tarefas = new LinkedList<>();
        String rawQuery = "SELECT t.*, c.nome FROM " +
                TarefaDAO.TABELA_TAREFA + " t INNER JOIN " +
                Tipo_TarefaDAO.TABELA_TAREFA_TIPO
                + " c ON t." + TarefaDAO.COLUNA_TAREFA_TIPO_ID + " = c." +
                Tipo_TarefaDAO.COLUNA_ID; /*+
                " ORDER BY " + TarefaDAO.COLUNA_NOME + " ASC";*/
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery, null);
        Tarefa tarefa = null;
        if (cursor.moveToFirst()) {
            do {
                tarefa = new Tarefa();
                tarefa.setId(cursor.getInt(0));
                tarefa.setNome(cursor.getString(1));
                tarefa.setTipo(new Tipo_Tarefa(cursor.getInt(2), cursor.getString(4)));
                tarefa.setData(cursor.getString(3));
                tarefas.add(tarefa);
            } while (cursor.moveToNext());
        }
        db.close();
        return tarefas;
    }

    public void deleteByID(int id) {
        SQLiteDatabase db = banco.getReadableDatabase();
        db.delete(TarefaDAO.TABELA_TAREFA, "id = " + id, null);
        db.close();
    }

    public Tarefa getByID(int id){
        SQLiteDatabase db = banco.getReadableDatabase();
        String rawQuery = "SELECT t.*, c.nome FROM " +
                TarefaDAO.TABELA_TAREFA + " t INNER JOIN " +
                Tipo_TarefaDAO.TABELA_TAREFA_TIPO
                + " c ON t." + TarefaDAO.COLUNA_TAREFA_TIPO_ID + " = c." +
                Tipo_TarefaDAO.COLUNA_ID + " where t.id = " + id;
        Cursor cursor = db.rawQuery(rawQuery, null);
        Tarefa tarefa = null;
        if (cursor.moveToFirst()) {
            do {
                tarefa = new Tarefa();
                tarefa.setId(cursor.getInt(0));
                tarefa.setNome(cursor.getString(1));
                tarefa.setTipo(new Tipo_Tarefa(cursor.getInt(2), cursor.getString(4)));
                tarefa.setData(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        db.close();
        return tarefa;
    }

    public String editByID(Tarefa tarefa){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, tarefa.getNome());
        values.put(COLUNA_TAREFA_TIPO_ID, tarefa.getTipo().getId());
        values.put(COLUNA_DATA, String.valueOf(tarefa.getData()));
        resultado = db.update(TarefaDAO.TABELA_TAREFA, values, " id = "+ tarefa.getId(), null);
        db.close();
        if (resultado == -1) {
            return "Erro ao editar registro";
        } else {
            return "Registro editado com sucesso";
        }
    }

}
