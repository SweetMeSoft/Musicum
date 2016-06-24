package com.erick.musicum.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.erick.musicum.comun.objetos.Constantes;

/**
 * Created by erick on 14/06/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;

    public SQLiteHelper(Context context) {
        super(context, Constantes.NOMBRE_DB, null, Constantes.VERSION_DB);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constantes.SENTENCIA_CREAR_TABLA_LISTAS);
        db.execSQL(Constantes.SENTENCIA_CREAR_TABLA_LISTA_CANCIONES);
        db.execSQL(Constantes.SENTENCIA_CREAR_TABLA_LISTA_ACTUAL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
