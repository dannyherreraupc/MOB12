package com.dannyalfredo.user.myactivity;

/**
 * Created by User on 18/03/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {

    // Informação das tabelas
    public static final String TABELA_USUARIOS = "usuarios";

    public static final String TABLE_MEMBER = "miembros";
    public static final String MIEMBRO_ID = "_id";
    public static final String MIEMBRO_NOMBRE = "nombre";
    public static final String MIEMBRO_PROFESION = "profesion";

    // informação banco de dados
    static final String DB_NAME = "DBMIEMBRO";
    static final int DB_VERSION = 1;



    // Informacão BD
    private static final String CREATE_TABLE = "create table "
            + TABLE_MEMBER + "(" + MIEMBRO_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MIEMBRO_NOMBRE + " TEXT NOT NULL," + MIEMBRO_PROFESION + " TEXT NOT NULL);";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( criaTabelaUsuarios() );
        db.execSQL(CREATE_TABLE);

    }

    public DBhelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);

        onCreate(db);
    }

    // Usuarios
   private String criaTabelaUsuarios(){
        String sql = "CREATE TABLE " + TABELA_USUARIOS
                + " (id INTEGER PRIMARY KEY, "
                + " login TEXT, "
                + " senha TEXT);";

        return sql;
    }

    public void insereUsuario(Usuario usuario){
        ContentValues values = new ContentValues();

        values.put("login", usuario.getLogin());
        values.put("senha", usuario.getSenha());


        getWritableDatabase().insert(TABELA_USUARIOS, null, values);
    }

   public int procuraUsuario(String login, String senha){
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM " + TABELA_USUARIOS
                + " WHERE login='"+ login.trim() +"' AND senha='"+ senha.trim() +"';";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Usuario usuario = new Usuario();
            usuarios.add(usuario);
        }

        return usuarios.size();
    }


}
