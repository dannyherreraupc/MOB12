package com.dannyalfredo.user.myactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button btnAgregarMiembro;
    ListView lista;
    SQLControlador dbconeccion;
    TextView tv_miemID, tv_miemNombre, tv_miemProfesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        btnAgregarMiembro = (Button) findViewById(R.id.btnAgregarMiembro);
        lista = (ListView) findViewById(R.id.listViewMiembros);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Boton para agregar miembro
        btnAgregarMiembro.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iagregar = new Intent(MainActivity.this, AgregarMiembro.class);
                startActivity(iagregar);
            }
        });

        // pega os dados do BD
        Cursor cursor = dbconeccion.leerDatos();

        String[] from = new String[] {
                DBhelper.MIEMBRO_ID,
                DBhelper.MIEMBRO_NOMBRE,
                DBhelper.MIEMBRO_PROFESION
        };
        int[] to = new int[] {
                R.id.miembro_id,
                R.id.miembro_nombre,
                R.id.miembro_profesion
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this, R.layout.formato_fila, cursor, from, to);

        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);

        // ação click no item para modificarlo ou eliminar
        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                tv_miemID = (TextView) view.findViewById(R.id.miembro_id);
                tv_miemNombre = (TextView) view.findViewById(R.id.miembro_nombre);
                tv_miemProfesion = (TextView) view.findViewById(R.id.miembro_profesion);

                String aux_miembroId = tv_miemID.getText().toString();
                String aux_miembroNombre = tv_miemNombre.getText().toString();
                String aux_miembroProfesion = tv_miemProfesion.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModificarMiembro.class);
                modify_intent.putExtra("miembroId", aux_miembroId);
                modify_intent.putExtra("miembroNombre", aux_miembroNombre);
                modify_intent.putExtra("miembroProfesion", aux_miembroProfesion);
                startActivity(modify_intent);
            }
        });
    }//termina onCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }

} //termina clase
