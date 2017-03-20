package com.dannyalfredo.user.myactivity;

/**
 * Created by User on 18/03/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModificarMiembro extends AppCompatActivity implements OnClickListener {

    EditText etNo, etPro;
    Button btnActualizar, btnEliminar;

    long member_id;

    SQLControlador dbcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_miembro);

        dbcon = new SQLControlador(this);
        dbcon.abrirBaseDeDatos();

        etNo = (EditText) findViewById(R.id.et_miembro_id);
        etPro = (EditText) findViewById(R.id.et_miembro_profesionu);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        Intent i = getIntent();
        String memberID = i.getStringExtra("miembroId");
        String memberName = i.getStringExtra("miembroNombre");
        String memberProfession = i.getStringExtra("miembroProfesion");

        member_id = Long.parseLong(memberID);

        etNo.setText(memberName);
        etPro.setText(memberProfession);


        btnActualizar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

// codigo para mostar a zeta isquerda
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
            case R.id.btnActualizar:
                if (etNo.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.error_name, Toast.LENGTH_SHORT).show();
                    etNo.requestFocus();
                    break;
                } else if (etPro.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.erro_profession, Toast.LENGTH_SHORT).show();
                    etPro.requestFocus();
                    break;


                } else {
                    String memName_upd = etNo.getText().toString();
                    String memProfession_upd = etPro.getText().toString();
                    dbcon.actualizarDatos(member_id, memName_upd , memProfession_upd);
                    this.returnHome();
                    break;
                }


            case R.id.btnEliminar:
                dbcon.deleteData(member_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }
}