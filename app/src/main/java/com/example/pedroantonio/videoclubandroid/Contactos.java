package com.example.pedroantonio.videoclubandroid;

import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;
// TODO: Mostrar contactos de forma correcta

public class Contactos extends AppCompatActivity {
    Button button;
    Button llamadas;
    TextView textView;
    ListView lvContactos;
    List<String> listaLlamadas = new ArrayList<>();
    List<String> listaContactos = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterDatos;
    ArrayAdapter<String> arrayAdapterLlamadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        lvContactos = (ListView) findViewById(R.id.lvContactos);
        button = (Button) findViewById(R.id.button);
        llamadas = (Button) findViewById(R.id.llamadas);
        textView = (TextView) findViewById(R.id.texto);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerDatos();
                button.setEnabled(false);
                llamadas.setEnabled(true);
            }
        });

        llamadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerDatosLlamadas();
                llamadas.setEnabled(false);
                button.setEnabled(true);
            }
        });
    }

    public void ObtenerDatosLlamadas() {
        Uri uri;
        uri = Uri.parse("content://call_log/calls");
        String[] projeccion = new String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};

        Cursor c = getContentResolver().query(uri, projeccion, null, null, null);
        while (c.moveToNext()) {
            listaLlamadas.add("Tipo: " + c.getString(0) + " Número: " + c.getString(1)
                    + " Duración: " + c.getString(2) + "\n");

            arrayAdapterDatos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                    listaLlamadas);
        }
        c.close();
        lvContactos.setAdapter(arrayAdapterLlamadas);
    }

    public void ObtenerDatos() {
        String[] projeccion = new String[]{ContactsContract.Data._ID,
                ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE};
        String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";
        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";

        Cursor c = getContentResolver().query(ContactsContract.Data.CONTENT_URI, projeccion,
                selectionClause, null, sortOrder);
        while (c.moveToNext()) {
            listaContactos.add("Nombre: " + c.getString(1)
                    + " Número: " + c.getString(2));
                if (!listaContactos.get(c.getPosition()).contains(c.getString(1))) {
                    listaContactos.add("Nombre: " + c.getString(1)
                            + " Número: " + c.getString(2));
                }
            arrayAdapterDatos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                    listaContactos);
        }
        c.close();
        lvContactos.setAdapter(arrayAdapterDatos);
    }
}