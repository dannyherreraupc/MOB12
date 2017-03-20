package com.dannyalfredo.user.myactivity;

/**
 * Created by User on 18/03/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarMiembro extends AppCompatActivity implements OnClickListener {
    private Activity activity;
    EditText et;
    EditText etP;
    Button btnAgregar, read_bt;
    SQLControlador dbconeccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_miembro);
        et = (EditText) findViewById(R.id.et_miembro_id);
        etP = (EditText) findViewById(R.id.et_miembro_profesion);
        btnAgregar = (Button) findViewById(R.id.btnAgregarId);

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        btnAgregar.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnAgregarId:

                if (et.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.error_name, Toast.LENGTH_SHORT).show();
                    et.requestFocus();
                } else if (etP.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.erro_profession, Toast.LENGTH_SHORT).show();
                    etP.requestFocus();
                } else {
                    String name = et.getText().toString();
                    String profession = etP.getText().toString();
                    dbconeccion.insertarDatos(name, profession);
                    Intent main = new Intent(AgregarMiembro.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                    finish();
                    break;

                }
        }

        }
    }


