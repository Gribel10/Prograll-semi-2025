package com.ugb.miprimeraaplicacion;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class lista_amigos extends Activity {
    Bundle parametros = new Bundle();
    ListView ltsAmigos;
    Cursor cAmigos;
    DB db;
    final ArrayList<amigos> alAmigos = new ArrayList<>();
    final ArrayList<amigos> alAmigosCopia = new ArrayList<>();
    JSONArray jsonArray;
    JSONObject jsonObject;
    amigos misAmigos;
    FloatingActionButton fab;
    int posicion; // Variable para manejar selección de menú contextual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_amigos);

        db = new DB(this);
        ltsAmigos = findViewById(R.id.ltsAmigos); // Inicializar ListView

        fab = findViewById(R.id.fabAgregarAmigo);
        fab.setOnClickListener(view -> abrirVentana());

        obtenerDatosAmigos();
        buscarAmigos();
    }

    private void abrirVentana() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(parametros);
        startActivity(intent);
    }

    private void obtenerDatosAmigos() {
        try {
            cAmigos = db.lista_amigos();
            if (cAmigos.moveToFirst()) {
                jsonArray = new JSONArray();
                do {
                    jsonObject = new JSONObject();
                    jsonObject.put("idAmigo", cAmigos.getString(0));
                    jsonObject.put("nombre", cAmigos.getString(1));
                    jsonObject.put("direccion", cAmigos.getString(2));
                    jsonObject.put("telefono", cAmigos.getString(3));
                    jsonObject.put("email", cAmigos.getString(4));
                    jsonObject.put("dui", cAmigos.getString(5));
                    jsonObject.put("foto", cAmigos.getString(6));
                    jsonArray.put(jsonObject);
                } while (cAmigos.moveToNext());
                mostrarDatosAmigos();
            } else {
                mostrarMsg("No hay amigos registrados.");
                abrirVentana();
            }
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }

    private void mostrarDatosAmigos() {
        try {
            if (jsonArray.length() > 0) {
                alAmigos.clear();
                alAmigosCopia.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    misAmigos = new amigos(
                            jsonObject.getString("idAmigo"),
                            jsonObject.getString("nombre"),
                            jsonObject.getString("direccion"),
                            jsonObject.getString("telefono"),
                            jsonObject.getString("email"),
                            jsonObject.getString("dui"),
                            jsonObject.getString("foto")
                    );
                    alAmigos.add(misAmigos);
                }
                alAmigosCopia.addAll(alAmigos);
                ltsAmigos.setAdapter(new AdactadorAmigos(this, alAmigos));
                registerForContextMenu(ltsAmigos);
            } else {
                mostrarMsg("No hay amigos registrados.");
                abrirVentana();
            }
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }

    private void buscarAmigos() {
        TextView tempVal = findViewById(R.id.txtBuscarAmigos);
        tempVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                alAmigos.clear();
                String buscar = tempVal.getText().toString().trim().toLowerCase();
                if (buscar.isEmpty()) {
                    alAmigos.addAll(alAmigosCopia);
                } else {
                    for (amigos item : alAmigosCopia) {
                        if (item.getNombre().toLowerCase().contains(buscar) ||
                                item.getDui().toLowerCase().contains(buscar) ||
                                item.getEmail().toLowerCase().contains(buscar)) {
                            alAmigos.add(item);
                        }
                    }
                }
                ltsAmigos.setAdapter(new AdactadorAmigos(getApplicationContext(), alAmigos));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void mostrarMsg(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mimenu, menu);
        try {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            posicion = info.position;
            menu.setHeaderTitle(jsonArray.getJSONObject(posicion).getString("nombre"));
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            if (item.getItemId() == R.id.mnxNuevo) {
                abrirVentana();
            } else if (item.getItemId() == R.id.mnxModificar) {
                parametros.putString("accion", "modificar");
                parametros.putString("amigos", jsonArray.getJSONObject(posicion).toString());
                abrirVentana();
            } else if (item.getItemId() == R.id.mnxEliminar) {
                eliminarAmigo();
            }
            return true;
        } catch (Exception e) {
            mostrarMsg("Error: " + e.getMessage());
            return super.onContextItemSelected(item);
        }
    }

    private void eliminarAmigo() {
        try {
            db.eliminaAmigo(jsonArray.getJSONObject(posicion).getString("idAmigo"));
            mostrarMsg("Amigo eliminado correctamente.");
            obtenerDatosAmigos(); // Recargar la lista
        } catch (Exception e) {
            mostrarMsg("Error al eliminar: " + e.getMessage());
        }
    }
}
