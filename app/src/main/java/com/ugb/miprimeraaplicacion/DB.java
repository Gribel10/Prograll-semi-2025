package com.ugb.miprimeraaplicacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "amigos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE amigos (" +
                    "idAmigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "direccion TEXT, " +
                    "telefono TEXT, " +
                    "email TEXT, " +
                    "dui TEXT, " +
                    "urlFoto TEXT)";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Para futuras actualizaciones de la BD
    }

    public String administrar_amigos(String accion, String[] datos) {
        SQLiteDatabase db = getWritableDatabase();
        String mensaje = "ok";

        try {
            switch (accion) {
                case "agregar":
                    ContentValues valores = new ContentValues();
                    valores.put("nombre", datos[1]);
                    valores.put("direccion", datos[2]);
                    valores.put("telefono", datos[3]);
                    valores.put("email", datos[4]);
                    valores.put("dui", datos[5]);
                    valores.put("urlFoto", datos[6]);
                    db.insert("amigos", null, valores);
                    break;

                case "modificar":
                    ContentValues valoresMod = new ContentValues();
                    valoresMod.put("nombre", datos[1]);
                    valoresMod.put("direccion", datos[2]);
                    valoresMod.put("telefono", datos[3]);
                    valoresMod.put("email", datos[4]);
                    valoresMod.put("dui", datos[5]);
                    valoresMod.put("urlFoto", datos[6]);
                    db.update("amigos", valoresMod, "idAmigo = ?", new String[]{datos[0]});
                    break;

                case "eliminar":
                    db.delete("amigos", "idAmigo = ?", new String[]{datos[0]});
                    break;

                default:
                    mensaje = "Acción no válida";
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
        } finally {
            db.close();
        }
        return mensaje;
    }

    public Cursor lista_amigos() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            return db.rawQuery("SELECT * FROM amigos", null);
        } catch (Exception e) {
            return null;
        }
    }

    public void eliminaAmigo(String idAmigo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.delete("amigos", "idAmigo = ?", new String[]{idAmigo});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}
