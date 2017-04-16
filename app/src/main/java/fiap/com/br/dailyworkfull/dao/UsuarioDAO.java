package fiap.com.br.dailyworkfull.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import fiap.com.br.dailyworkfull.model.Usuario;

/**
 * Created by gabri on 16/04/2017.
 */

public class UsuarioDAO {

    private SQLiteDatabase db;
    private DBOpenHelper banco;

    public UsuarioDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    private static final String TABELA_USUARIO = "usuario";
    private static final String COLUNA_USER = "user_name";
    private static final String COLUNA_PASSWORD = "password";

    public String add(Usuario usuario) {
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_USER, usuario.getUser());
        values.put(COLUNA_PASSWORD, usuario.getPassword());
        resultado = db.insert(TABELA_USUARIO,
                null,
                values);
        db.close();
        if (resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public List<Usuario> getAll() {
        List<Usuario> usuarios = new LinkedList<>();
        String rawQuery = "SELECT * FROM " +
                TABELA_USUARIO;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery, null);
        Usuario usuario = null;
        if (cursor.moveToFirst()) {
            do {
                usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setUser(cursor.getString(1));
                usuario.setPassword(cursor.getString(2));
                usuarios.add(usuario);
            } while (cursor.moveToNext());
        }
        db.close();
        return usuarios;
    }

}
