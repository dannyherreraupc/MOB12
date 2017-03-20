package com.dannyalfredo.user.myactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Usuario usuario;
    private EditText editLogin, editSenha;
    private CheckBox checkboxLogado;
    private Preferencia preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario = (Usuario) getIntent().getSerializableExtra(Constantes.TAG_USUARIO);
        editLogin = (EditText) findViewById(R.id.etLogin);
        editSenha = (EditText) findViewById(R.id.etSenha);
        checkboxLogado = (CheckBox) findViewById(R.id.cbLogado);
        preferencias = new Preferencia(this);
        validaLogado();
    }

    private void validaLogado(){
        if( preferencias.estaLogado() ){
            mostraDashboard();
        }
    }

    public void logaUsuario(View v){
        String login = editLogin.getText().toString();
        String senha = editSenha.getText().toString();

        if( formularioPreenchido() ){
            if( usuario != null ){
                logar(login, senha);
            }else{

                procuraNoBanco(login, senha);
            }
        }
    }

    private boolean formularioPreenchido(){
        boolean retorno = true;

        if( editLogin.getText().toString().equals("") ){
            editLogin.setError( getString(R.string.login_vazio) );
            retorno = false;
        }
        if( editSenha.getText().toString().equals("") ){
            editSenha.setError( getString(R.string.senha_vazia) );
            retorno = false;
        }

        return retorno;
    }

    private void logar(String login, String senha){
        if( usuario.getLogin().equals(login) && usuario.getSenha().equals(senha) ){

            if( checkboxLogado.isChecked() ){
                preferencias.manterLogado();
            }

            mostraDashboard();

        }else{
            Toast.makeText(this, R.string.erro_login, Toast.LENGTH_SHORT).show();
        }
    }

    private void procuraNoBanco(String usuario, String senha){
        DBhelper dbhelper = new DBhelper(this);
        if( dbhelper.procuraUsuario(usuario, senha) > 0 ){
            if( checkboxLogado.isChecked() ){
                preferencias.manterLogado();
            }

            mostraDashboard();
        }else{
            Toast.makeText(this, R.string.erro_login, Toast.LENGTH_SHORT).show();
        }
    }

    private void mostraDashboard(){
        Intent dashboard = new Intent(this, DashboardActivity.class);
        startActivity(dashboard);
        finish();
    }

}

