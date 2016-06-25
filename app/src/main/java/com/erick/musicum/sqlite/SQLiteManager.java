package com.erick.musicum.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.erick.musicum.common.objects.Columnas;
import com.erick.musicum.common.objects.Constantes;


/**
 * Created by ErickSteven on 14/06/2015.
 */
public class SQLiteManager {

    private final SQLiteHelper helper;
    private final SQLiteDatabase db;

    public SQLiteManager(Context context) {
        helper = new SQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public boolean isTableExists(String tableName) {

        String sentencia = "SELECT name FROM sqlite_master WHERE type='table' AND name=" + tableName + ";";
        Cursor cursor = db.rawQuery(sentencia, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public SQLiteHelper getHelper() {
        return helper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public boolean cancionEnLista(long idCancion, long idLista) {
        final Cursor cursor = db.query(Constantes.TABLA_LISTA_CANCION, Columnas.listaCancion,
                Constantes.CAN_ID + " = " + idCancion + " AND " + Constantes.LIS_ID + " = " + idLista, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
